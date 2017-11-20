package com.jobinlawrance.pics.ui.home

import com.jobinlawrance.pics.data.retrofit.model.PhotoResponse

/**
 * Created by jobinlawrance on 7/9/17.
 */
sealed class PartialStateChanges {
    /**
     * Indicates that the first page loading has started
     */
    object FirstPageLoading : PartialStateChanges()

    /**
     * Indicates fist page loading threw an error
     */
    class FirstPageError(val throwable: Throwable) : PartialStateChanges()

    /**
     * Indicates first page has loaded successfully
     */
    class FirstPageLoaded(val data: List<PhotoResponse>) : PartialStateChanges()

    /**
     * Indicates next page loading has started
     */
    object NextPageLoading : PartialStateChanges()

    /**
     * Indicates next page loading threw an error
     */
    class NextPageError(val throwable: Throwable) : PartialStateChanges()

    /**
     * Indicates next page loading was successful
     */
    class NexPageLoaded(val data: List<PhotoResponse>) : PartialStateChanges()
}