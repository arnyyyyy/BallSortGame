package com.example.ball_sort_game.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.ball_sort_game.R
import com.example.ball_sort_game.game.GameViewModel
import com.example.ball_sort_game.playSound

@Composable
fun SettingsScreen(navController: NavHostController, gameViewModel: GameViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current

        Spacer(modifier = Modifier.height(64.dp))
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = context.getString(R.string.settings),
            style = TextStyle(
                color = MaterialTheme.colorScheme.primary,
                fontSize = 72.sp,
                fontFamily = FontFamily(Font(R.font.dotness)),
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 48.dp)
        )
        Spacer(modifier = Modifier.height(64.dp))
        Spacer(modifier = Modifier.height(64.dp))
        Spacer(modifier = Modifier.height(16.dp))


        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = context.getString(R.string.sound),
                fontFamily = FontFamily(Font(R.font.dotness)),
                fontSize = 30.sp
            )
            Spacer(modifier = Modifier.width(16.dp))
            Switch(checked = gameViewModel.isSoundEnabled, onCheckedChange = {
                gameViewModel.isSoundEnabled = it
                playSound(context, R.raw.pop, gameViewModel.isSoundEnabled)
            }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = context.getString(R.string.music),
                fontFamily = FontFamily(Font(R.font.dotness)),
                fontSize = 30.sp
            )
            Spacer(modifier = Modifier.width(21.dp))
            Switch(checked = gameViewModel.isMusicEnabled, onCheckedChange = {
                gameViewModel.isMusicEnabled = it
                playSound(context, R.raw.pop, gameViewModel.isSoundEnabled)
            })
        }

        Spacer(modifier = Modifier.height(64.dp))
        Spacer(modifier = Modifier.height(64.dp))
        Spacer(modifier = Modifier.height(64.dp))
        Button(
            onClick = {
                navController.navigate("mainMenu")
                playSound(context, R.raw.pop, gameViewModel.isSoundEnabled)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            border = BorderStroke(1.dp, Color.Gray),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 80.dp)
                .height(70.dp),
        ) {
            Text(
                text = context.getString(R.string.to_main_menu),
                fontFamily = FontFamily(Font(R.font.dotness)),
                fontSize = 24.sp
            )
        }
    }
}