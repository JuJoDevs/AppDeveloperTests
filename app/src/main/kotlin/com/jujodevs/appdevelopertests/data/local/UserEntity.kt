package com.jujodevs.appdevelopertests.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [
        Index(value = ["email"], unique = true),
        Index(value = ["picture"], unique = true),
    ],
)
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val page: Int,
    val email: String,
    val name: String,
    val picture: String,
    val gender: String,
    val registered: String,
    val cell: String,
    val latitude: String,
    val longitude: String
)
