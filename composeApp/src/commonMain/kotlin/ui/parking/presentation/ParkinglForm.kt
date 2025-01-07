package ui.parking.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.theme.ColorD9D9D9
import core.theme.PrimaryColor
import core.theme.Shapes
import core.theme.White
import core.utils.DashedDivider
import core.utils.DottedShape
import core.utils.dollar
import menu.presentation.component.OrderItem
import menu.presentation.component.utils.EmptyBox
import orderhistory.presentation.OrderHistoryState
import receipt.ResultItem
import setting.domain.model.ItemModel
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