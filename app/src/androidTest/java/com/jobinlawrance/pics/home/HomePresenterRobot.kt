package com.jobinlawrance.pics.home

import io.reactivex.subjects.PublishSubject

/**
 * Created by jobinlawrance on 24/9/17.
 */
class HomePresenterRobot : HomeContract.Presenter() {

    private val viewState = PublishSubject.create<HomeViewState>()

    override fun bindIntents() {
        subscribeViewState(viewState, HomeContract.View::render)
    }

    fun customRender(homeViewState: HomeViewState) {
        viewState.onNext(homeViewState)
    }
}