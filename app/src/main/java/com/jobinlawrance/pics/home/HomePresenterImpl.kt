package com.jobinlawrance.pics.home

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by jobinlawrance on 7/9/17.
 */
class HomePresenterImpl @Inject constructor(private val interactor: HomeContract.Interactor) : HomeContract.Presenter() {

    override fun bindIntents() {

        fun loadFirstPage(): Observable<PartialStateChanges> = intent(HomeContract.View::loadingFirstPageIntent)
                .doOnNext { Timber.d("intent: Load First Page") }
                .flatMap { interactor.getPictures() }
                .observeOn(AndroidSchedulers.mainThread())

        val initialState = HomeViewState.Builder().firstPageLoading(true).build()

        subscribeViewState(
                loadFirstPage().scan(initialState, interactor::viewStateReducer).distinctUntilChanged(),
                HomeContract.View::render
        )
    }

}