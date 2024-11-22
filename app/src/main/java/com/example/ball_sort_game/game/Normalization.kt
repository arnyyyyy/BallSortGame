package com.example.ball_sort_game.game

import androidx.compose.ui.graphics.Color

fun List<Color?>.normalizeColumn(): List<Color?> {
    return this.filter { it == null } + this.filterNotNull()
}