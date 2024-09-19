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
        letterSpacing = 1.25.sp,
    ),
    val body1: TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.marvel_regular)),
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        letterSpacing = 0.sp,
        lineHeight = 20.sp,
        color = AppColors.light
    ),
    val textInCloud: TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.marvel_regular)),
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        letterSpacing = 0.sp,
        lineHeight = 20.sp,
    ),
    val hint: TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.marvel_regular)),
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        letterSpacing = 0.1.sp,
    ),
    val h4: TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.marvel_regular)),
        fontWeight = FontWeight.SemiBold,
        fontSize = 30.sp,
        letterSpacing = 0.sp,
    ),
    val materialTypography: Typography = Typography(
        displayLarge = h4, // Вы используете h4 для крупного заголовка
        displayMedium = body1, // Использование body1 для среднего заголовка
        displaySmall = paragraph1, // Использование paragraph1 для небольшого заголовка
        headlineMedium = h4, // Использование h4 для заголовка среднего размера
        headlineSmall = body1, // Использование body1 для заголовка небольшого размера
        titleLarge = h4, // Использование h4 для крупного заголовка
        titleMedium = body1, // Использование body1 для заголовка среднего размера
        titleSmall = hint, // Использование paragraph1 для небольшого заголовка
        bodyLarge = body1, // Использование body1 для текста основного размера
        bodyMedium = textInCloud, // Использование paragraph1 для текста среднего размера
        bodySmall = paragraph1.copy(fontSize = 13.sp), // Использование paragraph1 для текста меньшего размера
        labelLarge = button, // Использование button для крупных меток (например, кнопок)
        labelMedium = button, // Использование button для средних меток
        labelSmall = button.copy(fontSize = 18.sp) // Использование button для небольших меток
    )

)
