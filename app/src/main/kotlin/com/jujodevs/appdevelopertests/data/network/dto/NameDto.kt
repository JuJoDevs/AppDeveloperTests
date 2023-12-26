package com.jujodevs.appdevelopertests.data.network.dto

import com.google.gson.annotations.SerializedName

data class NameDto(
    @SerializedName("first")
    val first: String,
    @SerializedName("last")
    val last: String
)
