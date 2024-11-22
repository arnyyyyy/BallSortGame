import android.content.Context
import android.media.MediaPlayer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.ball_sort_game.game.GameViewModel

class MusicPlayer(private val context: Context) {
    private var mediaPlayer: MediaPlayer? = null

    fun playMusic(resourceId: Int) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, resourceId)
            mediaPlayer?.isLooping = true
            mediaPlayer?.start()
        }
    }

    fun stopMusic() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}

@Composable
fun MusicController(
    gameViewModel: GameViewModel,
    musicResource: Int
) {
    val isMusicEnabled by gameViewModel::isMusicEnabled

    val context = LocalContext.current
    val musicPlayer = remember { MusicPlayer(context) }

    LaunchedEffect(isMusicEnabled) {
        if (isMusicEnabled) {
            musicPlayer.playMusic(musicResource)
        } else {
            musicPlayer.stopMusic()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            musicPlayer.stopMusic()
        }
    }
}