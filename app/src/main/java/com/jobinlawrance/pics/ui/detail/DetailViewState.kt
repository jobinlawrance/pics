package com.jobinlawrance.pics.ui.detail

import com.jobinlawrance.pics.data.retrofit.model.PhotoResponse
import com.jobinlawrance.pics.ui.detail.model.DownloadStatus

/**
 * Created by jobinlawrance on 6/10/17.
 */
class DetailViewState private constructor(
        val photoResponse: PhotoResponse?,
        val downloadStatus: DownloadStatus) {

    class Builder(var photoResponse: PhotoResponse? = null, var downloadStatus: DownloadStatus = DownloadStatus(false)) {
        constructor(toCopyFrom: DetailViewState) : this(toCopyFrom.photoResponse, toCopyFrom.downloadStatus)

        fun setPhotoResponse(photoResponse: PhotoResponse) = apply { this.photoResponse = photoResponse }
        fun setDownloadStatus(downloadStatus: DownloadStatus) = apply { this.downloadStatus = downloadStatus }

        fun build(): DetailViewState = DetailViewState(this.photoResponse, this.downloadStatus)
    }

    override fun toString(): String {
        return "DetailViewState{ \n" +
                "photoResponse = $photoResponse \n" +
                "downloadStatus = $downloadStatus \n" +
                "}"
    }
}