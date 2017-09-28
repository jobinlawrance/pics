package com.jobinlawrance.pics.ui.home

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.jobinlawrance.pics.ui.MviView
import io.reactivex.Observable


/**
 * Created by jobinlawrance on 7/9/17.
 */
interface HomeContract {
    interface View : MviView<HomeViewState> {
        /*
        *  Here we define each intent as Rx Observables
        */
        fun loadingFirstPageIntent(): Observable<Boolean>

        fun networkStateIntent(): Observable<Boolean>
    }

    interface Interactor {
        fun getPictures(): Observable<PartialStateChanges>
        fun viewStateReducer(previousState: HomeViewState, partialStateChanges: PartialStateChanges): HomeViewState
    }

    abstract class Presenter : MviBasePresenter<View, HomeViewState>()
}