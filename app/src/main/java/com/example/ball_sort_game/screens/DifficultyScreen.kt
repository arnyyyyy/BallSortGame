import android.widget.SeekBar
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.ball_sort_game.R
import com.example.ball_sort_game.game.GameViewModel
import com.example.ball_sort_game.playSound
import kotlin.math.round

@Composable
fun DifficultyScreen(navController: NavHostController, gameViewModel: GameViewModel) {
    var numOfColors by remember { mutableStateOf(4) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current

        Spacer(modifier = Modifier.height(64.dp))
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = context.getString(R.string.difficulty),
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

        Text(
            text = context.getString(R.string.set_num_of_colors) + ' ' + ' ' + numOfColors.toString(),
            style = TextStyle(
                color = MaterialTheme.colorScheme.primary,
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.dotness)),
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        )

        Slider(
            value = numOfColors * 1.0f,
            onValueChange = { newValue ->
                numOfColors = round(newValue).toInt()
                playSound(context, R.raw.pop, gameViewModel.isSoundEnabled)
            },
            valueRange = 4f..10f,
            steps = 5,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(64.dp))
        Spacer(modifier = Modifier.height(64.dp))
        Spacer(modifier = Modifier.height(64.dp))
        Button(
            onClick = {
                gameViewModel.numOfColors = numOfColors
                playSound(context, R.raw.pop, gameViewModel.isSoundEnabled)
                navController.navigate("mainMenu")
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
