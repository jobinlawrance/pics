package com.jobinlawrance.pics.ui.home

import android.util.Pair
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.jobinlawrance.pics.data.retrofit.model.PhotoResponse
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

        fun loadNextPageIntent(): Observable<Boolean>

        fun networkStateIntent(): Observable<Boolean>

        fun openDetails(photoResponse: PhotoResponse, sharedElementsPair: Pair<android.view.View, String>)
    }

    interface Interactor {
        fun loadFirstPage(): Observable<PartialStateChanges>
        fun loadNextPage(): Observable<PartialStateChanges>
        fun viewStateReducer(previousState: HomeViewState, partialStateChanges: PartialStateChanges): HomeViewState
    }

    abstract class Presenter : MviBasePresenter<View, HomeViewState>()
}