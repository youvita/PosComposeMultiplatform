import android.os.Build
import org.topteam.pos.printer.printer

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
    override fun print() {
        printer()
    }
}

actual fun getPlatform(): Platform = AndroidPlatform()