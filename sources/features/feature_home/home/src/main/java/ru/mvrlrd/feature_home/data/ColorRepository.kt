package ru.mvrlrd.feature_home.data

import ru.mvrlrd.feature_home.R
import javax.inject.Inject

class ColorRepository @Inject constructor() {
    val colors: List<Int> = listOf(
        R.color.one,
        R.color.two,
        R.color.three,
        R.color.four,
        R.color.five,
        R.color.six,
        R.color.seven,
        R.color.eight
    )
}