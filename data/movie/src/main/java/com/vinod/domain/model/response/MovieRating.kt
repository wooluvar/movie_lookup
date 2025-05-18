package com.vinod.domain.model.response

import com.google.gson.annotations.SerializedName

data class MovieRating(
    @SerializedName("Source") val source: String = "",
    @SerializedName("Value") val value: String = ""
)
