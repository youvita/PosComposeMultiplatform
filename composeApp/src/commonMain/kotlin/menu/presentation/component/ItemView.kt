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
import core.theme.ColorDDE3F9
import core.theme.PrimaryColor
import core.theme.Shapes
import core.theme.White
import core.utils.RedRippleTheme
import core.utils.dollar
import core.utils.percentOf
import menu.presentation.OrderEvent
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
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
    
    val orderItem = remember { mutableStateOf(item?.copy()) }
    var reset by rememberSaveable { mutableStateOf(false) }
    var hot by rememberSaveable { mutableStateOf(false) }
    var bookmark by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(item){
        bookmark = item?.bookmark?: false

        if(item?.mood?.isNotEmpty() == true){
            hot = "hot".equals(item.mood?.get(0)?.option ?: "", true)
        }
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

                Image(
                    painter = painterResource(resource = DrawableResource(item?.imageUrl?:"")),
                    contentDescription = "avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(shape = Shapes.medium)
                        .border(width = 0.5.dp, shape = Shapes.medium, color = Color(0xFFE4E4E4))
                )

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

                        }

                        Spacer(modifier = Modifier.width(5.dp))

                        Text(
                            text = (price - (discount percentOf price)).dollar(),
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }

                Icon(
                    imageVector = if(bookmark) Icons.Rounded.Star else Icons.Filled.Star,
                    contentDescription = "bookmark",
                    tint = Color(0xFFFFD600),
                    modifier = Modifier
                        .size(28.dp)
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
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.wrapContentSize()
                    ){
                        // Mood
                        if(!item?.mood.isNullOrEmpty()){
                            Column(
                                Modifier
                                    .padding(10.dp)
                                    .weight(1f)) {
                                Text(
                                    text = "Mood",
                                    style = TextStyle(
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                OptionItem(options = item?.mood, reset = reset){
                                    if(it != null && item != null){
                                        orderItem.value = orderItem.value?.copy(mood = arrayListOf(it))
                                        reset = false
                                        hot = "hot".equals(it.option, true)
                                    }
                                }
                            }
                        }

                        // Size
                        if(!item?.size.isNullOrEmpty()){
                            Column(
                                Modifier
                                    .padding(10.dp)
                                    .weight(1f)) {
                                Text(
                                    text = "Size",
                                    style = TextStyle(
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                OptionItem(options = item?.size, reset = reset){
                                    if(it != null && item != null){
                                        orderItem.value = orderItem.value?.copy(size = arrayListOf(it))
                                        reset = false
                                    }
                                }
                            }
                        }
                    }

                    Row{
                        // Sugar
                        if(!item?.sugar.isNullOrEmpty()){
                            Column(
                                Modifier
                                    .padding(10.dp)
                                    .weight(1f)) {
                                Text(
                                    text = "Sugar",
                                    style = TextStyle(
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                OptionItem(options = item?.sugar, reset = reset){
                                    if(it != null && item != null){
                                        orderItem.value = orderItem.value?.copy(sugar = arrayListOf(it))
                                        reset = false
                                    }
                                }
                            }
                        }


                        // Ice
                        if(!item?.ice.isNullOrEmpty() && !hot){
                            Column(
                                Modifier
                                    .padding(10.dp)
                                    .weight(1f)) {
                                Text(
                                    text = "Ice",
                                    style = TextStyle(
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                OptionItem(options = item?.ice, reset = reset){
                                    if(it != null && item != null){
                                        orderItem.value = orderItem.value?.copy(ice = arrayListOf(it))
                                        reset = false
                                    }
                                }
                            }
                        }
                    }

                    OutlinedButton(
                        onClick = {
                            val ordered = orderItem.value
                            if(ordered  != null){
                                orderEvent(OrderEvent.SelectOrderEvent(ordered))
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
                            .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp)
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

@OptIn(ExperimentalLayoutApi::class, ExperimentalResourceApi::class)
@Composable
private fun OptionItem(options: List<ItemOption>? = null,
                       reset: Boolean = false,
                       onOptionChanged: (ItemOption?) -> Unit = {}){
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }

    LaunchedEffect(reset){
        if(reset) selectedItem = 0
    }

    FlowRow{
        repeat(options?.size?: 0){ index ->
            val item = options?.get(index) ?: return@FlowRow

            CompositionLocalProvider(LocalRippleTheme provides RedRippleTheme){
                Box(modifier = Modifier
                    .padding(4.dp)
                    .size(30.dp)
                    .aspectRatio(1f)
                    .background(Color(0xFFEFEFEF), shape = CircleShape)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        selectedItem = index
                        onOptionChanged(item)
                    }
                    .then(
                        if (selectedItem == index) {
                            Modifier
                                .background(color = ColorDDE3F9, shape = CircleShape)
                                .border(1.dp, color = PrimaryColor, shape = CircleShape)
                        } else {
                            Modifier
                        }
                    ),
                    contentAlignment = Alignment.Center
                ) {
                    val img = item.image
                    if(img != null){
                        Image(
                            modifier = Modifier.size(16.dp),
                            painter = painterResource(resource = img),
                            contentDescription = ""
                        )
                    }else{
                        Text(
                            text = item.option?: "",
                            style = TextStyle(
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }
    }
}
