package com.jobinlawrance.pics.data.retrofit.services

import com.jobinlawrance.downloadprogressinterceptor.DOWNLOAD_IDENTIFIER_HEADER
import com.jobinlawrance.pics.data.retrofit.model.PhotoResponse
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * Created by jobinlawrance on 5/9/17.
 */
interface PhotoService {

    @GET("photos")
    fun getPhotos(@Query("page") page: Int = 1): Observable<List<PhotoResponse>>

    @GET
    @Streaming
    fun downloadPhoto(@Url url: String, @Header(DOWNLOAD_IDENTIFIER_HEADER) identifier: String): Observable<ResponseBody>

}