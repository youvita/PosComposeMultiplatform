package ui.parking.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import core.app.convertToObject
import core.data.Status
import core.scanner.QrScannerScreen
import core.theme.PrimaryColor
import core.theme.White
import core.utils.Constants
import core.utils.LineWrapper
import core.utils.PrimaryButton
import core.utils.SharePrefer
import core.utils.checkTimeWithinHour
import core.utils.getCurrentDateTime
import core.utils.getDateTimePeriod
import getPlatform
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import poscomposemultiplatform.composeapp.generated.resources.Res
import poscomposemultiplatform.composeapp.generated.resources.ic_back
import poscomposemultiplatform.composeapp.generated.resources.ic_scanner
import poscomposemultiplatform.composeapp.generated.resources.parking
import ui.parking.domain.model.Parking
import ui.parking.presentation.component.ParkingBody
import ui.parking.presentation.component.ParkingFooter
import ui.parking.presentation.component.ParkingHeader
import ui.parking.presentation.component.ParkingTable
import ui.settings.domain.model.ParkingFeeData

@OptIn(ExperimentalResourceApi::class)
class ParkingScreen: Screen, KoinComponent {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val platform = getPlatform()

        val parkingViewModel = get<ParkingViewModel>()
        val state = parkingViewModel.state.collectAsState().value

        var previousScreen by remember { mutableStateOf(listOf("Super Mario (admin)")) }
        var currentScreen by remember { mutableStateOf(listOf("Parking")) }

        var parkingNo by remember { mutableStateOf("") }
        var keyword by remember { mutableStateOf("") }
        var parking by remember { mutableStateOf(Parking()) }

        var barcode by remember { mutableStateOf(ImageBitmap(100, 50)) }
        var isPreview by remember { mutableStateOf(false) }
        var isPrint by remember { mutableStateOf(false) }
        var isCheckIn by remember { mutableStateOf(false) }
        var startBarCodeScan by remember { mutableStateOf(false) }
        var isPaid by remember { mutableStateOf(false) }

        val scope = rememberCoroutineScope()

        val isLoading by rememberUpdatedState(state.isLoading)
        LaunchedEffect(isLoading) {
            if (state.status == Status.SUCCESS) {
            val list = state.data?.last()
            list?.let { item ->
                var parkingData = ParkingFeeData()
                val parkingFee = SharePrefer.getPrefer("${Constants.PreferenceType.PARKING_FEE}")

                if (parkingFee.isNotEmpty()) {
                    parkingData = convertToObject<ParkingFeeData>(parkingFee)
                }
                isPaid = item.checkOut?.isNotEmpty() == true
                val isWithinHour = checkTimeWithinHour(
                    item.checkIn.orEmpty(),
                    item.checkOut.orEmpty().takeIf { isPaid } ?: getCurrentDateTime())
                val fee = parkingData.fee ?: 0.00
                val period = getDateTimePeriod(item.checkIn ?: "", getCurrentDateTime())
                val price =
                    (period.toDouble() * fee).takeIf { isWithinHour } ?: (period.toDouble() * 0.05)


                parking = item.copy(
                    parkingNo = item.parkingNo,
                    checkOut = item.checkOut.takeIf { isPaid } ?: getCurrentDateTime(),
                    duration = item.duration.takeIf { isPaid } ?: period,
                    timeUnit = "hour".takeIf { isWithinHour } ?: "minute",
                    total = item.total.takeIf { isPaid } ?: price)

                isPreview = true
                isCheckIn = false
                parkingNo = ""
            }
            }
        }

        Scaffold(
            modifier = Modifier.padding(10.dp),
            backgroundColor = Color.Transparent,
            topBar = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(resource = Res.drawable.ic_back),
                        contentDescription = "",
                        modifier = Modifier.clickable(
                            indication = null,
                            interactionSource = remember {
                                MutableInteractionSource()
                            }
                        ) {
                            navigator.pop()
                        }
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    LazyRow {
                        items(previousScreen.size) {
                            Text(
                                modifier = Modifier
                                    .clickable(
                                        indication = null,
                                        interactionSource = remember {
                                            MutableInteractionSource()
                                        }
                                    ) {
                                        if (it > 0) {
                                            currentScreen = currentScreen.toMutableList().apply { removeLast() }
                                            previousScreen = previousScreen.toMutableList().apply { removeLast() }
                                        }
                                    },
                                text = previousScreen[it],
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryColor
                                )
                            )

                            Text(
                                text = " / ",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFBABABA)
                                )
                            )
                        }
                    }

                    Text(
                        text = currentScreen.last(),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    )
                }
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Card(
                        modifier = Modifier
                            .weight(2f)
                            .fillMaxHeight(),
                        shape = RoundedCornerShape(10.dp),
                        elevation = CardDefaults.elevatedCardElevation().also { 10.dp },
                        colors = CardDefaults.cardColors(White),
                    ) {
                        Box(modifier = Modifier.weight(1f)) {

                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    TextField(
                                        value = keyword,
                                        onValueChange = {
                                            keyword = it
                                            parkingViewModel.onEvent(ParkingEvent.SearchParking(it))
                                        },
                                        modifier = Modifier
                                            .weight(3f)
                                            .padding(16.dp)
                                            .focusRequester(remember { FocusRequester() }),
                                        shape = RoundedCornerShape(10.dp),
                                        placeholder = {
                                            Text(
                                                "Search Parking No.",
                                                maxLines = 1
                                            )
                                        },
                                        trailingIcon = {
//                                        if (!isInputEmpty) {
//                                            Icon(
//                                                imageVector = Icons.Default.Clear,
//                                                contentDescription = "Clear",
//                                                tint = PrimaryColor,
//                                                modifier = Modifier.clickable {
//                                                    // Handle clear action
//                                                    historyEvent(OrderHistoryEvent.ClearSearchOrder)
//                                                    isInputEmpty = true
//                                                }
//                                            )
//                                        } else {
//                                            Icon(
//                                                imageVector = Icons.Outlined.Search,
//                                                contentDescription = "Search",
//                                                tint = PrimaryColor
//                                            )
//                                        }
                                        },
                                        colors = TextFieldDefaults.textFieldColors(
                                            focusedIndicatorColor = Color.Transparent,
                                            unfocusedIndicatorColor = Color.Transparent,
                                            disabledIndicatorColor = Color.Transparent
                                        )
                                    )

                                    Box(
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .clickable {
                                                startBarCodeScan = true
                                            }
                                    ) {
                                        Image(
                                            modifier = Modifier
                                                .padding(10.dp)
                                                .size(24.dp),
                                            contentDescription = null,
                                            painter = painterResource(resource = Res.drawable.ic_scanner))
                                    }
                                }

                                //Table
                                ParkingTable(
                                    state = state,
                                    onItemClick = { item ->
                                        var parkingData = ParkingFeeData()
                                        val parkingFee = SharePrefer.getPrefer("${Constants.PreferenceType.PARKING_FEE}")

                                        if (parkingFee.isNotEmpty()) {
                                            parkingData = convertToObject<ParkingFeeData>(parkingFee)
                                        }
                                        isPaid = item.checkOut?.isNotEmpty() == true
                                        val isWithinHour = checkTimeWithinHour(item.checkIn.orEmpty(), item.checkOut.orEmpty().takeIf { isPaid } ?: getCurrentDateTime())
                                        val fee = parkingData.fee ?: 0.00
                                        val period = getDateTimePeriod(item.checkIn ?: "", getCurrentDateTime())
                                        val price = (period.toDouble() * fee).takeIf { isWithinHour } ?: (period.toDouble() * 0.05)


                                        parking = item.copy(
                                            checkOut = item.checkOut.takeIf { isPaid } ?: getCurrentDateTime(),
                                            duration = item.duration.takeIf { isPaid } ?: period,
                                            timeUnit = "hour".takeIf { isWithinHour } ?: "minute",
                                            total = item.total.takeIf { isPaid } ?: price)

                                        isPreview = true
                                        isCheckIn = false
                                        parkingNo = ""
                                    }
                                )
                            }

                        }

                    }

                    Spacer(modifier = Modifier.width(15.dp))

                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        if (isPrint) {
                            platform.Capture(0) {
                                Box(
                                    modifier = Modifier.fillMaxWidth().background(White)
                                ) {
                                    ParkingHeader(
                                        parking = parking
                                    )
                                }
                            }

                            platform.Capture(1) {
                                Box(
                                    modifier = Modifier.fillMaxWidth().background(White)
                                ) {
                                    ParkingBody(
                                        parking = parking
                                    )
                                }
                            }

                            platform.Capture(2) {
                                Box(
                                    modifier = Modifier.fillMaxWidth().background(White)
                                ) {
                                    ParkingFooter(
                                        barcode = barcode,
                                        parking = parking
                                    )
                                }
                            }

                            scope.launch {
                                delay(1000L)
                                platform.printer()
                                isPrint = false
                            }
                        } else {
                            platform.clearPrinter()
                        }

                        Card(
                            modifier = Modifier
                                .fillMaxHeight(),
                            shape = RoundedCornerShape(10.dp),
                            elevation = CardDefaults.elevatedCardElevation().also { 10.dp },
                            colors = CardDefaults.cardColors(White),
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    TextField(
                                        value = parkingNo,
                                        onValueChange = {
                                            parkingNo = it
//                                        parkingViewModel.onEvent(ParkingEvent.SearchParking(it))
                                        },
                                        modifier = Modifier
                                            .weight(3f)
                                            .padding(16.dp)
                                            .focusRequester(remember { FocusRequester() }),
                                        shape = RoundedCornerShape(10.dp),
                                        placeholder = {
                                            Text(
                                                "Enter Parking No.",
                                                maxLines = 1
                                            )
                                        },
                                        trailingIcon = {
//                                        if (!isInputEmpty) {
//                                            Icon(
//                                                imageVector = Icons.Default.Clear,
//                                                contentDescription = "Clear",
//                                                tint = PrimaryColor,
//                                                modifier = Modifier.clickable {
//                                                    // Handle clear action
//                                                    historyEvent(OrderHistoryEvent.ClearSearchOrder)
//                                                    isInputEmpty = true
//                                                }
//                                            )
//                                        } else {
//                                            Icon(
//                                                imageVector = Icons.Outlined.Search,
//                                                contentDescription = "Search",
//                                                tint = PrimaryColor
//                                            )
//                                        }
                                        },
                                        colors = TextFieldDefaults.textFieldColors(
                                            focusedIndicatorColor = Color.Transparent,
                                            unfocusedIndicatorColor = Color.Transparent,
                                            disabledIndicatorColor = Color.Transparent
                                        )
                                    )

                                    Box(
                                        modifier = Modifier.padding(10.dp)
                                    ) {
                                        PrimaryButton(
                                            text = "Check In",
                                            onClick = {
                                                platform.generateBarcode(
                                                    data = parkingNo,
                                                    width = 800,
                                                    height = 150
                                                )
                                                barcode = platform.barcode
                                                val item = Parking(
                                                    parkingNo = parkingNo,
                                                    checkIn = getCurrentDateTime(),
                                                    checkOut = null
                                                )
                                                parking = item
                                                parkingViewModel.onEvent(ParkingEvent.AddParking(item))
                                                isPreview = true
                                                isCheckIn = true
                                            }
                                        )
                                    }
                                }

                                LineWrapper()

                                Box(
                                    modifier = Modifier.fillMaxSize().padding(20.dp),
                                    contentAlignment = Alignment.CenterEnd
                                ) {
                                    if (isPreview) {
                                        Column(
                                            modifier = Modifier.fillMaxSize(),
                                            verticalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            ParkingForm(
                                                imageBitmap = barcode,
                                                isPreview = isPreview,
                                                parking = parking
                                            )

                                            if (!isPaid) {
                                                PrimaryButton(
                                                    text = "Get Ticket".takeIf { isCheckIn } ?: "Payment",
                                                    onClick = {
                                                        if (isCheckIn) {
                                                            isPrint = true
                                                            parkingViewModel.onEvent(ParkingEvent.GetParking())
                                                        } else {
                                                            parkingViewModel.onEvent(ParkingEvent.UpdateParking(parking))
                                                            parkingViewModel.onEvent(ParkingEvent.GetParking())
                                                        }
                                                        isPreview = false
                                                    }
                                                )
                                            }
                                        }
                                    } else {
                                        Box(
                                            modifier = Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Column(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Image(
                                                    modifier = Modifier.size(100.dp),
                                                    painter = painterResource(Res.drawable.parking),
                                                    contentDescription = null
                                                )

                                                Spacer(modifier = Modifier.height(10.dp))

                                                Text(
                                                    text = "Booking Your Parking"
                                                )
                                            }

                                        }
                                    }
                                }

                            }
                        }
                    }
                }
            }

            AnimatedVisibility(
                visible = startBarCodeScan,
                enter = scaleIn(),
                exit = scaleOut()
            ) {
                QrScannerScreen(
                    result = {
                        startBarCodeScan = false

                        if (it.isEmpty()) return@QrScannerScreen
                        parkingViewModel.onEvent(ParkingEvent.SearchParking(it))
                    }
                )
            }
        }
    }
}