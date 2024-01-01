package com.jujodevs.appdevelopertests.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jujodevs.appdevelopertests.R

val oswald = FontFamily(
    Font(R.font.oswald_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.oswald_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.oswald_light, FontWeight.Light, FontStyle.Normal),
    Font(R.font.oswald_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.oswald_semibold, FontWeight.SemiBold, FontStyle.Normal),
    Font(R.font.oswald_extra_light, FontWeight.ExtraLight, FontStyle.Normal),
)

val opensans = FontFamily(
    Font(R.font.opensans_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.opensans_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.opensans_light, FontWeight.Light, FontStyle.Normal),
    Font(R.font.opensans_semibold, FontWeight.SemiBold, FontStyle.Normal),
    Font(R.font.opensans_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.opensans_extrabold, FontWeight.ExtraBold, FontStyle.Normal),
    Font(R.font.opensans_lightitalic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.opensans_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.opensans_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.opensans_bolditalic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.opensans_extrabolditalic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(R.font.opensans_mediumitalic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.opensans_lightitalic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.opensans_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.opensans_mediumitalic, FontWeight.Medium, FontStyle.Italic),
)

// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge = TextStyle(
        fontSize = 16.sp,
        fontFamily = oswald,
        fontWeight = FontWeight.Medium,
    ),
    titleMedium = TextStyle(
        fontSize = 16.sp,
        fontFamily = opensans,
        fontWeight = FontWeight.SemiBold,
    ),
    bodySmall = TextStyle(
        fontSize = 14.sp,
        fontFamily = opensans,
        fontWeight = FontWeight.Normal,
        color = LightGray,
    ),
    labelSmall = TextStyle(
        fontSize = 11.sp,
        fontFamily = opensans,
        fontWeight = FontWeight.Normal,
        color = LightGray,
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),
)
