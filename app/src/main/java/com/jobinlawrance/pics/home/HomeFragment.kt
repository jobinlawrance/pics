package com.jobinlawrance.pics.home

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.jobinlawrance.pics.R
import com.jobinlawrance.pics.application.MyApplication
import com.jobinlawrance.pics.extras.Utils.Companion.inflate
import com.jobinlawrance.pics.home.dagger.DaggerHomeComponent
import com.jobinlawrance.pics.utils.getActionBarSize
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_home.*
import timber.log.Timber


/**
 * Created by jobinlawrance on 7/9/17.
 * ref - https://github.com/nickbutcher/plaid/blob/e6716fb4553ce74e99009904bbcaa02c5d5598b5/app/src/main/java/io/plaidapp/ui/HomeActivity.java
 */
class HomeFragment : MviFragment<HomeContract.View, HomeContract.Presenter>(), HomeContract.View {

    lateinit var homeAdapter: HomeAdapter
    lateinit var gridLayoutManager: GridLayoutManager

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeAdapter = HomeAdapter()
        gridLayoutManager = GridLayoutManager(activity, resources.getInteger(R.integer.grid_span_size))
        return container?.inflate(R.layout.fragment_home)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        windowInsetFrameLayout.setOnApplyWindowInsetsListener { view, windowInsets ->

            // inset the toolbar down by the status bar height
            val lpToolbar = toolbar
                    .layoutParams as ViewGroup.MarginLayoutParams
            lpToolbar.topMargin += windowInsets.systemWindowInsetTop
            lpToolbar.leftMargin += windowInsets.systemWindowInsetLeft
            lpToolbar.rightMargin += windowInsets.systemWindowInsetRight
            toolbar.layoutParams = lpToolbar

            // inset the recyclerView top by statusbar+toolbar & the bottom by the navbar (don't clip)
            recyclerView.setPadding(
                    recyclerView.paddingLeft + windowInsets.systemWindowInsetLeft, // landscape
                    windowInsets.systemWindowInsetTop + getActionBarSize(activity),
                    recyclerView.paddingRight + windowInsets.systemWindowInsetRight, // landscape
                    recyclerView.paddingBottom + windowInsets.systemWindowInsetBottom)

            // clear this listener so insets aren't re-applied
            windowInsetFrameLayout.setOnApplyWindowInsetsListener(null)

            windowInsets.consumeStableInsets()
        }

        recyclerView.adapter = homeAdapter
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.addOnScrollListener(toolbarElevation)
    }

    override fun createPresenter(): HomeContract.Presenter {
        return DaggerHomeComponent.builder()
                .appComponent((activity.application as MyApplication).getAppComponent())
                .build()
                .providePresenter()
    }

    override fun loadingFirstPageIntent(): Observable<Boolean> = Observable.just(true).doOnComplete { Timber.d("First Page Loaded") }

    override fun render(viewState: HomeViewState) {
        Timber.d("render : %s", viewState)

        if (viewState.loadingFirstPage) {
            renderProgress()
        } else if (viewState.firstPageError != null) {
            renderFirstPageError(viewState.firstPageError)
        } else {
            renderData(viewState)
        }
    }

    private fun renderProgress() {
        progressBar.visibility = View.VISIBLE
    }

    private fun renderFirstPageError(throwable: Throwable) {

    }

    private fun renderData(viewState: HomeViewState) {
        progressBar.visibility = View.GONE
        homeAdapter.setItems(viewState.data)
    }

    private val toolbarElevation = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
            // we want the grid to scroll over the top of the toolbar but for the toolbar items
            // to be clickable when visible. To achieve this we play games with elevation. The
            // toolbar is laid out in front of the grid but when we scroll, we lower it's elevation
            // to allow the content to pass in front (and reset when scrolled to top of the grid)
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && gridLayoutManager.findFirstVisibleItemPosition() == 0
                    && gridLayoutManager.findViewByPosition(0).top == recyclerView?.paddingTop
                    && toolbar.translationZ != 0f) {
                // at top, reset elevation
                toolbar.translationZ = 0f
            } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING && toolbar.translationZ != -1f) {
                // grid scrolled, lower toolbar to allow content to pass in front
                toolbar.translationZ = -1f
            }
        }
    }
}