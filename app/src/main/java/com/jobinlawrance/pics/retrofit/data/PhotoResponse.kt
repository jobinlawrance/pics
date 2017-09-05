package com.jobinlawrance.pics.retrofit.data

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
)
