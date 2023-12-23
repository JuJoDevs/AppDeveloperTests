package com.jujodevs.appdevelopertests.domain

data class User(
    val email: String = "",
    val name: String = "",
    val picture: String = "",
    val gender: String = "",
    val registered: String = "",
    val cell: String = "",
    val latitude: String = "",
    val longitude: String = ""
) {
    fun getPhotoNumber() =
        picture.split("/").last().split(".")[0].last().digitToIntOrNull() ?: 0
}
