package ru.mvrlrd.companion.pull.pullrefresh

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Random
import javax.inject.Inject


class ColorItemDataSource @Inject constructor() {

    private val defaultList = mutableListOf(
        generateColor(),
        generateColor(),
        generateColor(),
        generateColor(),
        generateColor(),
        generateColor(),
        generateColor(),
        generateColor(),
    )

    fun getColorList(): Flow<Result<List<Color>>> = flow {
        emit(Result.Loading)
        val colorList = mutableListOf<Color>()
        // uncomment this if you want to simulate item being added during refresh
        // defaultList.add(0, generateColor())
        colorList.addAll(defaultList)
        delay(1000)
        emit(Result.Success(colorList))
    }

    private fun generateColor(): Color {
        val rnd = Random()
        val color =
            android.graphics.Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        return Color(color)
    }
}