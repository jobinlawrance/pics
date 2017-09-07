package com.jobinlawrance.pics.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.jobinlawrance.pics.R
import com.jobinlawrance.pics.application.MyApplication
import com.jobinlawrance.pics.extras.Utils.Companion.inflate
import com.jobinlawrance.pics.home.dagger.DaggerHomeComponent
import io.reactivex.Observable
import timber.log.Timber

/**
 * Created by jobinlawrance on 7/9/17.
 */
class HomeFragment : MviFragment<HomeContract.View, HomeContract.Presenter>(), HomeContract.View {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_home)
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
    }
}