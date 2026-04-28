package com.buller.ckkal.ui

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.buller.ckkal.R

object TypographySetup {
    private val montserratFontFamily = FontFamily(
        Font(R.font.montserrat_regular),
        Font(R.font.montserrat_light),
        Font(R.font.montserrat_medium),
        Font(R.font.montserrat_semibold)
    )

    val typography = Typography(
        titleMedium = TextStyle(
            fontFamily = montserratFontFamily,
            fontWeight = FontWeight.SemiBold
        ),
        titleLarge = TextStyle(fontFamily = montserratFontFamily, fontWeight = FontWeight.Bold, fontSize = 20.sp),
        bodyMedium = TextStyle(fontFamily = montserratFontFamily, fontWeight = FontWeight.Normal, fontSize = 16.sp),
        bodySmall = TextStyle(fontFamily = montserratFontFamily, fontWeight = FontWeight.Light),
    )
}