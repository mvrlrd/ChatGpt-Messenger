package ru.mvrlrd.main.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.mvrlrd.main.R

data class AppTypography internal constructor(
    val paragraph1: TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.marvel_regular)),
        fontSize = 15.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp
    ),
    val button: TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.marvel_regular)),
        fontWeight = FontWeight.SemiBold,
        fontSize = 36.sp,
        letterSpacing = 1.25.sp
    ),
    val body1: TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.marvel_regular)),
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        letterSpacing = 0.sp,
        lineHeight = 20.sp,
    ),
    val h4: TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.marvel_regular)),
        fontWeight = FontWeight.SemiBold,
        fontSize = 30.sp,
        letterSpacing = 0.sp,
    ),
    val materialTypography: Typography = Typography(

    ),////////разобраться здесь

)
