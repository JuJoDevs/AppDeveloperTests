package com.jujodevs.appdevelopertests.ui.screens.userdetail

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val FormatFYear = "yyyy-MM-dd"
private const val FormatFDay = "dd-MM-yyyy"

fun Date.formatterDate(formatter: String = FormatFYear): String =
    SimpleDateFormat(formatter, Locale.getDefault()).format(this)

fun String.toDate(formatter: String = FormatFYear): Date? =
    try {
        SimpleDateFormat(formatter, Locale.getDefault()).parse(this)
    } catch (_: Exception) {
        null
    }

fun String.changeFormatDate(
    formatterInit: String = FormatFYear,
    formatterResult: String = FormatFDay
): String =
    this.toDate(formatterInit)?.formatterDate(formatterResult) ?: this
