package com.example.ball_sort_game.game

import androidx.compose.ui.graphics.Color

fun moveBall(fromColumn_: List<Color?>, toColumn_: List<Color?>): Pair<List<Color?>, List<Color?>> {

    val fromColumn = fromColumn_.toMutableList()
    val toColumn = toColumn_.toMutableList()

    val positionToTake = fromColumn.indexOfFirst { it != null }
    if (positionToTake == -1) {
        return Pair(fromColumn, toColumn)
    }
    var positionToPut = toColumn.indexOfFirst { it != null }
    if (positionToPut == 0) {
        return Pair(fromColumn, toColumn)
    }
    if (positionToPut == -1) {
        positionToPut = toColumn.size
    }
    positionToPut -= 1

    val takenBall =  fromColumn[positionToTake]
    fromColumn[positionToTake] = null
    toColumn[positionToPut] = takenBall

    return Pair(fromColumn, toColumn)
}
