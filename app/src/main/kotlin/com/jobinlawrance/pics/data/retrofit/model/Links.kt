package com.jobinlawrance.pics.data.retrofit.model

import android.os.Parcel
import android.os.Parcelable
import paperparcel.PaperParcel

@PaperParcel
data class Links(
        val followers: String? = null,
        val portfolio: String? = null,
        val following: String? = null,
        val self: String? = null,
        val html: String? = null,
        val photos: String? = null,
        val likes: String? = null
) : Parcelable {
    companion object {
        @JvmField
        val CREATOR = PaperParcelLinks.CREATOR
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        PaperParcelLinks.writeToParcel(this, dest, flags)
    }
}