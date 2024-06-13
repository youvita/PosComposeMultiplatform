package receipt

import Platform
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.theme.White
import getPlatform
import setting.domain.model.ItemModel

@Composable
fun CaptureItem(
    platform: Platform = getPlatform()
) {
    platform.Capture(0) {
        Box(
            modifier = Modifier.fillMaxWidth().background(White)
        ) {
            BillHeader()
        }
    }
    platform.Capture(1) {
        Box(
            modifier = Modifier.fillMaxWidth().background(White)
        ) {
            BillCustomerForm1()
        }
    }
    platform.Capture(2) {
        Box(
            modifier = Modifier.fillMaxWidth().background(White)
        ) {
            BillCustomerForm2()
        }
    }
    platform.Capture(3) {
        Box(
            modifier = Modifier.fillMaxWidth().background(White)
        ) {
            val columnList = listOf("Description", "Qty", "Price", "Dis.", "Amount")
            val rowList = listOf(
                ItemModel(
                    name = "Caramel Frappuccino Caramel",
                    qty = 1,
                    price = 1.0,
                    discount = 0
                )
            )
            BillHeaderItem(
                columnList = columnList,
                rowList = rowList
            )
        }
    }
    platform.Capture(5) {
        Box(
            modifier = Modifier.fillMaxWidth().background(White)
        ) {
            val columnList = listOf(
                "ទំនិញ / ចំនួន Item/Qty :",
                "សរុបរង / Sub Total :",
                "បញ្ចុះតម្លៃ / Discount :",
                "អាករ / VAT :",
                "សរុប / Total :"
            )
            val rowList = listOf("99 items / Qty 999", "22222.22 $", "99%", "10%", "234,234.00 $")
            val pointColumnList = listOf("Old Point :", "New Point :", "Total Current Point :")
            val pointRowList = listOf("999", "999", "999")
            BillTotalItem(
                columnList = columnList,
                rowList = rowList,
                pointColumnList = pointColumnList,
                pointRowList = pointRowList
            )
        }
    }
    platform.Capture(6) {
        Box(
            modifier = Modifier.fillMaxWidth().background(White)
        ) {
            BillPayment()
        }
    }
    platform.Capture(7) {
        Box(
            modifier = Modifier.fillMaxWidth().background(White)
        ) {
            BillCompanySeal()
        }
    }
    platform.Capture(8) {
        Box(
            modifier = Modifier.fillMaxWidth().background(White)
        ) {
            BillQueue()
        }
    }
    platform.Capture(9) {
        Box(
            modifier = Modifier.fillMaxWidth().background(White)
        ) {
            BillFooter()
        }
    }
}