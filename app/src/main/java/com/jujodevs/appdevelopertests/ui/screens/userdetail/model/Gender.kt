package com.jujodevs.appdevelopertests.ui.screens.userdetail.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.ui.graphics.vector.ImageVector

enum class Gender(val id: String, val showText: String, val icon: ImageVector) {
    MALE("male", "Hombre", Icons.Default.Male),
    FEMALE("female", "Mujer", Icons.Default.Female);
}

fun getGender(id: String): Gender {
    return when (id) {
        Gender.MALE.id -> Gender.MALE
        Gender.FEMALE.id -> Gender.FEMALE
        else -> Gender.MALE
    }
}
