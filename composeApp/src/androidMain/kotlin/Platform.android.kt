
import android.content.Context
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import org.topteam.pos.excel.createWorkbook
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

    @Composable
    override fun download(data: String) {
        val context = LocalContext.current
        createWorkbook(context, data)
    }
}

actual fun getPlatform(): Platform = AndroidPlatform()