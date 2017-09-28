package com.jobinlawrance.pics.data.retrofit.services

import com.jobinlawrance.pics.data.retrofit.model.PhotoResponse
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by jobinlawrance on 5/9/17.
 */
interface PhotoService {
    @GET("photos")
    fun getPhotos(): Observable<List<PhotoResponse>>
}