package mario.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.theme.PrimaryColor
import core.theme.Shapes
import core.theme.White
import menu.domain.model.MenuModel
import setting.domain.model.ItemModel

@Composable
fun CreateItem(
    menu: MenuModel? = null,
    onCreate: (ItemModel, Boolean) -> Unit = { _, _ -> },
){
    var required by remember { mutableStateOf(true) }

    var itemModel by remember {
        mutableStateOf(
            ItemModel(
                menuId = menu?.menuId,
                mood = arrayListOf(),
                size = arrayListOf(),
                sugar = arrayListOf(),
                ice = arrayListOf()
            )
        )
    }

    Row {
        //Input product path
        Box(modifier = Modifier.weight(1f)){
            InputItemData(
                menu = menu,
                onItemChange = {
                    itemModel = it
                },
                onRequire = {
                    required = it
                }
            )

            OutlinedButton(
                onClick = {
                    onCreate(itemModel, required)
                },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = PrimaryColor,
                    contentColor = White
                ),
                border = null,
                shape = Shapes.medium,
                contentPadding = PaddingValues(10.dp),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp)
            ) {
                Text(
                    text = "Create",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }
    }
}