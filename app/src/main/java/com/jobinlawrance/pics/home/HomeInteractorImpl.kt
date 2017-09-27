package com.jobinlawrance.pics.home

import com.jobinlawrance.pics.retrofit.services.PhotoService
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by jobinlawrance on 7/9/17.
 */
//TODO("use kotlin allOpen instead of declaring the class open")
open class HomeInteractorImpl @Inject constructor(val photoService: PhotoService) : HomeContract.Interactor {

    override fun getPictures(): Observable<PartialStateChanges> {
        return photoService.getPhotos()
                .map { PartialStateChanges.FirstPageLoaded(it) as PartialStateChanges }
                .startWith(PartialStateChanges.FirstPageLoading)
                .onErrorReturn { PartialStateChanges.FirstPageError(it) }
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
        }
    }

    fun getPhotos(): Observable<PartialStateChanges> = Observable.error(Throwable("Blo"))
}