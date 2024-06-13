package receipt

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import core.app.convertToObject
import core.theme.Styles
import core.utils.Constants
import core.utils.DashedDivider
import core.utils.SharePrefer
import core.utils.getTextStyle
import ui.settings.domain.model.ExchangeRateData
import ui.settings.domain.model.InvoiceData
import ui.settings.domain.model.ShopData

@Composable
fun BillCustomer() {
    BillCustomerForm1()
    BillCustomerForm2()
}

@Composable
fun BillCustomerForm1(
    isPreview: Boolean = false,
) {
    val invoiceNo = SharePrefer.getPrefer("${Constants.PreferenceType.INVOICE_NO}")
    val invoice = convertToObject<InvoiceData>(invoiceNo)

    val exchangeRate = SharePrefer.getPrefer("${Constants.PreferenceType.EXCHANGE_RATE}")
    val exchange = convertToObject<ExchangeRateData>(exchangeRate)

    Column(
        modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "វិក្កយបត្រ Customer Bill ", style = getTextStyle(typography = Styles.HeaderLarge.takeIf { !isPreview } ?: Styles.LabelSmall))

        Spacer(modifier = Modifier.height(10.dp))

        DashedDivider(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp),
            color = Color.Black, thickness = 2.dp.takeIf { !isPreview } ?: 1.dp)

        Spacer(modifier = Modifier.height(10.dp))

        if (invoice.isUsed) {
            ResultItem(
                label = "លេខវិក័យប័ត្រ /Invoice No :",
                value = "2024020000234",
                isPreview = isPreview
            )

            Spacer(modifier = Modifier.height(5.dp))

            ResultItem(
                label = "កាលបរិច្ឆេទ / Date :",
                value = "23 Jan 2024 14:00",
                isPreview = isPreview
            )
        }

        if (exchange.isUsed) {
            Spacer(modifier = Modifier.height(5.dp))

            ResultItem(
                label = "អត្រាប្តូរប្រាក់ / Exchange Rate :",
                value = "(\$)1 USD = (R)4,160",
                isPreview = isPreview
            )

            Spacer(modifier = Modifier.height(5.dp))
        }

        ResultItem(label = "ពេលវេលាចាប់ផ្តើម / Start Time :", value = "23 Jan 2024 10:00", isPreview = isPreview)

        Spacer(modifier = Modifier.height(5.dp))

        ResultItem(label = "ពេលវេលាបញ្ចប់ / End Time :", value = "23 Jan 2024 14:00", isPreview = isPreview)
    }
}

@Composable
fun BillCustomerForm2(
    isPreview: Boolean = false,
) {
    Column(
        modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(5.dp))

        ResultItem(label = "តុ / Table :", value = "Out Table 12", isPreview = isPreview)

        Spacer(modifier = Modifier.height(5.dp))

        ResultItem(label = "អ្នកគិតលុយ / Cashier :", value = "Jenny", isPreview = isPreview)

        Spacer(modifier = Modifier.height(5.dp))

        ResultItem(label = "VAT TIN :", value = "E003-1234567890", isPreview = isPreview)

        Spacer(modifier = Modifier.height(5.dp))

        ResultItem(label = "អតិថិជន / Customer :", value = "093 234 234", isPreview = isPreview)

        Spacer(modifier = Modifier.height(5.dp))

        ResultItem(label = "អាស័យដ្ឋាន / Address :", value = ".7953 Oakland St Honolulu, HI 9681 Phnom Penh, Cambodia", isPreview = isPreview)

        Spacer(modifier = Modifier.height(5.dp))
    }
}