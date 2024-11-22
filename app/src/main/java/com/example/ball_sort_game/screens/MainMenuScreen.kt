import androidx.compose.animation.animateColor
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.ball_sort_game.R
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.ball_sort_game.game.GameViewModel
import com.example.ball_sort_game.playSound
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun AnimatedGradientText() {
    val context = LocalContext.current
    val text = context.getString(R.string.main_menu)

    var colors by remember {
        mutableStateOf(
            List(4) { Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256)) }
        )
    }
    LaunchedEffect(Unit) {
        while (true) {
            colors =
                List(4) { Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256)) }
            delay(1000)
        }
    }

    val transition = rememberInfiniteTransition(label = "")
    val animatedColors = colors.map { color ->
        transition.animateColor(
            initialValue = color,
            targetValue = Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256)),
            animationSpec = infiniteRepeatable(
                tween(durationMillis = 2000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ), label = ""
        )
    }

    val gradientBrush = Brush.linearGradient(
        colors = animatedColors.map { it.value }
    )


    Text(
        text = text,
        style = TextStyle(
            brush = gradientBrush,
            fontSize = 78.sp,
            fontFamily = FontFamily(Font(R.font.dotness)),
        ),
        modifier = Modifier.fillMaxWidth()
    )

}


@Composable
fun MainMenuScreen(navController: NavHostController, gameViewModel : GameViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(64.dp))
        Spacer(modifier = Modifier.height(48.dp))
        AnimatedGradientText()
        Spacer(modifier = Modifier.height(64.dp))
        Spacer(modifier = Modifier.height(64.dp))
        Spacer(modifier = Modifier.height(16.dp))

        val context = LocalContext.current

        Button(
            onClick = { navController.navigate("game")
                playSound(context, R.raw.pop, gameViewModel.isSoundEnabled)},
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 8.dp)
                .height(70.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            Text(
                text = context.getString(R.string.play),
                fontFamily = FontFamily(Font(R.font.dotness)),
                fontSize = 30.sp
            )
        }

        Button(
            onClick = { navController.navigate("difficulty")
                playSound(context, R.raw.pop, gameViewModel.isSoundEnabled) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 8.dp)
                .height(70.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            border = BorderStroke(1.dp, Color.Gray)
        ){
            Text(
                text = context.getString(R.string.difficulty),
                fontFamily = FontFamily(Font(R.font.dotness)),
                fontSize = 30.sp
            )
        }

        Button(
            onClick = { navController.navigate("settings")
                playSound(context, R.raw.pop, gameViewModel.isSoundEnabled)},
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 8.dp)
                .height(70.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            Text(
                text = context.getString(R.string.settings),
                fontFamily = FontFamily(Font(R.font.dotness)),
                fontSize = 30.sp,
            )
        }
    }
}

