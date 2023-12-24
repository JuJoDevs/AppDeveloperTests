package com.jujodevs.appdevelopertests.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("gender")
    val gender: String,
    @SerializedName("name")
    val name: NameDto,
    @SerializedName("location")
    val location: LocationDto,
    @SerializedName("email")
    val email: String,
    @SerializedName("registered")
    val registered: RegisteredDto,
    @SerializedName("cell")
    val cell: String,
    @SerializedName("picture")
    val picture: PictureDto
)
