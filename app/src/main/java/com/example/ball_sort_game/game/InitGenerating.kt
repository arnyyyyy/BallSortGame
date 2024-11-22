package com.example.ball_sort_game.game

import androidx.compose.ui.graphics.Color
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.random.Random

fun generateRandomColorOneBall(): Color {
    return Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
}

fun generateRandomColor(count: Int): List<Color> {
    val colors = mutableListOf<Color>()
    val step = 360 / count

    for (i in 0 until count) {
        val hue = (i * step) % 360
        colors.add(hslToColor(hue.toFloat(), 0.8f, 0.6f))
    }

    return colors.shuffled(Random)
}

fun hslToColor(hue: Float, saturation: Float, lightness: Float): Color {
    val c = (1 - kotlin.math.abs(2 * lightness - 1)) * saturation
    val x = c * (1 - kotlin.math.abs((hue / 60) % 2 - 1))
    val m = lightness - c / 2

    val (r, g, b) = when {
        hue < 60 -> Triple(c, x, 0f)
        hue < 120 -> Triple(x, c, 0f)
        hue < 180 -> Triple(0f, c, x)
        hue < 240 -> Triple(0f, x, c)
        hue < 300 -> Triple(x, 0f, c)
        else -> Triple(c, 0f, x)
    }

    return Color(
        red = ((r + m) * 255).roundToInt(),
        green = ((g + m) * 255).roundToInt(),
        blue = ((b + m) * 255).roundToInt()
    )
}


fun fillColumns(numOfColors: Int): List<List<Color?>> {
    val colors =
        if (numOfColors > 6) generateRandomColor(numOfColors - 1) else List(numOfColors - 1) { generateRandomColorOneBall() }

    val balls: MutableList<Color?> = mutableListOf()

    colors.forEach { color ->
        repeat(min(5, numOfColors)) {
            balls.add(color)
        }
    }

    repeat(min(5, numOfColors)) {
        balls.add(null)
    }

    balls.shuffle()

    val columns = balls.chunked(min(5, numOfColors)).toMutableList()

    columns.forEachIndexed { index, column ->
        columns[index] = column.normalizeColumn()
    }

    return columns
}