import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.ball_sort_game.game.Ball
import com.example.ball_sort_game.R
import com.example.ball_sort_game.game.GameViewModel
import com.example.ball_sort_game.game.fillColumns
import com.example.ball_sort_game.game.moveBall
import com.example.ball_sort_game.playSound

@Composable
fun BallSortGameScreen(navController: NavHostController, gameViewModel: GameViewModel) {
    val numOfColors = gameViewModel.numOfColors
    val isSoundEnabled = gameViewModel.isSoundEnabled
    val isMusicEnabled = gameViewModel.isMusicEnabled
    var isMenuOpen by remember { mutableStateOf(false) }

    var columns by remember { mutableStateOf(fillColumns(numOfColors)) }
    var selectedColumn by remember { mutableStateOf<Int?>(null) }

    val rows = if (numOfColors > 5) {
        columns.chunked(columns.size / 2)
    } else {
        listOf(columns)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        IconButton(
            onClick = { isMenuOpen = !isMenuOpen },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Menu",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        DropdownMenu(
            expanded = isMenuOpen,
            onDismissRequest = { isMenuOpen = false },
            modifier = Modifier.padding(end = 16.dp)
        ) {
            DropdownMenuItem(
                text = { Text("Back to Menu") },
                onClick = {
                    navController.navigate("mainMenu")
                    isMenuOpen = false
                }
            )
            DropdownMenuItem(
                text = { Text("Change Ball Colors") },
                onClick = {
                    columns = fillColumns(numOfColors)
                    isMenuOpen = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(if (isSoundEnabled) "Disable Sound" else "Enable Sound")
                },
                onClick = {
                    gameViewModel.isSoundEnabled = !isSoundEnabled
                    isMenuOpen = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(if (isMusicEnabled) "Disable Music" else "Enable Music")
                },
                onClick = {
                    gameViewModel.isMusicEnabled = !isMusicEnabled
                    isMenuOpen = false
                }
            )
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ball Sort Puzzle",
            style = MaterialTheme.typography.headlineMedium,
            fontFamily = FontFamily(
                Font(R.font.dotness)
            ),
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp)
        )

        val context = LocalContext.current

        rows.forEach { rowColumns ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                rowColumns.forEachIndexed { index, column ->
                    val columnWidth = 60.dp
                    val columnHeight = 200.dp

                    Column(
                        modifier = Modifier
                            .clickable {
                                if (selectedColumn == null) {
                                    selectedColumn = columns.indexOf(column)
                                } else {
                                    val selectedIndex = selectedColumn!!
                                    val targetIndex = columns.indexOf(column)

                                    if (selectedIndex != targetIndex) {
                                        val (updatedFrom, updatedTo) = moveBall(
                                            fromColumn_ = columns[selectedIndex],
                                            toColumn_ = column
                                        )

                                        if (updatedFrom != columns[selectedIndex] || updatedTo != column) {
                                            playSound(context, R.raw.pop, isSoundEnabled)
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
                            .height(columnHeight),
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
}