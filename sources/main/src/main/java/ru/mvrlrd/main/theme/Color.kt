package ru.mvrlrd.main.theme



import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

object AppColors{
    val darkBlue = Color(0xFF171925)
    val gray = Color(0xFF333541)
    val purple = Color(0xFF7908AA)

    val gree = Color(0xFF3AAFA9)
    val white1 = Color(0xFFDEF2F1)
    val white2 = Color(0xFFFEFFFF)


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
    val error: Color     // для сообщений об ошибках, таких как красные уведомления
    val onPrimary : Color   //  текста на основном фоне, контрастирует с основным цветом
    val onSecondary: Color //  текста на второстепенном фоне, контрастирует со второстепенным цветом
    val onBackground: Color //  текста на фоне всего приложения
    val onSurface: Color //  текста на элементах с поверхностным цветом
    val onError: Color //   текста на элементах с ошибками
    val materialColors: ColorScheme
}

fun lightColorPalette(): ColorPalette = object : ColorPalette {
    override val primary: Color
        get() = AppColors.white1
    override val primaryVariant: Color
        get() = AppColors.peach
    override val secondary: Color
        get() = AppColors.gree
    override val background: Color
        get() = AppColors.light
    override val surface: Color
        get() =  AppColors.white2
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

    override val materialColors: ColorScheme = lightColorScheme(
        primary = primary,
        secondary = secondary,
        background = background,
        surface = surface,
        error = error,
        onPrimary = onPrimary,
        onSecondary = onSecondary,
        onBackground = onBackground,
        onSurface = onSurface,
        onError = onError,
    )
}

fun darkColorPalette(): ColorPalette = object : ColorPalette {
    override val primary: Color
        get() = AppColors.darkBlue //
    override val primaryVariant: Color
        get() = AppColors.peach
    override val secondary: Color
        get() = AppColors.purple //
    override val background: Color
        get() = AppColors.dark
    override val surface: Color
        get() =  AppColors.gray //
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

    override val materialColors: ColorScheme = darkColorScheme(
        primary = primary,
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