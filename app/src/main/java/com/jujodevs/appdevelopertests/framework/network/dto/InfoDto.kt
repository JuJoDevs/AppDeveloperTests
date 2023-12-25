package com.jujodevs.appdevelopertests.framework.network.dto

import com.google.gson.annotations.SerializedName

data class InfoDto(
    @SerializedName("results")
    val results: Int,
    @SerializedName("page")
    val page: Int
)
