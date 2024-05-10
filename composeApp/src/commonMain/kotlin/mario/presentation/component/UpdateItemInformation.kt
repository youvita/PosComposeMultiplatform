//package com.topteam.pos.ui.features.mario.presentation.component
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.defaultMinSize
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.rounded.Delete
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Icon
//import androidx.compose.material3.OutlinedButton
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.share.module.ui.theme.Primary
//import com.share.module.ui.theme.Red
//import com.share.module.ui.theme.Shapes
//import com.share.module.ui.theme.White
//import com.topteam.pos.ui.features.mario.presentation.MarioEvent
//import com.topteam.pos.ui.features.menu.domain.model.MenuModel
//import com.topteam.pos.ui.features.setting.domain.model.ItemModel
//import com.topteam.pos.ui.utils.DialogAlert
//
//@Preview(showBackground = true)
//@Composable
//fun UpdateItemInformation(
//    menu: MenuModel? = null,
//    item: ItemModel? = null,
//    marioEvent: (MarioEvent) -> Unit = {},
//){
//    var itemModel by rememberSaveable { mutableStateOf(item?.copy()?: ItemModel()) }
//    var delete by rememberSaveable { mutableStateOf(false) }
//
//    LaunchedEffect(item) {
//        itemModel = item?.copy()?: ItemModel()
//    }
//    if(delete){
//        DialogAlert(
//            title = "Warning",
//            message = "Are you sure to delete item?",
//            buttonText = "Delete",
//            onButtonClick = {
//                delete = false
//                marioEvent(MarioEvent.DeleteItemEvent(itemModel))
//            },
//            onDismissRequest = {
//                delete = false
//            }
//        )
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxHeight()
//            .clip(shape = Shapes.medium)
//            .background(White)
//    ){
//        Column {
//            Row(
//                modifier = Modifier
//                    .padding(horizontal = 16.dp, vertical = 8.dp),
//            ) {
//                Text(
//                    modifier = Modifier
//                        .weight(1f)
//                        .align(Alignment.CenterVertically),
//                    text = "Item Information",
//                    style = TextStyle(
//                        fontSize = 18.sp,
//                        fontWeight = FontWeight.Bold
//                    )
//                )
//
//                Icon(
//                    imageVector = Icons.Rounded.Delete,
//                    contentDescription = "",
//                    tint = Red,
//                    modifier = Modifier
//                        .align(Alignment.CenterVertically)
//                        .size(24.dp)
//                        .clickable {
//                            delete = true
//                        }
//                )
//            }
//
//            InputItemView(
//                menu = menu,
//                item = itemModel,
//                onItemChange = {
//                    itemModel = it
//                },
//                onRequire = {
//
//                }
//            )
//        }
//
//
//        OutlinedButton(
//            onClick = {
//                marioEvent(MarioEvent.UpdateItemEvent(itemModel))
//            },
//            colors = ButtonDefaults.elevatedButtonColors(
//                containerColor = Primary,
//                contentColor = White
//            ),
//            border = null,
//            shape = Shapes.medium,
//            contentPadding = PaddingValues(10.dp),
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp)
//                .align(Alignment.BottomCenter)
//                .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp)
//        ) {
//            Text(
//                text = "Update",
//                style = TextStyle(
//                    fontSize = 14.sp,
//                    fontWeight = FontWeight.Normal
//                )
//            )
//        }
//    }
//}