package com.jujodevs.appdevelopertests.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UsersDto(
    @SerializedName("results")
    val results: List<UserDto>,
    @SerializedName("info")
    val info: InfoDto
)
