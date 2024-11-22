import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.ball_sort_game.game.Ball
import com.example.ball_sort_game.R
import com.example.ball_sort_game.game.GameViewModel
import com.example.ball_sort_game.game.fillColumns
import com.example.ball_sort_game.game.isWon
import com.example.ball_sort_game.game.moveBall
import com.example.ball_sort_game.playSound
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun BallSortGameScreen(navController: NavHostController, gameViewModel: GameViewModel) {
    val numOfColors = gameViewModel.numOfColors
    val isSoundEnabled = gameViewModel.isSoundEnabled
    val isMusicEnabled = gameViewModel.isMusicEnabled
    var isMenuOpen by remember { mutableStateOf(false) }

    var stepsRemained by remember { mutableStateOf(numOfColors * numOfColors) }

    var columns by remember { mutableStateOf(fillColumns(numOfColors)) }
    var selectedColumn by remember { mutableStateOf<Int?>(null) }

    val rows = if (numOfColors > 5) {
        columns.chunked((columns.size + 1) / 2)
    } else {
        listOf(columns)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        Spacer(modifier = Modifier.height(64.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "Ball Sort Puzzle",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineMedium,
                fontFamily = FontFamily(
                    Font(R.font.dotness)
                ),
                fontSize = 40.sp
            )

            Spacer(modifier = Modifier.width(30.dp))

            Box {
                IconButton(
                    onClick = { isMenuOpen = !isMenuOpen }
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Menu",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        Spacer(Modifier.height((if (numOfColors <= 5) 156 else 156 / 2).dp))
        Text(
            text = stepsRemained.toString() + context.getString(R.string.steps_remained),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineMedium,
            fontFamily = FontFamily(
                Font(R.font.dotness)
            ),
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(4.dp))

        rows.forEach { rowColumns ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                rowColumns.forEachIndexed { index, column ->
                    val columnWidth = 60.dp
                    val columnHeight = if (numOfColors < 6) 200.dp else 250.dp
                    val columnId = index

                    val offsetY by animateDpAsState(
                        targetValue = if (selectedColumn == columnId) (-12).dp else 0.dp,
                        animationSpec = tween(durationMillis = 200)
                    )

                    Column(
                        modifier = Modifier
                            .clickable {
                                if (selectedColumn == null) {
                                    selectedColumn =
                                        columnId
                                } else {
                                    val selectedIndex = selectedColumn!!
                                    val targetIndex = columnId

                                    if (selectedIndex != targetIndex) {
                                        val (updatedFrom, updatedTo) = moveBall(
                                            fromColumn_ = columns[selectedIndex],
                                            toColumn_ = column
                                        )

                                        if (updatedFrom != columns[selectedIndex] || updatedTo != column) {
                                            playSound(context, R.raw.pop, isSoundEnabled)
                                            stepsRemained -= 1
                                        }
                                        columns = columns
                                            .toMutableList()
                                            .apply {
                                                this[selectedIndex] = updatedFrom
                                                this[targetIndex] = updatedTo
                                            }
                                    }
                                    selectedColumn = null
                                }
                            }
                            .padding(8.dp)
                            .width(columnWidth)
                            .height(columnHeight)
                            .offset(y = offsetY)
                        ,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        column.forEach { color ->
                            Ball(color = color)
                        }
                    }
                }
            }

        }
    }

    val context = LocalContext.current

    if (isWon(columns)) {
        AlertDialog(
            onDismissRequest = { navController.navigate("mainMenu") },
            text = {
                AlertText(context.getString(R.string.congratulations))
            },
            confirmButton = {},
        )
        playSound(context, R.raw.success, isSoundEnabled)

    }

    if (stepsRemained <= 0) {
        AlertDialog(
            onDismissRequest = { navController.navigate("mainMenu") },
            text = {
                AlertText(context.getString(R.string.try_again))
            },
            confirmButton = {},
        )
        playSound(context, R.raw.game_over, isSoundEnabled)

    }


    if (isMenuOpen) {
        AlertDialog(
            onDismissRequest = { isMenuOpen = false },
            text = {
                Column {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            navController.navigate("mainMenu")
                            isMenuOpen = false
                            playSound(context, R.raw.pop, isSoundEnabled)

                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        border = BorderStroke(1.dp, Color.Gray)
                    ) {
                        Text(
                            text = context.getString(R.string.to_main_menu),
                            fontFamily = FontFamily(Font(R.font.dotness)),
                            fontSize = 20.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            columns = fillColumns(numOfColors)
                            isMenuOpen = false
                            playSound(context, R.raw.pop, isSoundEnabled)

                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        border = BorderStroke(1.dp, Color.Gray)
                    ) {
                        Text(
                            text = "Change Ball Colors",
                            fontFamily = FontFamily(Font(R.font.dotness)),
                            fontSize = 20.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            gameViewModel.isSoundEnabled = !isSoundEnabled
                            isMenuOpen = false
                            playSound(context, R.raw.pop, isSoundEnabled)

                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        border = BorderStroke(1.dp, Color.Gray)
                    ) {
                        Text(
                            text = if (isSoundEnabled) context.getString(R.string.disable_sound) else context.getString(R.string.enable_sound),
                            fontFamily = FontFamily(Font(R.font.dotness)),
                            fontSize = 20.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            gameViewModel.isMusicEnabled = !isMusicEnabled
                            isMenuOpen = false
                            playSound(context, R.raw.pop, isSoundEnabled)

                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        border = BorderStroke(1.dp, Color.Gray)
                    ) {
                        Text(
                            text = if (isMusicEnabled) context.getString(R.string.disable_music) else context.getString(R.string.enable_music),
                            fontFamily = FontFamily(Font(R.font.dotness)),
                            fontSize = 20.sp
                        )
                    }
                }
            },
            confirmButton = {}
        )
    }
}

@Composable
fun AlertText(text: String) {
    val context = LocalContext.current
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
            fontSize = 56.sp,
            fontFamily = FontFamily(Font(R.font.dotness)),
        ),
        modifier = Modifier.fillMaxWidth()
    )

}