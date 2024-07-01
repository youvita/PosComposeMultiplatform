package menu.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.preat.peekaboo.image.picker.toImageBitmap
import core.theme.PrimaryColor
import core.theme.Shapes
import core.theme.White
import core.utils.dollar
import core.utils.formatDouble
import core.utils.percentOf
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import poscomposemultiplatform.composeapp.generated.resources.Res
import poscomposemultiplatform.composeapp.generated.resources.ic_add_circle
import poscomposemultiplatform.composeapp.generated.resources.ic_remove_circle
import poscomposemultiplatform.composeapp.generated.resources.ic_unknown
import setting.domain.model.ItemModel

@OptIn(ExperimentalLayoutApi::class, ExperimentalResourceApi::class)
@Composable
fun OrderItem(
    isDetailHistory: Boolean = false,
    item: ItemModel? = null,
    onRemove: (ItemModel) -> Unit = {},
    onQtyChanged: (Int) -> Unit? = {}
){
    val focusManager = LocalFocusManager.current
    val discount = item?.discount ?: 0
    val price = item?.price ?: 0.0

    val sizes = item?.size?: arrayListOf()
    val size: String = if(sizes.isEmpty()) "" else "Size " + sizes[0].option


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .padding(16.dp)
    ) {
        if (item?.image_product != null && item.image_product!!.isNotEmpty()){
            Image(
                bitmap = item.image_product!!.toImageBitmap(),
                contentDescription = "avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(Shapes.medium)
            )
        }
        else {
            Image(
                painter = painterResource(Res.drawable.ic_unknown),
                contentDescription = "avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(Shapes.medium)
                    .border(0.5.dp, color = Color(0xFFE4E4E4), shape = Shapes.medium)
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = item?.name?: "Unknown",
                style = TextStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            if(discount > 0){
                Text(
                    text = "$discount% off",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            FlowRow(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                if(discount > 0){
                    Text(
                        text = price.dollar(),
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray,
                            textDecoration = TextDecoration.LineThrough
                        )
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                }

                Text(
                    text = (price - formatDouble((discount percentOf price)).toDouble()).dollar(),
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryColor
                    )
                )

                Box(modifier = Modifier.weight(1f)){
                    FlowRow(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        //minus qty item 1
                        Icon(
                            modifier = Modifier
                                .size(24.dp)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() },
                                    enabled = !isDetailHistory
                                ) {
                                    //qty change
                                    if ((item?.qtySelected ?: 1) <= 1){
                                        if (item != null) {
                                            onRemove(item)
                                        }
                                    } else {
                                        onQtyChanged((item?.qtySelected ?: 1).dec())
                                    }
                                },
                            painter = painterResource(resource = Res.drawable.ic_remove_circle),
                            contentDescription = "minus",
                            tint = PrimaryColor.takeIf { !isDetailHistory } ?: Color.Gray
                        )

                        //show qty item
                        BasicTextField(
                            modifier = Modifier
                                .width(IntrinsicSize.Min)
                                .padding(horizontal = 0.dp)
                                .align(Alignment.CenterVertically),
//                            value = qty.toString(),
                            value = item?.qtySelected.toString().takeIf { !isDetailHistory } ?: item?.qty.toString(),
                            onValueChange = {
                                val qtyChange =
                                    if(it.isEmpty())
                                        0
                                    else if (it.toInt() >= (item?.qty ?: 0))
                                        item?.qty?:0
                                    else
                                        it.toInt()
                                onQtyChanged(qtyChange)
                            },
                            textStyle = TextStyle(
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Normal,
                                color = PrimaryColor
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(onDone = {
                                val qtyChange =
                                    if((item?.qtySelected ?: 1) <= 0) 1
                                    else if ((item?.qtySelected ?: 1) >= (item?.qty ?: 0)) {
                                        item?.qty?:0
                                    } else (item?.qtySelected ?: 1)
                                focusManager.clearFocus()
                            }),
                        )

                        //plus qty item 1
                        Icon(
                            modifier = Modifier
                                .size(24.dp)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() },
                                    enabled =
                                        if (isDetailHistory){
                                            false
                                        } else {
                                            (item?.qtySelected ?: 1) < (item?.qty ?: 0)
                                        }
                                ) {
                                    onQtyChanged((item?.qtySelected ?: 1).inc())
                                },
                            painter = painterResource(resource = Res.drawable.ic_add_circle),
                            contentDescription = "plus",
                            tint =
                            if (isDetailHistory) {
                                Color.Gray
                            } else {
                                PrimaryColor.takeIf { (item?.qtySelected ?: 1) < (item?.qty ?: 0) } ?: Color.Gray
                            }
                        )

                        //remove item product from order
                        Icon(
                            imageVector = Icons.Rounded.Delete,
                            contentDescription = "remove cart",
                            tint = Color.Red,
                            modifier = Modifier
                                .alpha(0f.takeIf { isDetailHistory } ?: 1f)
                                .size(24.dp)
                                .clickable (enabled = !isDetailHistory){
                                    if (item != null) {
                                        onRemove(item)
                                    }
                                }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Qty : ${item?.qty}",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            )
        }

        //remove item product from order
//        Icon(
//            imageVector = Icons.Rounded.Delete,
//            contentDescription = "remove cart",
//            tint = Color.Red,
//            modifier = Modifier
//                .size(24.dp)
//                .clickable {
//                    if (item != null) {
//                        onRemove(item)
//                    }
//                }
//        )
    }
}