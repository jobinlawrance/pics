package com.jobinlawrance.pics.ui.home

import com.jobinlawrance.pics.data.retrofit.model.PhotoResponse
import java.util.*

/**
 * Created by jobinlawrance on 7/9/17.
 */
class HomeViewState private constructor(
        val loadingFirstPage: Boolean,
        val firstPageError: Throwable?,
        val data: List<PhotoResponse>
) {
    fun builder() = Builder(toCopyFrom = this)

    class Builder(var loadingFirstPage: Boolean = false,
                  var firstPageError: Throwable? = null,
                  var data: List<PhotoResponse> = Collections.emptyList()) {

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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        val that = other as HomeViewState

        if (loadingFirstPage != that.loadingFirstPage) return false

        if (firstPageError?.javaClass != that.firstPageError?.javaClass) return false

        return data.equals(other.data)

    }

    override fun hashCode(): Int {
        var result = loadingFirstPage.hashCode()
        result = 31 * result + (firstPageError?.hashCode() ?: 0)
        result = 31 * result + data.hashCode()
        return result
    }


}