import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap

interface Platform {
    val name: String

    @Composable
    fun Capture(key: Int, composable: @Composable () -> Unit)

    fun printer()
}

expect fun getPlatform(): Platform