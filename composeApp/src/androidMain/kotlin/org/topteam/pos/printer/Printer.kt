package org.topteam.pos.printer

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.graphics.applyCanvas
import androidx.core.view.doOnLayout
import com.khairo.escposprinter.EscPosCharsetEncoding
import com.khairo.escposprinter.EscPosPrinter
import com.khairo.escposprinter.connection.bluetooth.BluetoothPrintersConnections
import com.khairo.escposprinter.textparser.PrinterTextParserImg

var imageList = mutableListOf<Capture>()

data class Capture(
    val key: Int,
    val bitmap: Bitmap
)

@Composable
fun CaptureImage(
    key: Int, content: @Composable () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        AndroidView(factory = {
            ComposeView(it).apply {
                setContent {
                    Row(
                        modifier = Modifier.size(380.dp, height = Dp.Infinity)
                    ) {
                        content()
                    }
                }
            }
        }, modifier = Modifier.fillMaxWidth(), update = {
            it.run {
                doOnLayout { view ->
                    val bitmap = Bitmap.createBitmap(
                        1080,
                        view.measuredHeight,
                        Bitmap.Config.ARGB_8888
                    ).applyCanvas {
                        it.draw(this)
                    }

                    imageList.add(Capture(key, bitmap))
                }
            }
        })
    }
}

fun printOut() {
    val printer = EscPosPrinter(BluetoothPrintersConnections.selectFirstPaired(),
        203,
        48f,
        32, EscPosCharsetEncoding("UTF-8", 24))

    var receipt = ""
    for(image in imageList.sortedBy { it.key }) {
        receipt = receipt.plus("[C]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(
            printer, image.bitmap
        ) + "</img>\n")
    }

    printer.printFormattedText(receipt)
}