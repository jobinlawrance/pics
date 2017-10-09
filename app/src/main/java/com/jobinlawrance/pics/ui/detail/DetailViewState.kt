package com.jobinlawrance.pics.ui.detail

import com.jobinlawrance.pics.data.retrofit.model.PhotoResponse

/**
 * Created by jobinlawrance on 6/10/17.
 */
class DetailViewState private constructor(
        val loadingPhotoResponse: Boolean,
        val photoResponse: PhotoResponse?,
        val isDownloading: Boolean,
        val downloadProgress: Int) {

    class Builder private constructor(private var loadingPhotoResponse: Boolean,
                                      private var photoResponse: PhotoResponse?,
                                      private var isDownloading: Boolean,
                                      private var downloadProgress: Int) {

        constructor() : this(false, null, false, 0)

        constructor(dVS: DetailViewState) :
                this(dVS.loadingPhotoResponse, dVS.photoResponse, dVS.isDownloading, dVS.downloadProgress)

        fun setLoadingPhotoResponse(loadingPhotoResponse: Boolean) = apply { this.loadingPhotoResponse = loadingPhotoResponse }
        fun setPhotoResponse(photoResponse: PhotoResponse) = apply { this.photoResponse = photoResponse }
        fun setIsDownloading(isDownloading: Boolean) = apply { this.isDownloading }
        fun setDownloadProgress(progress: Int) = apply { this.downloadProgress = progress }


        fun build(): DetailViewState =
                DetailViewState(this.loadingPhotoResponse, this.photoResponse, this.isDownloading, this.downloadProgress)
    }

    override fun toString(): String {
        return "DetailViewState { \n" +
                "isLoadingPhotoResponse = $loadingPhotoResponse\n" +
                "photoResponse = $photoResponse \n" +
                "isDownloading = $isDownloading \n" +
                "downloadProgress = $downloadProgress \n" +
                "}"
    }
}