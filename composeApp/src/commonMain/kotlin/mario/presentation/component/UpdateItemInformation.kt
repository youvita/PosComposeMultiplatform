package mario.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.theme.PrimaryColor
import core.theme.Shapes
import core.theme.White
import core.utils.DialogAlert
import mario.presentation.MarioEvent
import menu.domain.model.MenuModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import setting.domain.model.ItemModel


@Composable
fun UpdateItemInformation(
    menu: MenuModel? = null,
    item: ItemModel? = null,
    marioEvent: (MarioEvent) -> Unit = {},
){
    var itemModel by remember { mutableStateOf(item?:ItemModel()) }
    var delete by remember { mutableStateOf(false) }

    if(delete){
        DialogAlert(
            title = "Warning",
            message = "Stock will be update.\nAre you sure to delete item?",
            buttonText = "Delete",
            onButtonClick = {
                delete = false
                marioEvent(MarioEvent.DeleteItemEvent(itemModel))
            },
            onDismissRequest = {
                delete = false
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .clip(shape = Shapes.medium)
            .background(White)
    ){
        Column {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    text = "Item Information",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = "",
                    tint = Red,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(24.dp)
                        .clickable {
                            delete = true
                        }
                )
            }
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
            InputItemData(
                menu = menu,
                oldItem = itemModel,
                onItemChange = {
                    itemModel = it
                },
                onRequire = {

                }
            )
        }


        OutlinedButton(
            onClick = {
                marioEvent(MarioEvent.UpdateItemEvent(itemModel))
            },
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = PrimaryColor,
                contentColor = White
            ),
            border = null,
            shape = Shapes.medium,
            contentPadding = PaddingValues(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .align(Alignment.BottomCenter)
                .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp)
        ) {
            Text(
                text = "Update",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            )
        }
    }
}