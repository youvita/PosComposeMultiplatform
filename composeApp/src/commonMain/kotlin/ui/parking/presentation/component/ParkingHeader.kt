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

@Composable
fun ParkingHeader(
    isPreview: Boolean = false,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
        ) {
            ResultItem(
                label = "កាលបរិច្ឆេទ / Date :",
                value = "23 Jan 2024 14:00",
                isPreview = isPreview
            )

            Spacer(modifier = Modifier.height(5.dp))

            ResultItem(
                label = "ស្លាកលេខ / Parking No. :",
                value = "AC-5151-2222",
                isPreview = isPreview
            )

            Spacer(modifier = Modifier.height(10.dp))

            DashedDivider(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
                color = Color.Black, thickness = 2.dp.takeIf { !isPreview } ?: 1.dp)

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}