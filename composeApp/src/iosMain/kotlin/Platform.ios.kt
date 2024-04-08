import platform.UIKit.UIDevice
import printer.printer

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
    override fun print() {
        printer()
    }
}

actual fun getPlatform(): Platform = IOSPlatform()