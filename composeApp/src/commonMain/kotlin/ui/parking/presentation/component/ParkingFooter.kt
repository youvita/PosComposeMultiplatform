package ui.parking.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.app.convertToObject
import core.utils.Constants
import core.utils.DashedDivider
import core.utils.SharePrefer
import core.utils.dollar
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import poscomposemultiplatform.composeapp.generated.resources.Res
import poscomposemultiplatform.composeapp.generated.resources.invoice_paid
import receipt.ResultItem
import ui.parking.domain.model.Parking
import ui.settings.domain.model.ParkingFeeData

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ParkingFooter(
    isPreview: Boolean = false,
    barcode: ImageBitmap,
    parking: Parking? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(5.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
//                .padding(start = 5.dp, end = 5.dp),
        ) {
            parking?.fee?.let {
                ResultItem(
                    label = "តំលៃក្នុង១ម៉ោង\nPrice per hour :",
                    value = it.dollar(),
                    isPreview = isPreview
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        DashedDivider(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp),
            color = Color.Black, thickness = 1.dp.takeIf { !isPreview } ?: 1.dp)

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
//                .padding(start = 5.dp, end = 5.dp),
        ) {
            parking?.total?.let { total ->
                ResultItem(
                    label = "បង់ប្រាក់សរុប\nTotal:",
                    value = total.dollar(),
                    isPreview = isPreview
                )
            }

        }

        if (parking?.checkOut.isNullOrEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 0.dp, top = 20.dp, end = 0.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(bitmap = barcode, contentDescription = null)

                Spacer(modifier = Modifier.height(10.dp))

                Text("Show Your Ticket Barcode")
            }
        }

        if (parking?.status == "1") {
            Box(
                modifier = Modifier.padding(20.dp)
            ) {
                Image(painter = painterResource(resource = Res.drawable.invoice_paid), contentDescription = null)
            }
        }
    }
}