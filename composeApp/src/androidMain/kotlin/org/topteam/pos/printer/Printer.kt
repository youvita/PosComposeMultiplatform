package org.topteam.pos.printer

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import ir.mahozad.multiplatform.comshot.captureToImage
import kotlin.time.measureTimedValue

//import org.topteam.pos.capture.findActivity


@Composable
fun PrinterReceipt(content: @Composable () -> Unit) {
    val context = LocalContext.current
    println("L>>>>>>>>>pidjfdijfkdjfdf")
    var image by remember { mutableStateOf<ImageBitmap?>(null) }
    val activity = try {
        context.findActivity()
    } catch (t: Throwable) {
//        screenCaptureState.updateImageState(ScreenShotResult.Error(t))
        return
    }

    var timevalue = measureTimedValue {
        captureToImage(activity) {
//            Row(
//                modifier = Modifier.size(380.dp, height = Dp.Infinity)
//            ) {
//                content()
//            }
        }
    }
    image = timevalue.value
    image?.let {
        println(it)
//        val path = Environment.getExternalStorageDirectory().toString() + "/Download/"
//            val file = File(path + "20240452349.png")
//            val fileOutputStream = FileOutputStream(file)
//            it.asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
//            fileOutputStream.close()
    }
//    Button(
//        onClick = {
//            image = captureToImage(activity) {
//                Box(
//                    modifier = Modifier.size(width = 380.dp, height = Dp.Infinity)) {
//                    Box(modifier = Modifier.height(100.dp)) {
//                        Text("Hello")
//                    }
//
//                }
//            }
//            image?.let {
//                println(">>>>> $it")
//            }
//        }
//    ) {
//        androidx.compose.material3.Text("Click")
//    }


//    val printer = EscPosPrinter(
//        BluetoothPrintersConnections.selectFirstPaired(),
//        203,
//        48f,
//        32,
//        EscPosCharsetEncoding("UTF-8", 24)
//    )
//
//    printer.printFormattedText("[C]Compose Multiplatform")


}

private fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("Did not find activity")
}