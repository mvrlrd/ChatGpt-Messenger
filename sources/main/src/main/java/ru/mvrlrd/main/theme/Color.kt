package ru.mvrlrd.main.theme

import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

object AppColors{
    val dark = Color(0xFF2C3531)
    val green_dark = Color(0xFF116466)
    val peach = Color(0xFFD9B08C)
    val sand = Color(0xFFFFCB9A)
    val light = Color(0xFFD1E8E2)
    val red = Color.Red
}

interface ColorPalette {
    val primary: Color //buttons
    val primaryVariant: Color //for accent
    val secondary: Color // // Второстепенный цвет, может использоваться для элементов, таких как всплывающие меню
    val background: Color
    val surface: Color// для карточек или диалогов
    val error: Color     // Цвет для сообщений об ошибках, таких как красные уведомления
    val onPrimary : Color   // Цвет текста на основном фоне, контрастирует с основным цветом
    val onSecondary: Color // Цвет текста на второстепенном фоне, контрастирует со второстепенным цветом
    val onBackground: Color // Цвет текста на фоне всего приложения
    val onSurface: Color // Цвет текста на элементах с поверхностным цветом
    val onError: Color // // Цвет текста на элементах с ошибками
    val materialColors: Colors
}

fun lightColorPalette(): ColorPalette = object : ColorPalette {
    override val primary: Color
        get() = AppColors.green_dark
    override val primaryVariant: Color
        get() = AppColors.peach
    override val secondary: Color
        get() = AppColors.sand
    override val background: Color
        get() = AppColors.light
    override val surface: Color
        get() =  Color.Gray
    override val error: Color
        get() = AppColors.red
    override val onPrimary: Color
        get() = AppColors.dark
    override val onSecondary: Color
        get() = AppColors.dark
    override val onBackground: Color
        get() = AppColors.dark
    override val onSurface: Color
        get() = AppColors.dark
    override val onError: Color
        get() = AppColors.dark

    override val materialColors: Colors = lightColors(
        primary = primary,
        primaryVariant = primaryVariant,
        secondary = secondary,
        background = background,
        surface = surface,
        error = error,
        onPrimary = onPrimary,
        onSecondary = onSecondary,
        onBackground = onBackground,
        onSurface = onSurface,
        onError = onError
    )
}

fun darkColorPalette(): ColorPalette = object : ColorPalette {
    override val primary: Color
        get() = AppColors.green_dark
    override val primaryVariant: Color
        get() = AppColors.peach
    override val secondary: Color
        get() = AppColors.sand
    override val background: Color
        get() = AppColors.dark
    override val surface: Color
        get() =  Color.Gray
    override val error: Color
        get() = AppColors.red
    override val onPrimary: Color
        get() = AppColors.light
    override val onSecondary: Color
        get() = AppColors.dark
    override val onBackground: Color
        get() = AppColors.light
    override val onSurface: Color
        get() = AppColors.light
    override val onError: Color
        get() = AppColors.light

    override val materialColors: Colors = darkColors(
        primary = primary,
        primaryVariant = primaryVariant,
        secondary = secondary,
        background = background,
        surface = surface,
        error = error,
        onPrimary = onPrimary,
        onSecondary = onSecondary,
        onBackground = onBackground,
        onSurface = onSurface,
        onError = onError
    )

}