package com.jobinlawrance.pics.data.feed

import com.jobinlawrance.pics.data.retrofit.model.PhotoResponse
import com.jobinlawrance.pics.data.retrofit.services.PhotoService
import io.reactivex.Observable
import java.util.*

/**
 * Created by jobinlawrance on 18/11/17.
 */
class PhotoFeederImpl(val photoService: PhotoService) : PhotoFeeder {

    private var nextPage: Int = 2
    private var endReached = false

    override fun getFirstPage(): Observable<List<PhotoResponse>> =
            photoService.getPhotos(1)

    override fun getNextPage(): Observable<List<PhotoResponse>> {
        return if (endReached)
            Observable.just(Collections.emptyList())
        else
            photoService
                    .getPhotos(nextPage)
                    .doOnNext {
                        nextPage++
                        if (it.isEmpty())
                            endReached = true
                    }
    }
}