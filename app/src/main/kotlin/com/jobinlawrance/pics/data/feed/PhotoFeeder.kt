package com.jobinlawrance.pics.data.feed

import com.jobinlawrance.pics.data.retrofit.model.PhotoResponse
import io.reactivex.Observable

/**
 * Created by jobinlawrance on 18/11/17.
 */
interface PhotoFeeder {
    fun getFirstPage(): Observable<List<PhotoResponse>>
    fun getNextPage(): Observable<List<PhotoResponse>>
}