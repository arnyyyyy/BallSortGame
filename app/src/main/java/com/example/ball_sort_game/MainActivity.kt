package com.example.ball_sort_game

import BallSortGameScreen
import DifficultyScreen
import MainMenuScreen
import MusicController
import android.content.Context
import android.content.res.Configuration
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ball_sort_game.game.GameViewModel
import com.example.ball_sort_game.screens.SettingsScreen
import java.util.Locale


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val locale = Locale("ru")
//        Locale.setDefault(locale)
//        val config = Configuration(resources.configuration)
//        config.setLocale(locale)
//        resources.updateConfiguration(config, resources.displayMetrics)

        setContent {
            GameApp()
        }
    }
}

fun playSound(context: Context, soundResId: Int, isSoundEnabled: Boolean) {
    if (isSoundEnabled) {
        val mediaPlayer = MediaPlayer.create(context, soundResId)
        mediaPlayer.setOnCompletionListener { mp ->
            mp.release()
        }
        mediaPlayer.start()
    }
}

@Composable
fun GameApp() {
    val navController = rememberNavController()
    val gameViewModel: GameViewModel = viewModel()

    MusicController(gameViewModel, musicResource = R.raw.background_music)


    NavHost(
        navController = navController,
        startDestination = "mainMenu"
    ) {
        composable("mainMenu") { MainMenuScreen(navController, gameViewModel) }
        composable("settings") { SettingsScreen(navController, gameViewModel) }
        composable("difficulty") { DifficultyScreen(navController, gameViewModel) }
        composable("game") { BallSortGameScreen(navController, gameViewModel) }
    }
}

