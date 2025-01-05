package ui.parking.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import receipt.ResultItem

@Composable
fun ParkingBody(
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
                label = "កាលបរិច្ឆេទចូល / Enter Date :",
                value = "23 Jan 2024 14:00",
                isPreview = isPreview
            )

            Spacer(modifier = Modifier.height(5.dp))

            ResultItem(
                label = "កាលបរិច្ឆេទចេញ / Exit Date :",
                value = "23 Jan 2024 15:00",
                isPreview = isPreview
            )

            Spacer(modifier = Modifier.height(5.dp))

            ResultItem(
                label = "រយះពេល / Duration :",
                value = "1 Hours",
                isPreview = isPreview
            )
        }
    }
}