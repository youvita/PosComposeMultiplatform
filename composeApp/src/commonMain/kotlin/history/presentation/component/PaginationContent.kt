package com.topteam.pos.ui.features.history.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.theme.Color000000_15
import history.presentation.HistoryEvent
import history.presentation.PagingState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import poscomposemultiplatform.composeapp.generated.resources.Res
import poscomposemultiplatform.composeapp.generated.resources.ic_next
import poscomposemultiplatform.composeapp.generated.resources.ic_next_available
import poscomposemultiplatform.composeapp.generated.resources.ic_previous
import poscomposemultiplatform.composeapp.generated.resources.ic_previous_available
import poscomposemultiplatform.composeapp.generated.resources.ic_to_end
import poscomposemultiplatform.composeapp.generated.resources.ic_to_end_available
import poscomposemultiplatform.composeapp.generated.resources.ic_to_first
import poscomposemultiplatform.composeapp.generated.resources.ic_to_first_available


@OptIn(ExperimentalResourceApi::class)
@Composable
fun PaginationContent(
    pagingState: PagingState,
    historyEvent: (HistoryEvent) -> Unit = {}
) {

//    val pagingState = historyViewModel.pagingState.value

    Row (
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(20.dp)
    ){
        Text(text = "Page rows")

        //set limit page
        PageLimitDropDown(limitRow = pagingState.limitRow){
            historyEvent(HistoryEvent.SetLimitRow(it))
        }

        Text(text = "${pagingState.currentPage}-${pagingState.totalPage} of ${pagingState.allRow}")

        //Click to first page
        IconButton(
            onClick = {
                if ((pagingState.currentPage ?: 0) > 1){
                    historyEvent(HistoryEvent.NextPage(
                        offset = 0,
                        totalPage = pagingState.totalPage,
                        currentPage = 1
                    ))
                }
            }
        ) {
            Image(
                painter = painterResource(
                    resource = if ((pagingState.currentPage?:0) > 1){
                        Res.drawable.ic_to_first_available
                    } else {
                        Res.drawable.ic_to_first
                    }
                ),
                contentDescription = ""
            )
        }
        //Click to previous page
        IconButton(
            onClick = {
                if ((pagingState.currentPage ?: 1) >= 2){ //if currentPage = 1 can't back previous more
                    historyEvent(HistoryEvent.NextPage(
                        offset = pagingState.currentPage.minus(2).times(pagingState.limitRow),
                        totalPage = pagingState.totalPage,
                        currentPage = pagingState.currentPage.minus(1)
                    ))
                }
            }
        ) {
            Image(
                painter = painterResource(
                    resource = if ((pagingState.currentPage) > 1){
                        Res.drawable.ic_previous_available
                    } else {
                        Res.drawable.ic_previous
                    }
                ),
                contentDescription = ""
            )
        }
        //Click to next page
        IconButton(
            onClick = {
                if ((pagingState.currentPage)+1 <= (pagingState.totalPage ?: 0)){
                    historyEvent(HistoryEvent.NextPage(
                        offset = pagingState.currentPage.times(pagingState.limitRow),
                        totalPage = pagingState.totalPage,
                        currentPage = pagingState.currentPage.plus(1)
                    ))
                }
            }
        ) {
            Image(
                painter = painterResource(
                    resource = if ((pagingState.totalPage?:0) > (pagingState.currentPage ?: 0)){
                        Res.drawable.ic_next_available
                    } else {
                        Res.drawable.ic_next
                    }
                ),
                contentDescription = ""
            )
        }
        //Click to end page
        IconButton(
            onClick = {
                if ((pagingState.totalPage?:0) > (pagingState.currentPage ?: 0)){
                    historyEvent(HistoryEvent.NextPage(
                        offset = pagingState.totalPage.minus(1).times(pagingState.limitRow),
                        totalPage = pagingState.totalPage,
                        currentPage = pagingState.totalPage
                    ))
                }
            }
        ) {

            Image(
                painter = painterResource(
                    resource = if ((pagingState.totalPage?:0) > (pagingState.currentPage ?: 0)){
                        Res.drawable.ic_to_end_available
                    } else {
                        Res.drawable.ic_to_end
                    }
                ),
                contentDescription = ""
            )
        }

    }
}
@Composable
fun PageLimitDropDown(
    limitRow: Int?,
    onClicked: (number: Int)-> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .wrapContentSize()
            .wrapContentSize(Alignment.TopEnd)
    ) {

        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .border(1.dp, color = Color000000_15, shape = RoundedCornerShape(7.dp))
                .padding(start = 15.dp, top = 3.dp, bottom = 3.dp)
        ){
            Text(text = "$limitRow")
            IconButton(
                onClick = { expanded = !expanded },
                modifier = Modifier.height(34.dp)
            ) {
                Image(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "",
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("5") },
                onClick = {
                    onClicked(5)
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("10") },
                onClick = {
                    onClicked(10)
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("15") },
                onClick = {
                    onClicked(15)
                    expanded = false
                }
            )
        }
    }
}