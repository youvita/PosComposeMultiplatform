package ui.parking.presentation.component

import androidx.compose.foundation.Image
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
import core.app.convertToObject
import core.utils.Constants
import core.utils.DashedDivider
import core.utils.SharePrefer
import core.utils.dollar
import receipt.ResultItem
import ui.parking.domain.model.Parking
import ui.settings.domain.model.ParkingFeeData

@Composable
fun ParkingFooter(
    isPreview: Boolean = false,
    barcode: ImageBitmap,
    parking: Parking? = null
) {
    var parkingData = ParkingFeeData()
    val parkingFee = SharePrefer.getPrefer("${Constants.PreferenceType.PARKING_FEE}")

    if (parkingFee.isNotEmpty()) {
        parkingData = convertToObject<ParkingFeeData>(parkingFee)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(5.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
        ) {
            if (parkingData.isUsed) {
                ResultItem(
                    label = "តំលៃក្នុង១ម៉ោង / Price per hour :",
                    value = parkingData.fee?.dollar(),
                    isPreview = isPreview
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        DashedDivider(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp),
            color = Color.Black, thickness = 2.dp.takeIf { !isPreview } ?: 1.dp)

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
        ) {
            parking?.duration?.let { duration ->
                val fee = parkingData.fee ?: 0.00
                ResultItem(
                    label = "បង់ប្រាក់សរុប / Total :",
                    value = (duration.toDouble() * fee).dollar(),
                    isPreview = isPreview
                )
            }

        }

        if (parking?.checkOut.isNullOrEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, top = 20.dp, end = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(bitmap = barcode, contentDescription = null)

                Spacer(modifier = Modifier.height(10.dp))

                Text("Show Your Ticket Barcode")
            }
        }
    }
}