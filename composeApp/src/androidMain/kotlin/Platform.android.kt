
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import org.topteam.pos.barcode.createBarcodeBitmap
import org.topteam.pos.excel.createWorkbook
import org.topteam.pos.printer.CaptureImage
import org.topteam.pos.printer.printOut

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
    override var barcode: ImageBitmap = ImageBitmap(100, 50)

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

    override fun generateBarcode(data: String, width: Int, height: Int) {
        barcode = createBarcodeBitmap(data, width, height)
    }
}

actual fun getPlatform(): Platform = AndroidPlatform()