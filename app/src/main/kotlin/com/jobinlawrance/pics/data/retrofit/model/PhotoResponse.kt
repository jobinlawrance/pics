package com.jobinlawrance.pics.data.retrofit.model

import android.os.Parcel
import android.os.Parcelable
import paperparcel.PaperParcel

@PaperParcel
data class PhotoResponse(
        val urls: Urls? = null,
        val updatedAt: String? = null,
        val color: String? = null,
        val width: Int? = null,
        val createdAt: String? = null,
        val links: Links? = null,
        val id: String? = null,
        val likedByUser: Boolean? = null,
        val user: User? = null,
        val height: Int? = null,
        val likes: Int? = null
) : Parcelable {
    companion object {
        @JvmField
        val CREATOR = PaperParcelPhotoResponse.CREATOR
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        PaperParcelPhotoResponse.writeToParcel(this, dest, flags)
    }
}
