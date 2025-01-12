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
import ui.parking.domain.model.Parking

@Composable
fun ParkingBody(
    isPreview: Boolean = false,
    parking: Parking? = null
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
            parking?.checkIn?.let { checkIn ->
                ResultItem(
                    label = "កាលបរិច្ឆេទចូល / Check In :",
                    value = checkIn,
                    isPreview = isPreview
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            if (parking?.checkOut.isNullOrEmpty()) {
                ResultItem(
                    label = "កាលបរិច្ឆេទចេញ / Check Out :",
                    value = "-",
                    isPreview = isPreview
                )
            } else {
                parking?.checkOut?.let { checkOut ->
                    ResultItem(
                        label = "កាលបរិច្ឆេទចេញ / Check Out :",
                        value = checkOut,
                        isPreview = isPreview
                    )
                }
            }

            Spacer(modifier = Modifier.height(5.dp))

            parking?.duration?.let { duration ->
                ResultItem(
                    label = "រយះពេល / Duration :",
                    value = "$duration ${parking.timeUnit}",
                    isPreview = isPreview
                )
            }

        }
    }
}