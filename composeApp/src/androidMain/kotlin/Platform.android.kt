
import android.os.Build
import androidx.compose.runtime.Composable
import org.topteam.pos.printer.Printer

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"

    @Composable
    override fun Capture(composable: @Composable () -> Unit) {
        Printer(composable)
    }
}

actual fun getPlatform(): Platform = AndroidPlatform()