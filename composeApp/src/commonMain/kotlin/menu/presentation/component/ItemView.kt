package menu.presentation.component

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.preat.peekaboo.image.picker.toImageBitmap
import core.theme.ColorDDE3F9
import core.theme.PrimaryColor
import core.theme.Shapes
import core.theme.White
import core.utils.RedRippleTheme
import core.utils.dollar
import core.utils.formatDouble
import core.utils.percentOf
import menu.presentation.OrderEvent
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import poscomposemultiplatform.composeapp.generated.resources.Res
import poscomposemultiplatform.composeapp.generated.resources.ic_bookmark_check
import poscomposemultiplatform.composeapp.generated.resources.ic_bookmark_uncheck
import poscomposemultiplatform.composeapp.generated.resources.ic_dessert
import poscomposemultiplatform.composeapp.generated.resources.ic_scanner
import poscomposemultiplatform.composeapp.generated.resources.ic_unknown
import setting.domain.model.ItemModel
import setting.domain.model.ItemOption

@OptIn(ExperimentalLayoutApi::class, ExperimentalResourceApi::class)
@Composable
fun ItemView(
    modifier: Modifier = Modifier,
    item: ItemModel? = null,
    selected: Boolean = false,
    orderEvent: (OrderEvent) -> Unit = {},
){
    val discount = item?.discount?: 0
    val price = item?.price?: 0.0

    var reset by remember { mutableStateOf(false) }
    var bookmark by remember { mutableStateOf(false) }

    LaunchedEffect(item){
        bookmark = item?.bookmark?: false
    }

    Card(
        modifier = modifier,
        shape = Shapes.medium,
        colors = CardDefaults.cardColors(White),
        elevation = CardDefaults.cardElevation(2.dp)
    ){
        Column {
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
                            .size(78.dp)
                            .clip(Shapes.medium)
                            .border(0.5.dp, color = Color(0xFFE4E4E4), shape = Shapes.medium)
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
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    if(discount > 0){
                        Spacer(modifier = Modifier.height(5.dp))
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
                    ){
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

                            Spacer(modifier = Modifier.width(5.dp))
                        }

                        Text(
                            text = (price - formatDouble((discount percentOf price)).toDouble()).dollar(),
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
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

                Image(
                    painter = if(bookmark) painterResource(Res.drawable.ic_bookmark_check) else painterResource(Res.drawable.ic_bookmark_uncheck),
                    contentDescription = "bookmark",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember {
                                MutableInteractionSource()
                            }
                        ) {
                            bookmark = !bookmark
                            if(item != null){
                                item.bookmark = bookmark
                                orderEvent(OrderEvent.BookmarkItemEvent(item))
                            }
                        }
                )
            }

            AnimatedVisibility(visible = selected){
                Column {
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = "Product SKU: ${item?.product_id}",
                        style = TextStyle(
                            fontSize = 15.sp,
                        )
                    )

                    OutlinedButton(
                        onClick = {
                            if(item  != null){
                                orderEvent(OrderEvent.SelectOrderEvent(item))
                                reset = true
                            }
                        },
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = ColorDDE3F9,
                            contentColor = PrimaryColor
                        ),
                        border = null,
                        contentPadding = PaddingValues(10.dp),
                        shape = Shapes.medium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),
                        enabled = (item?.qty ?: 0) > 0
                    ) {
                        Text(
                            text = "Add to Billing",
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }
    }
}
