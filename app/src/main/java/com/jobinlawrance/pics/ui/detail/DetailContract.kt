package com.jobinlawrance.pics.ui.detail

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.jobinlawrance.pics.data.retrofit.model.PhotoResponse
import com.jobinlawrance.pics.ui.MviView
import io.reactivex.Observable

/**
 * Created by jobinlawrance on 1/10/17.
 */
interface DetailContract {
    interface View : MviView<PhotoResponse> {
        fun loadDetailsIntent(): Observable<PhotoResponse>
    }

    abstract class Presenter : MviBasePresenter<View, PhotoResponse>()
}