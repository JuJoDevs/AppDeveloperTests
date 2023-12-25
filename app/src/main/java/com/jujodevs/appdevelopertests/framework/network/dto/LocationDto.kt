package com.jujodevs.appdevelopertests.framework.network.dto

import com.google.gson.annotations.SerializedName

data class LocationDto(
    @SerializedName("coordinates")
    val coordinates: CoordinatesDto
)
