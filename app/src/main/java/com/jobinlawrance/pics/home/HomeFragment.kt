package com.jobinlawrance.pics.home

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.jobinlawrance.pics.R
import com.jobinlawrance.pics.application.MyApplication
import com.jobinlawrance.pics.extras.Utils.Companion.inflate
import com.jobinlawrance.pics.home.dagger.DaggerHomeComponent
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_home.*
import timber.log.Timber

/**
 * Created by jobinlawrance on 7/9/17.
 */
class HomeFragment : MviFragment<HomeContract.View, HomeContract.Presenter>(), HomeContract.View {

    lateinit var homeAdapter: HomeAdapter
    lateinit var gridLayoutManager: GridLayoutManager

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeAdapter = HomeAdapter()
        gridLayoutManager = GridLayoutManager(activity, 1)

        return container?.inflate(R.layout.fragment_home)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = homeAdapter
        recyclerView.layoutManager = gridLayoutManager
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
}