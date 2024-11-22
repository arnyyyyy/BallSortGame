package com.example.ball_sort_game.game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Ball(color: Color?) {
    Canvas(
        modifier = Modifier
            .size(40.dp)
            .background(Color.LightGray, shape = CircleShape)
    ) {
        if (color != null) {
            drawCircle(color)
        }
    }
}
