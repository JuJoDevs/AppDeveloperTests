package com.jujodevs.appdevelopertests.data.network.dto

import com.google.gson.annotations.SerializedName

data class LocationDto(
    @SerializedName("coordinates")
    val coordinates: CoordinatesDto
)
