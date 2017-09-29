package com.jobinlawrance.pics.data.retrofit.model

import android.os.Parcel
import android.os.Parcelable
import paperparcel.PaperParcel

@PaperParcel
data class Urls(
        val small: String? = null,
        val thumb: String? = null,
        val raw: String? = null,
        val regular: String? = null,
        val full: String? = null
) : Parcelable {
    companion object {
        @JvmField
        val CREATOR = PaperParcelUrls.CREATOR
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        PaperParcelUrls.writeToParcel(this, dest, flags)
    }
}
