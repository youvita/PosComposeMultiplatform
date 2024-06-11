package ui.settings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.theme.Black
import core.theme.PrimaryColor
import core.theme.hintTextColor


sealed class SettingSideBarEvent{
    object PreferenceEvent: SettingSideBarEvent()
    object ReceiptPrinterEvent: SettingSideBarEvent()
    object LabelPrinterEvent: SettingSideBarEvent()
    object AccountEvent: SettingSideBarEvent()
    object PinEvent: SettingSideBarEvent()
}

@Composable
fun SettingSideBar(event: (SettingSideBarEvent) -> Unit = {}){
    var selected by rememberSaveable { mutableIntStateOf(0) }

    val style = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        color = Black
    )

    val styleSelected = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        color = PrimaryColor
    )
    
    val styleHeader = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        color = hintTextColor
    )

    Column(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.17f)
    ) {
        Text(
            modifier = Modifier.padding(start = 20.dp, top = 20.dp, bottom = 8.dp),
            text = "Receipt",
            style = styleHeader
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                selected = 0
                event(SettingSideBarEvent.PreferenceEvent)
            }
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 20.dp, top = 8.dp, bottom = 8.dp)
                    .drawBehind {
                        if (selected == 0) {
                            val strokeWidthPx = 1.dp.toPx()
                            val verticalOffset = size.height - 2.sp.toPx()
                            drawLine(
                                color = PrimaryColor,
                                strokeWidth = strokeWidthPx,
                                start = Offset(0f, verticalOffset),
                                end = Offset(size.width, verticalOffset)
                            )
                        }
                    },
                text = "Preferences",
                style = if(selected == 0) styleSelected else style
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            modifier = Modifier.padding(start = 20.dp, top = 8.dp, bottom = 8.dp),
            text = "Printer Connection",
            style = styleHeader
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    selected = 1
                    event(SettingSideBarEvent.ReceiptPrinterEvent)
                }
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 20.dp, top = 8.dp, bottom = 8.dp)
                    .drawBehind {
                        if (selected == 1) {
                            val strokeWidthPx = 1.dp.toPx()
                            val verticalOffset = size.height - 2.sp.toPx()
                            drawLine(
                                color = PrimaryColor,
                                strokeWidth = strokeWidthPx,
                                start = Offset(0f, verticalOffset),
                                end = Offset(size.width, verticalOffset)
                            )
                        }
                    },
                text = "Receipt Printer",
                style = if(selected == 1) styleSelected else style
            )
        }


//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .clickable {
//                    selected = 2
//                    event(SettingSideBarEvent.LabelPrinterEvent)
//                }
//        ) {
//            Text(
//                modifier = Modifier
//                    .padding(start = 20.dp, top = 8.dp, bottom = 8.dp)
//                    .drawBehind {
//                        if (selected == 2) {
//                            val strokeWidthPx = 1.dp.toPx()
//                            val verticalOffset = size.height - 2.sp.toPx()
//                            drawLine(
//                                color = PrimaryColor,
//                                strokeWidth = strokeWidthPx,
//                                start = Offset(0f, verticalOffset),
//                                end = Offset(size.width, verticalOffset)
//                            )
//                        }
//                    },
//                text = "Label Printer",
//                style = if(selected == 2) styleSelected else style
//            )
//        }
//
//
//        Spacer(modifier = Modifier.height(30.dp))
//
//        Text(
//            modifier = Modifier.padding(start = 20.dp, top = 8.dp, bottom = 8.dp),
//            text = "Security",
//            style = styleHeader
//        )
//
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .clickable {
//                    selected = 3
//                    event(SettingSideBarEvent.AccountEvent)
//                }
//        ) {
//            Text(
//                modifier = Modifier
//                    .padding(start = 20.dp, top = 8.dp, bottom = 8.dp)
//                    .drawBehind {
//                        if (selected == 3) {
//                            val strokeWidthPx = 1.dp.toPx()
//                            val verticalOffset = size.height - 2.sp.toPx()
//                            drawLine(
//                                color = PrimaryColor,
//                                strokeWidth = strokeWidthPx,
//                                start = Offset(0f, verticalOffset),
//                                end = Offset(size.width, verticalOffset)
//                            )
//                        }
//                    },
//                text = "Account",
//                style = if(selected == 3) styleSelected else style
//            )
//        }
//
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .clickable {
//                    selected = 4
//                    event(SettingSideBarEvent.PinEvent)
//                }
//        ) {
//            Text(
//                modifier = Modifier
//                    .padding(start = 20.dp, top = 8.dp, bottom = 8.dp)
//                    .drawBehind {
//                        if (selected == 4) {
//                            val strokeWidthPx = 1.dp.toPx()
//                            val verticalOffset = size.height - 2.sp.toPx()
//                            drawLine(
//                                color = PrimaryColor,
//                                strokeWidth = strokeWidthPx,
//                                start = Offset(0f, verticalOffset),
//                                end = Offset(size.width, verticalOffset)
//                            )
//                        }
//                    },
//                text =  "PIN Code",
//                style = if(selected == 4) styleSelected else style
//            )
//        }

    }
}