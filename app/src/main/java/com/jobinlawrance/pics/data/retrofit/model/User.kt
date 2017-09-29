package com.jobinlawrance.pics.data.retrofit.model

import android.os.Parcel
import android.os.Parcelable
import paperparcel.PaperParcel

@PaperParcel
data class User(
        val totalPhotos: Int? = null,
        val twitterUsername: String? = null,
        val lastName: String? = null,
        val bio: String? = null,
        val totalLikes: Int? = null,
        val portfolioUrl: String? = null,
        val profileImage: ProfileImage? = null,
        val updatedAt: String? = null,
        val name: String? = null,
        val location: String? = null,
        val totalCollections: Int? = null,
        val links: Links? = null,
        val id: String? = null,
        val firstName: String? = null,
        val username: String? = null
) : Parcelable {
    companion object {
        @JvmField
        val CREATOR = PaperParcelUser.CREATOR
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        PaperParcelUser.writeToParcel(this, dest, flags)
    }
}
