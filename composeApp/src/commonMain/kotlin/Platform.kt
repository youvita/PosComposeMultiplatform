import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap

interface Platform {
    val name: String
    val barcode: ImageBitmap

    @Composable
    fun Capture(key: Int, composable: @Composable () -> Unit)

    @Composable
    fun download(data: String)

    fun printer()

    fun generateBarcode(data: String, width: Int, height: Int)
}

expect fun getPlatform(): Platform