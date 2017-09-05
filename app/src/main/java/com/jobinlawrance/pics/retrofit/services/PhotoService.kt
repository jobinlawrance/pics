package com.jobinlawrance.pics.retrofit.services

import com.jobinlawrance.pics.retrofit.data.PhotoResponse
import io.reactivex.Single
import retrofit2.http.GET

/**
 * Created by jobinlawrance on 5/9/17.
 */
interface PhotoService {
    @GET("photos")
    fun getPhotos(): Single<List<PhotoResponse>>
}