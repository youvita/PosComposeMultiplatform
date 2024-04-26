import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap

interface Platform {
    val name: String

    @Composable
    fun Capture(composable: @Composable () -> Unit)
}

expect fun getPlatform(): Platform