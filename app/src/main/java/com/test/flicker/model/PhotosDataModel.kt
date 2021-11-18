package com.test.flicker.model

import com.google.gson.annotations.SerializedName

data class PhotosDataModel(
    @SerializedName("photos") val photos: Photos,
)

data class Photos(
    @SerializedName("photo") val photo: List<Photo>,
)

data class Photo(
    @SerializedName("id") val id: String,
    @SerializedName("secret") val secret: String,
    @SerializedName("server") val server: String,
    @SerializedName("title") val title: String
)