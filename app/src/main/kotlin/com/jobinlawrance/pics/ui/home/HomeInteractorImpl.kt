package com.jobinlawrance.pics.ui.home

import com.jobinlawrance.pics.data.feed.PhotoFeeder
import com.jobinlawrance.pics.data.retrofit.model.PhotoResponse
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by jobinlawrance on 7/9/17.
 */
//TODO("use kotlin allOpen instead of declaring the class open")
open class HomeInteractorImpl @Inject constructor(val photoFeeder: PhotoFeeder) : HomeContract.Interactor {

    override fun loadFirstPage(): Observable<PartialStateChanges> {
        return photoFeeder.getFirstPage()
                .map { PartialStateChanges.FirstPageLoaded(it) as PartialStateChanges }
                .startWith(PartialStateChanges.FirstPageLoading)
                .onErrorReturn { PartialStateChanges.FirstPageError(it) }
    }

    override fun loadNextPage(): Observable<PartialStateChanges> {
        return photoFeeder.getNextPage()
                .map { PartialStateChanges.NexPageLoaded(it) as PartialStateChanges }
                .startWith(PartialStateChanges.NextPageLoading)
                .onErrorReturn { PartialStateChanges.NextPageError(it) }
    }

    override fun viewStateReducer(previousState: HomeViewState, partialStateChanges: PartialStateChanges): HomeViewState {
        when (partialStateChanges) {
            PartialStateChanges.FirstPageLoading ->
                return previousState.builder()
                        .firstPageLoading(true)
                        .firstPageError(null)
                        .build()

            is PartialStateChanges.FirstPageError ->
                return previousState.builder()
                        .firstPageLoading(false)
                        .firstPageError(partialStateChanges.throwable)
                        .build()

            is PartialStateChanges.FirstPageLoaded ->
                return previousState.builder()
                        .firstPageLoading(false)
                        .firstPageError(null)
                        .data(partialStateChanges.data)
                        .build()

            is PartialStateChanges.NextPageLoading ->
                return previousState.builder()
                        .nextPageLoading(true)
                        .nextPageError(null)
                        .build()

            is PartialStateChanges.NextPageError ->
                return previousState.builder()
                        .nextPageLoading(false)
                        .nextPageError(partialStateChanges.throwable)
                        .build()

            is PartialStateChanges.NexPageLoaded -> {
                val newList = ArrayList<PhotoResponse>(previousState.data.size + partialStateChanges.data.size)
                newList.addAll(previousState.data)
                newList.addAll(partialStateChanges.data)

                return previousState.builder()
                        .nextPageLoading(false)
                        .nextPageError(null)
                        .data(newList)
                        .build()
            }
        }
    }

}