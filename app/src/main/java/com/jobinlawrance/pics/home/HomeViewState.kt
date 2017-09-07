package com.jobinlawrance.pics.home

import com.jobinlawrance.pics.retrofit.data.PhotoResponse

/**
 * Created by jobinlawrance on 7/9/17.
 */
data class HomeViewState private constructor(
        val loadingFirstPage: Boolean,
        val firstPageError: Throwable?,
        val data: List<PhotoResponse>
) {
    fun builder() = Builder(toCopyFrom = this)

    class Builder(var loadingFirstPage: Boolean = false,
                  var firstPageError: Throwable? = null,
                  var data: List<PhotoResponse> = emptyList()) {

        constructor(toCopyFrom: HomeViewState) : this(toCopyFrom.loadingFirstPage, toCopyFrom.firstPageError, toCopyFrom.data)

        fun firstPageLoading(loadingFirstPage: Boolean) = apply { this.loadingFirstPage = loadingFirstPage }

        fun firstPageError(throwable: Throwable?) = apply { this.firstPageError = throwable }

        fun data(data: List<PhotoResponse>) = apply { this.data = data }

        fun build(): HomeViewState = HomeViewState(
                loadingFirstPage = this.loadingFirstPage,
                firstPageError = this.firstPageError,
                data = this.data
        )
    }

    override fun toString(): String {
        return "HomeViewState{" +
                "\nloadingFirstPage = $loadingFirstPage" +
                "\n firstPageError = $firstPageError" +
                "\n data = $data" +
                "\n}"
    }
}