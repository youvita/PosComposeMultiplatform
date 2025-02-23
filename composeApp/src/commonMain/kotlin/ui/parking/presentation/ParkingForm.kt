package ui.parking.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import ui.parking.domain.model.Parking
import ui.parking.presentation.component.ParkingBody
import ui.parking.presentation.component.ParkingFooter
import ui.parking.presentation.component.ParkingHeader


@Composable
fun ParkingForm(
    isPreview: Boolean = false,
    imageBitmap: ImageBitmap,
    parking: Parking
){
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ParkingHeader(isPreview = isPreview, parking = parking)

        ParkingBody(isPreview = isPreview, parking = parking)

        ParkingFooter(isPreview = isPreview, barcode = imageBitmap, parking = parking)
    }

}