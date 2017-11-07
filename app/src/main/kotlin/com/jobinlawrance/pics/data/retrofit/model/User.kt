package com.jobinlawrance.pics.data.retrofit.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import paperparcel.PaperParcel

@PaperParcel
data class User(
        val totalPhotos: Int? = null,
        @field:Json(name = "twitter_username") val twitterUsername: String? = null,
        @field:Json(name = "last_name") val lastName: String? = null,
        val bio: String? = null,
        @field:Json(name = "total_likes") val totalLikes: Int? = null,
        @field:Json(name = "portfolio_url") val portfolioUrl: String? = null,
        @field:Json(name = "profile_image") val profileImage: ProfileImage? = null,
        @field:Json(name = "updated_at") val updatedAt: String? = null,
        val name: String? = null,
        val location: String? = null,
        @field:Json(name = "total_collections") val totalCollections: Int? = null,
        val links: Links? = null,
        val id: String? = null,
        @field:Json(name = "first_name") val firstName: String? = null,
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
