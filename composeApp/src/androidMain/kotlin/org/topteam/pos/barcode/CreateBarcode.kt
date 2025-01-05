package org.topteam.pos.barcode

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import com.google.zxing.BarcodeFormat
import com.google.zxing.oned.Code128Writer

fun createBarcodeBitmap(
    barcodeValue: String,
    width: Int,
    height: Int
): ImageBitmap {
    val bitMatrix = Code128Writer().encode(
        barcodeValue,
        BarcodeFormat.CODE_128,
        width,
        height
    )

    val pixels = IntArray(bitMatrix.width * bitMatrix.height)
    for (y in 0 until bitMatrix.height) {
        val offset = y * bitMatrix.width
        for (x in 0 until bitMatrix.width) {
            pixels[offset + x] =
                if (bitMatrix.get(x, y)) Color.Black.toArgb() else Color.Transparent.toArgb()
        }
    }

    val bitmap = Bitmap.createBitmap(
        bitMatrix.width,
        bitMatrix.height,
        Bitmap.Config.ARGB_8888
    )
    bitmap.setPixels(
        pixels,
        0,
        bitMatrix.width,
        0,
        0,
        bitMatrix.width,
        bitMatrix.height
    )
    return bitmap.asImageBitmap()
}