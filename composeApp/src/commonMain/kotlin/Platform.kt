import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap

interface Platform {
    val name: String

    @Composable
    fun Capture(key: Int, composable: @Composable () -> Unit)

    fun printer()

    @Composable
    fun download(data: String)
}

expect fun getPlatform(): Platform