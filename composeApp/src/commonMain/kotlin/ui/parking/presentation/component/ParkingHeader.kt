package ui.parking.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import core.utils.DashedDivider
import receipt.ResultItem
import ui.parking.domain.model.Parking

@Composable
fun ParkingHeader(
    isPreview: Boolean = false,
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
            Spacer(modifier = Modifier.height(5.dp))

            parking?.parkingNo?.let { parkingNo ->
                ResultItem(
                    label = "ស្លាកលេខ\nParking No.:",
                    value = parkingNo,
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
    }
}