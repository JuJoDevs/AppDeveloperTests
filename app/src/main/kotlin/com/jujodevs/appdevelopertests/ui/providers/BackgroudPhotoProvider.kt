@file:Suppress("MagicNumber")

package com.jujodevs.appdevelopertests.ui.providers

import com.jujodevs.appdevelopertests.R

object BackgroudPhotoProvider {
    fun getPhoto(number: Int) = when (number) {
        0 -> R.drawable.photo0
        1 -> R.drawable.photo1
        2 -> R.drawable.photo2
        3 -> R.drawable.photo3
        4 -> R.drawable.photo4
        5 -> R.drawable.photo5
        6 -> R.drawable.photo6
        7 -> R.drawable.photo7
        8 -> R.drawable.photo8
        9 -> R.drawable.photo9
        else -> R.drawable.photo0
    }
}
