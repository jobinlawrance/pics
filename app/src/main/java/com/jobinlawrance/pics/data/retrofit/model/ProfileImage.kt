package com.jobinlawrance.pics.data.retrofit.model

import android.os.Parcel
import android.os.Parcelable
import paperparcel.PaperParcel

@PaperParcel
data class ProfileImage(
        val small: String? = null,
        val large: String? = null,
        val medium: String? = null
) : Parcelable {
    companion object {
        @JvmField
        val CREATOR = PaperParcelProfileImage.CREATOR
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        PaperParcelProfileImage.writeToParcel(this, dest, flags)
    }
}
