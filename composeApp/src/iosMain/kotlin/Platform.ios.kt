import androidx.compose.runtime.Composable
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion

    @Composable
    override fun Capture(key: Int, composable: @Composable () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun printer() {
        TODO("Not yet implemented")
    }


}

actual fun getPlatform(): Platform = IOSPlatform()