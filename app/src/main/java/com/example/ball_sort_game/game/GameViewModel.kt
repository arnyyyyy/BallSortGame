package com.example.ball_sort_game.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    var numOfColors by mutableStateOf(4)
    var isSoundEnabled by mutableStateOf(true)
    var isMusicEnabled by mutableStateOf(true)
}