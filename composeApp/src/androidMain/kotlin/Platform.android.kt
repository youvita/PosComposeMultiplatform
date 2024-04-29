
import android.os.Build
import androidx.compose.runtime.Composable
import org.topteam.pos.printer.CaptureImage
import org.topteam.pos.printer.printOut

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"

    @Composable
    override fun Capture(key: Int, composable: @Composable () -> Unit) {
        CaptureImage(key, composable)
    }

    override fun printer() {
        printOut()
    }
}

actual fun getPlatform(): Platform = AndroidPlatform()