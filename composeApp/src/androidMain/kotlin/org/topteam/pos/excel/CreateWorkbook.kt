package org.topteam.pos.excel

import android.content.Context
import android.content.Intent
import androidx.compose.material3.SnackbarDuration
import androidx.core.content.FileProvider
import core.app.convertToObject
import io.kamel.core.utils.File
import kotlinx.serialization.Serializable
import org.apache.poi.hssf.usermodel.HSSFCellStyle
import org.apache.poi.hssf.usermodel.HSSFDataFormat
import org.apache.poi.hssf.usermodel.HSSFRichTextString
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.hssf.util.HSSFColor
import org.apache.poi.ss.usermodel.Font
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.RichTextString
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFCellStyle
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun createWorkbook(context: Context, data: String) {
    val workbook = XSSFWorkbook()
    val sheet = workbook.createSheet("Sheet")

    // Column style
    val fontBold = workbook.createFont()
    fontBold.bold = true
    val boldStyle = workbook.createCellStyle()
    boldStyle.setFont(fontBold)

    val unitPriceF = workbook.createFont()
    unitPriceF.color = IndexedColors.BLACK.index
    val unitStyle = workbook.createCellStyle()
    unitStyle.setFont(unitPriceF)
    val dataFormat = workbook.createDataFormat()
    unitStyle.dataFormat = dataFormat.getFormat("$#,##0.00")

    createSheetHeader(sheet, boldStyle)

    val stock = convertToObject<List<ProductStock>>(data)
    if (stock.isNotEmpty()) {
        val startRow = sheet.createRow(1)
        val startRange = startRow.createCell(8)
        val endRow = sheet.createRow(stock.size)
        val endRange = endRow.createCell(8)

        // Stock In Column
        val stockInSRange = startRow.createCell(4)
        val stockInERange = endRow.createCell(4)

        // Stock Out Column
        val stockOutSRange = startRow.createCell(5)
        val stockOutERange = endRow.createCell(5)

        for (index in stock.indices) {
            addData(index + 1, sheet, stock.elementAt(index), unitStyle)
        }

        // StockIn amount
        val stockInRow = sheet.createRow(stock.size + 2) // skip one row for total amount
        val stockInCell7 = stockInRow.createCell(7)
        stockInCell7.setCellValue("Total Stock In")
        stockInCell7.cellStyle = boldStyle
        val stockInCell8 = stockInRow.createCell(8)
        stockInCell8?.cellFormula = "SUMIF(${stockOutSRange.address}:${stockOutERange.address}, ${0} ,${startRange.address}:${endRange.address})"
        stockInCell8?.cellStyle = unitStyle

        // StockOut amount
        val stockOutRow = sheet.createRow(stock.size + 3) // skip one row for total amount
        val stockOutCell7 = stockOutRow.createCell(7)
        stockOutCell7.setCellValue("Total Stock Out")
        stockOutCell7.cellStyle = boldStyle
        val stockOutCell8 = stockOutRow.createCell(8)
        stockOutCell8?.cellFormula = "SUMIF(${stockInSRange.address}:${stockInERange.address}, ${0} ,${startRange.address}:${endRange.address})"
        stockOutCell8?.cellStyle = unitStyle

        // Total amount
        val row = sheet.createRow(stock.size + 4) // skip one row for total amount
        val totalCell7 = row.createCell(7)
        totalCell7.setCellValue("Total Amount")
        totalCell7.cellStyle = boldStyle
        val totalCell8 = row.createCell(8)
        totalCell8?.cellFormula = "${stockInCell8.address}-${stockOutCell8.address}"
        totalCell8?.cellStyle = unitStyle
    }

    val phat = context.getExternalFilesDir(null)
    if (phat?.list()?.isNotEmpty() == true) {
        phat.list()?.last()?.let {
            File(phat, it).delete()
        }
    }

    val file = File(phat, "Report_${getCurrentDate()}${getCurrentTime()}.xlsx")
    val fileOutputStream = FileOutputStream(file)
    workbook.write(fileOutputStream)
    fileOutputStream.close()

    if (file.exists()) {
        val uri = FileProvider.getUriForFile(context, "org.topteam.pos.provider", file)
        val intent = Intent(Intent.ACTION_SEND)
            .apply {
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            }
        context.startActivity(Intent.createChooser(intent, "Share Excel File"))
    } else {
        println("File write failed!")
    }
}

private fun getCurrentDate(): String {
    val formatter = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
    val date = Date()
    return formatter.format(date)
}

private fun getCurrentTime(): String {
    val time = Calendar.getInstance().time
    val formatter = SimpleDateFormat("HHmm", Locale.getDefault())
    return formatter.format(time)
}

private fun createSheetHeader(sheet: Sheet, style: XSSFCellStyle) {
    //setHeaderStyle is a custom function written below to add header style

    //Create sheet first row
    val row = sheet.createRow(0)

    //Header list
    val header = listOf("No", "Product Name", "Category", "SKU", "Stock In", "Stock Out", "Total Stock", "Unit Price", "Amount")

    //Loop to populate each column of header row
    for ((index, value) in header.withIndex()) {

        val columnWidth = (15 * 300)

        //index represents the column number
        sheet.setColumnWidth(index, columnWidth)

        //Create cell
        val cell = row.createCell(index)

        //value represents the header value from HEADER_LIST
        cell?.setCellValue(value)
        cell?.cellStyle = style
    }
}

private fun addData(rowIndex: Int, sheet: Sheet, value: ProductStock, style: XSSFCellStyle) {

    // Create row based on row index
    val row = sheet.createRow(rowIndex)

    val cell0 = row.createCell(0)
    cell0.setCellValue(rowIndex.toDouble())

    val cell1 = row.createCell(1)
    cell1.setCellValue(value.productName)

    val cell2 = row.createCell(2)
    cell2.setCellValue(value.categoryName)

    val cell3 = row.createCell(3)
    cell3.setCellValue(value.productId.toString())

    val cell4 = row.createCell(4)
    cell4.setCellValue(value.stockIn?.toDouble() ?: 0.00)

    val cell5 = row.createCell(5)
    cell5.setCellValue(value.stockOut?.toDouble() ?: 0.00)

    val cell6 = row.createCell(6)
    cell6.setCellValue(value.stockTotal?.toDouble() ?: 0.00)

    val cell7 = row.createCell(7)
    cell7.setCellValue(value.productPrice?.toDouble() ?: 0.00)
    cell7.cellStyle = style

    val cell8 = row.createCell(8)
    cell8.cellFormula = "${cell6.address} * ${cell7.address}"
    cell8.cellStyle = style
}

@Serializable
data class ProductStock(
    val stockId: Long? = null,
    val stockIn: Long? = null,
    val stockOut: Long? = null,
    val stockTotal: Long? = null,
    val productId: Long? = null,
    val productName: String? = null,
    val productPrice: String? = null,
    val categoryName: String? = null,
    val dateIn: String? = null,
    val timeIn: String? = null,
    val dateOut: String? = null,
    val timeOut: String? = null,
)



