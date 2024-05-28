package orderhistory.presentation

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.topteam.pos.ui.features.history.presentation.component.PaginationContent
import core.theme.PrimaryColor
import core.theme.White
import orderhistory.presentation.component.TransactionTable
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.UtcOffset
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.format
import kotlinx.datetime.toLocalDateTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderHistoryScreen(
    orderHistoryState: OrderHistoryState,
    pagingState: PagingState,
    historyEvent: (OrderHistoryEvent) -> Unit = {}
) {

    var isInputEmpty by remember { mutableStateOf(true) }
    var date by mutableStateOf("")

    val currentMoment = Clock.System.now()
    val datetimeInSystemZone: LocalDateTime = currentMoment.toLocalDateTime(TimeZone.currentSystemDefault())

    val dadadda = DateTimeComponents.Formats.RFC_1123.format {
        setDate(datetimeInSystemZone.date)
        hour = 0
        minute = 0
        second = 0
        setOffset(UtcOffset(hours = 0))
    }

    println(">>>>> : ${currentMoment.toEpochMilliseconds()}")
    println(">>>>> : date formatted ${dadadda}")

    // set the initial date
    val datePickerStartState = rememberDatePickerState(initialSelectedDateMillis = currentMoment.toEpochMilliseconds())
    val datePickerEndState = rememberDatePickerState(initialSelectedDateMillis = currentMoment.toEpochMilliseconds())

    var showDatePickerStart by remember {
        mutableStateOf(false)
    }
    var showDatePickerEnd by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit){
        historyEvent(OrderHistoryEvent.GetOrderOrderHistoryEvent)
    }

    Scaffold(
        containerColor = Color.Transparent
    ) { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "Transaction Order History",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row (
                    modifier = Modifier.weight(3f)
                ){
                    //Transaction History
                    Card (
                        modifier = Modifier
                            .weight(2f)
                            .fillMaxHeight(),
                        shape = RoundedCornerShape(10.dp),
                        elevation = CardDefaults.elevatedCardElevation().also { 10.dp },
                        colors = CardDefaults.cardColors(White),
                    ){
                        Row (
                            modifier = Modifier.fillMaxWidth()
                        ){
                            //Input Search
                            TextField(
                                value = orderHistoryState?.searchText?:"",
//                                value = "",
                                onValueChange = {
                                    historyEvent(OrderHistoryEvent.SearchEventOrder(it))
                                    isInputEmpty = it.isEmpty() },
                                modifier = Modifier
                                    .weight(3f)
                                    .padding(16.dp)
                                    .focusRequester(remember { FocusRequester() }),
                                shape = RoundedCornerShape(10.dp),
                                placeholder = { Text("Search order ID, Cashier, Amount", maxLines = 1) },
                                trailingIcon = {
                                    if (!isInputEmpty){
                                        Icon(
                                            imageVector = Icons.Default.Clear,
                                            contentDescription = "Clear",
                                            tint = PrimaryColor,
                                            modifier = Modifier.clickable {
                                                // Handle clear action
                                                historyEvent(OrderHistoryEvent.ClearEventOrder)
                                                isInputEmpty = true
                                            }
                                        )
                                    } else {
                                        Icon(
                                            imageVector = Icons.Outlined.Search,
                                            contentDescription = "Search",
                                            tint = PrimaryColor
                                        )
                                    }
                                },
                                colors = TextFieldDefaults.textFieldColors(
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent
                                )
                            )

                            //Start date
                            if (showDatePickerStart) {
                                DatePickerDialog(
                                    onDismissRequest = {
                                        showDatePickerStart = false
                                        date = ""
                                    },
                                    confirmButton = {
                                        TextButton(onClick = {
                                            showDatePickerStart = false
                                            showDatePickerEnd = true
                                            date = TextFieldValue(
                                                DateTimeComponents.Formats.RFC_1123.format {
                                                    setDate(epochMillisToLocalDate(datePickerStartState.selectedDateMillis?:0))
                                                    hour = 0
                                                    minute = 0
                                                    second = 0
                                                    setOffset(UtcOffset(hours = 0))
                                                }
                                            ).text.substring(5,16) //"Thu, 25 Apr 2024 00:00 GMT" to 25 Apr 2024
                                        }) {
                                            Text(text = "Confirm")
                                        }
                                    },
                                    dismissButton = {
                                        TextButton(onClick = {
                                            showDatePickerStart = false
                                            date = ""
                                        }) {
                                            Text(text = "Cancel")
                                        }
                                    }
                                ) {
                                    DatePicker(
                                        state = datePickerStartState,
                                        title = {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(top = 10.dp),
                                                contentAlignment = Alignment.Center
                                            ){
                                                Text(
                                                    text = "Select start date",
                                                    fontSize = 20.sp,
                                                )
                                            }
                                        }
                                    )
                                }
                            }

                            //End date
                            if (showDatePickerEnd) {
                                DatePickerDialog(
                                    onDismissRequest = {
                                        showDatePickerEnd = false
                                        date = ""
                                    },
                                    confirmButton = {
                                        TextButton(onClick = {
                                            showDatePickerEnd = false
                                            date += " - "+TextFieldValue(
                                                DateTimeComponents.Formats.RFC_1123.format {
                                                    setDate(epochMillisToLocalDate(datePickerEndState.selectedDateMillis?:0))
                                                    hour = 0
                                                    minute = 0
                                                    second = 0
                                                    setOffset(UtcOffset(hours = 0))
                                                }
                                            ).text.substring(5,16) //"Thu, 25 Apr 2024 00:00 GMT" to 25 Apr 2024
                                        }) {
                                            Text(text = "Confirm")
                                        }
                                    },
                                    dismissButton = {
                                        TextButton(onClick = {
                                            showDatePickerEnd = false
                                            date = ""
                                        }) {
                                            Text(text = "Cancel")
                                        }
                                    }
                                ) {
                                    DatePicker(
                                        state = datePickerEndState,
                                        title = {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(top = 10.dp),
                                                contentAlignment = Alignment.Center
                                            ){
                                                Text(
                                                    text = "Select end date",
                                                    fontSize = 20.sp,
                                                )
                                            }
                                        }
                                    )
                                }
                            }


                            //date picker
                            TextField(
                                value = date,
                                singleLine = true,
                                enabled = false,
                                placeholder = { Text("23 Jan 2024 - 31 Dec 2024", maxLines = 1) },
                                modifier = Modifier
                                    .weight(2f)
                                    .padding(top = 16.dp, end = 16.dp, bottom = 16.dp)
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ) {
                                        showDatePickerStart = true
                                    },
                                colors = TextFieldDefaults.textFieldColors(
                                    cursorColor = Color.Black,
                                    disabledTextColor = Color.Black,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent
                                ),
                                shape = RoundedCornerShape(10.dp),
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.DateRange,
                                        tint = PrimaryColor,
                                        contentDescription = "Pick Date",
                                    )
                                },
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.KeyboardArrowDown,
                                        tint = PrimaryColor,
                                        contentDescription = "Pick Date",
                                    )
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                onValueChange = {
                                    date = it
                                }
                            )
                        }

                        Box(modifier = Modifier.weight(1f)){
                            //Table
                            TransactionTable(
                                state = orderHistoryState,
                                historyEvent = historyEvent
                            )
                        }

                        PaginationContent(
                            pagingState = pagingState,
                            historyEvent = historyEvent
                        )

                    }

                    Spacer(modifier = Modifier.width(15.dp))

                    //Customer Info
                    Card (
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        shape = RoundedCornerShape(10.dp),
                        elevation = CardDefaults.elevatedCardElevation().also { 10.dp },
                        colors = CardDefaults.cardColors(White),
                    ){

                    }
                }
            }
        }
    }

}

fun epochMillisToLocalDate(epochMillis: Long): LocalDate {
    val instant: Instant = Instant.fromEpochMilliseconds(epochMillis)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return LocalDate(localDateTime.year, localDateTime.monthNumber, localDateTime.dayOfMonth)
}