package com.jobinlawrance.pics.ui.detail


/**
 * Created by jobinlawrance on 1/10/17.
 */
class DetailPresenterImpl : DetailContract.Presenter() {
    override fun bindIntents() {
        val photoResponseObservable = intent(DetailContract.View::loadDetailsIntent)
        subscribeViewState(photoResponseObservable, DetailContract.View::render)
    }
}