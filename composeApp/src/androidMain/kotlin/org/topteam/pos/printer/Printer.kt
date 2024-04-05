package org.topteam.pos.printer

import androidx.compose.runtime.Composable
import com.khairo.escposprinter.EscPosCharsetEncoding
import com.khairo.escposprinter.EscPosPrinter
import com.khairo.escposprinter.connection.bluetooth.BluetoothPrintersConnections
import com.khairo.escposprinter.textparser.PrinterTextParserImg

@Composable
fun Printer() {

    println("Hello Printer")
    val printer = EscPosPrinter(
        BluetoothPrintersConnections.selectFirstPaired(),
        203,
        48f,
        32,
        EscPosCharsetEncoding("UTF-8", 24)
    )

    printer.printFormattedText("[C]Compose Multiplatform")


}