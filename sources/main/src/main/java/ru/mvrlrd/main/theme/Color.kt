package ru.mvrlrd.main.theme



import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

object AppColors{
    val darkGray = Color(0xFF222629)
    val gray = Color(0xFF474B4F)
    val salad = Color(0xFF86C232)

    val seaWave = Color(0xFF3AAFA9)
    val sky = Color(0xFFDEF2F1)
    val cloud = Color(0xFFFEFFFF)

}

interface ColorPalette {
    val primary: Color //buttons
    val secondary: Color // // Второстепенный цвет, может использоваться для элементов, таких как всплывающие меню
    val surface: Color// для карточек или диалогов
    val error: Color     // для сообщений об ошибках, таких как красные уведомления
    val onSurface: Color //  текста на элементах с поверхностным цветом
    val materialColors: ColorScheme
}

fun lightColorPalette(): ColorPalette = object : ColorPalette {
    override val primary: Color
        get() = AppColors.sky
    override val secondary: Color
        get() = AppColors.seaWave
    override val surface: Color
        get() =  AppColors.cloud
    override val error: Color
        get() = Color.Red
    override val onSurface: Color
        get() = AppColors.darkGray


    override val materialColors: ColorScheme = lightColorScheme(
        primary = primary,
        secondary = secondary,
        surface = surface,
        error = error,
        onSurface = onSurface,
    )
}

fun darkColorPalette(): ColorPalette = object : ColorPalette {
    override val primary: Color
        get() = AppColors.darkGray
    override val secondary: Color
        get() = AppColors.salad
    override val surface: Color
        get() =  AppColors.gray
    override val error: Color
        get() = Color.Red
    override val onSurface: Color
        get() = AppColors.cloud

    override val materialColors: ColorScheme = darkColorScheme(
        primary = primary,
        secondary = secondary,
        surface = surface,
        error = error,
        onSurface = onSurface,
    )

}