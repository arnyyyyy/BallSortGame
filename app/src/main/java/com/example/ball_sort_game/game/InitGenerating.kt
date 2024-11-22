package com.example.ball_sort_game.game

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

fun generateRandomColor(): Color {
    return Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
}

fun fillColumns(numOfColors : Int): List<List<Color?>> {
    val colors = List(numOfColors - 1) { generateRandomColor() }

    val balls: MutableList<Color?> = mutableListOf()

    colors.forEach { color ->
        repeat(numOfColors) {
            balls.add(color)
        }
    }

    repeat(numOfColors) {
        balls.add(null)
    }

    balls.shuffle()

    val columns = balls.chunked(numOfColors).toMutableList()

    columns.forEachIndexed { index, column ->
        columns[index] = column.normalizeColumn()
    }

    return columns
}