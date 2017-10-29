package com.jobinlawrance.pics.ui.detail

import android.text.format.DateFormat
import com.jobinlawrance.pics.PicToDownload
import com.jobinlawrance.pics.businesslogic.download.DownloadInteractor
import com.jobinlawrance.pics.businesslogic.download.DownloadService
import com.jobinlawrance.pics.utils.plusAssign
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import java.util.*


/**
 * Created by jobinlawrance on 1/10/17.
 */
class DetailPresenterImpl(val downloadInteractor: DownloadInteractor) : DetailContract.Presenter() {

    /**
     * we use replay() to make the observable Connectable observable
     * This prevent creating the service each time subscription happens and replays previous emission to new subscribers
     * In our case the [DownloadService] is created once when first subscriber subscribes
     *
     * refcount() converts Connectable Observable back to normal observable and disposes only when all it's consumer dispose
     */
    val downloadServiceObservable = downloadInteractor.getService()
            .replay()
            .refCount()

    val compositeDisposable = CompositeDisposable()

    var picToDownload: PicToDownload? = null


    override fun bindIntents() {
        val photoResponseObservable = intent(DetailContract.View::loadDetailsIntent)
                .doOnNext {
                    val username = it.user?.name ?: "Unsplash"
                    val createdAt = it.createdAt ?: DateFormat.format("dd-MM-yyyy", Date())
                    picToDownload = PicToDownload(it.id!!, "$username($createdAt)", it.urls!!.raw!!)
                }
                .map { PartialStateChanges.PhotoResponse(it) }

        compositeDisposable +=
                intent(DetailContract.View::downloadPic)
                        .flatMap { p -> downloadServiceObservable.map { it.addToDownloadQueue(picToDownload!!) } }
                        .subscribe()

        val downloadStatusIntent =
                intent(DetailContract.View::getDownloadStatus)
                        .doOnNext { Timber.d("Requesting download status of $it") }
                        .flatMap { identifier -> downloadServiceObservable.flatMap { it.getDownloadProgress(identifier) } }
                        .map { PartialStateChanges.DownloadProgress(it) }

//        compositeDisposable +=
//                intent(DetailContract.View::getDownloadStatus)
//                        .doOnNext{t-> Timber.d("Requesting status - $t")}
//                        .subscribe()

        val initialState = DetailViewState.Builder().setLoadingPhotoResponse(true).build()

        val allIntents =
                Observable.merge(photoResponseObservable, downloadStatusIntent)
                        .scan(initialState, this::reducer)

        subscribeViewState(allIntents, DetailContract.View::render)
    }

    override fun unbindIntents() {
        super.unbindIntents()
        compositeDisposable.dispose()
    }

    sealed class PartialStateChanges {
        object Loading : PartialStateChanges()
        class PhotoResponse(val response: com.jobinlawrance.pics.data.retrofit.model.PhotoResponse) : PartialStateChanges()
        class DownloadProgress(val progress: Int) : PartialStateChanges()
    }

    fun reducer(previousState: DetailViewState, partialChange: PartialStateChanges): DetailViewState {

        when (partialChange) {

            PartialStateChanges.Loading -> return DetailViewState.Builder(previousState).setLoadingPhotoResponse(true).build()

            is PartialStateChanges.PhotoResponse ->
                return DetailViewState.Builder(previousState)
                        .setLoadingPhotoResponse(false)
                        .setPhotoResponse(partialChange.response)
                        .build()

            is PartialStateChanges.DownloadProgress ->
                return DetailViewState.Builder(previousState)
                        .setIsDownloading(true)
                        .setDownloadProgress(partialChange.progress)
                        .build()
        }
    }
}