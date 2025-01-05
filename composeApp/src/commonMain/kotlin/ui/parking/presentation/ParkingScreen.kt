package ui.parking.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.topteam.pos.ui.features.history.presentation.component.PaginationContent
import core.theme.PrimaryColor
import core.theme.White
import getPlatform
import kotlinx.datetime.UtcOffset
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.format
import mario.presentation.MarioViewModel
import menu.presentation.OrderEvent
import orderhistory.presentation.OrderHistoryEvent
import orderhistory.presentation.component.OrderBillsDetailForm
import orderhistory.presentation.component.TransactionTable
import orderhistory.presentation.epochMillisToLocalDate
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import poscomposemultiplatform.composeapp.generated.resources.Res
import poscomposemultiplatform.composeapp.generated.resources.ic_back
import poscomposemultiplatform.composeapp.generated.resources.ic_scanner
import ui.parking.presentation.component.ParkingBody
import ui.parking.presentation.component.ParkingHeader
import ui.stock.domain.model.ProductMenu

@OptIn(ExperimentalResourceApi::class)
class ParkingScreen: Screen, KoinComponent {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val platform = getPlatform()

        var barcode by remember { mutableStateOf(ImageBitmap(100, 50)) }
        var isPreview by remember { mutableStateOf(false) }
        var isPrint by remember { mutableStateOf(false) }

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

                    Text(
                        text = "Transaction Parking Details",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        ) {
            Box(
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Row(
                    modifier = Modifier
                ) {
                    //Transaction History
                    Card(
                        modifier = Modifier
                            .weight(2f)
                            .fillMaxHeight(),
                        shape = RoundedCornerShape(10.dp),
                        elevation = CardDefaults.elevatedCardElevation().also { 10.dp },
                        colors = CardDefaults.cardColors(White),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row {
                                TextField(
                                    value = "",
                                    onValueChange = {
//                                        historyEvent(OrderHistoryEvent.SearchOrder(it))
//                                        isInputEmpty = it.isEmpty()
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

                                Button(
                                    onClick = {
                                        isPreview = true
                                        platform.generateBarcode(
                                            data = "AT-123-000",
                                            width = 500,
                                            height = 100
                                        )
                                        barcode = platform.barcode
                                    }
                                ) {
                                    Text("Booking")
                                }
                            }

                            Box(modifier = Modifier.weight(1f)) {
                                //Table
//                                TransactionTable(
//                                    state = orderHistoryState,
//                                    orderSearchList = orderSearchList,
//                                    focusManager = focusManager,
//                                    historyEvent = historyEvent
//                                )
                            }

//                            PaginationContent(
//                                pagingState = pagingState,
//                                historyEvent = historyEvent
//                            )

                        }

                    }

                    Spacer(modifier = Modifier.width(15.dp))

                    //Customer Info
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        shape = RoundedCornerShape(10.dp),
                        elevation = CardDefaults.elevatedCardElevation().also { 10.dp },
                        colors = CardDefaults.cardColors(White),
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(20.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            if (isPreview) {
                                ParkingForm(
                                    imageBitmap = barcode
                                )
                            }
                        }

                        Button(
                            onClick = {
                                isPrint = true
                            }
                        ) {
                            Text("Print")
                        }
                    }
                }
            }

            if (isPrint) {
                platform.Capture(0) {
                    Box(
                        modifier = Modifier.fillMaxWidth().background(White)
                    ) {
                        ParkingHeader()
                    }
                }

                platform.Capture(1) {
                    Box(
                        modifier = Modifier.fillMaxWidth().background(White)
                    ) {
                        ParkingBody()
                    }
                }

                platform.Capture(2) {
                    Box(
                        modifier = Modifier.fillMaxWidth().background(White)
                    ) {
                        ParkingHeader()
                    }
                }
            }
        }
    }
}