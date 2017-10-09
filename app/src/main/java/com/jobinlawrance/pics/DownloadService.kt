package com.jobinlawrance.pics

import io.reactivex.Observable

/**
 * Created by jobinlawrance on 6/10/17.
 */
interface DownloadService {
    fun addToDownloadQueue(picToDownload: PicToDownload)
    fun getDownloadProgress(identifier: String): Observable<Int>
}