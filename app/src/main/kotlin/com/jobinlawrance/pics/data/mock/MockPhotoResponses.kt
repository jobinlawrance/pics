package com.jobinlawrance.pics.data.mock

import com.jobinlawrance.pics.data.retrofit.model.PhotoResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

/**
 * Created by jobinlawrance on 8/9/17.
 */
class MockPhotoResponses {
    companion object {

        private val moshi = Moshi.Builder().build()
        private val type = Types.newParameterizedType(List::class.java, PhotoResponse::class.java)
        private val adapter = moshi.adapter<List<PhotoResponse>>(type)

        @JvmStatic
        fun asList(jsonString: String) = adapter.fromJson(jsonString)
    }
}