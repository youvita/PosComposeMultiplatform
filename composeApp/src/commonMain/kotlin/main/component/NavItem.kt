package main.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import core.theme.Shapes
import core.theme.textStylePrimary12Normal
import core.utils.ImageLoader
import main.model.NavModel
import main.model.SlideState
import kotlinx.coroutines.launch

private val particlesStreamRadii = mutableListOf<Float>()
private var itemHeight = 0
private var particleRadius = 0f
private var slotItemDifference = 0f

@ExperimentalAnimationApi
@Composable
fun NavCard(
    modifier: Modifier = Modifier,
    navModel: NavModel,
    slideState: SlideState,
    navModelList: MutableList<NavModel>,
    updateSlideState: (navModel: NavModel, slideState: SlideState) -> Unit,
    updateItemPosition: (currentIndex: Int, destinationIndex: Int) -> Unit,
    onSelectedItem: (NavModel) -> Unit
) {
    val itemHeightDp = 70.dp
    with(LocalDensity.current) {
        itemHeight = itemHeightDp.toPx().toInt()
        particleRadius = 3.dp.toPx()
        if (particlesStreamRadii.isEmpty())
            particlesStreamRadii.addAll(arrayOf(6.dp.toPx(), 10.dp.toPx(), 14.dp.toPx()))
        slotItemDifference = 18.dp.toPx()
    }
    val verticalTranslation by animateIntAsState(
        targetValue = when (slideState) {
            SlideState.UP -> -itemHeight
            SlideState.DOWN -> itemHeight
            else -> 0
        },
    )
    val isDragged = remember { mutableStateOf(false) }
    val zIndex = if (isDragged.value) 0.5f else 0.0f
    val currentIndex = remember { mutableStateOf(0) }
    val destinationIndex = remember { mutableStateOf(0) }

    val isPlaced = remember { mutableStateOf(false) }
    LaunchedEffect(isPlaced.value) {
        if (isPlaced.value) {
            launch {
                if (currentIndex.value != destinationIndex.value) {
                    updateItemPosition(currentIndex.value, destinationIndex.value)
                }
                isPlaced.value = false
            }
        }
    }

    Box(
        Modifier
            .padding(horizontal = 16.dp)
            .dragToReorder(
                navModel,
                navModelList,
                itemHeight,
                updateSlideState,
                isDraggedAfterLongPress = true,
                { isDragged.value = true },
                { cIndex, dIndex ->
                    isDragged.value = false
                    isPlaced.value = true
                    currentIndex.value = cIndex
                    destinationIndex.value = dIndex
                }
            )
            .offset { IntOffset(0, verticalTranslation) }
            .zIndex(zIndex)
    ) {
        Box(modifier = Modifier.clip(Shapes.large)) {
            Card(
                modifier = Modifier
                .clickable {
                    onSelectedItem(navModel)
                },
                colors = CardDefaults.cardColors(Color.Transparent)
            ) {
                Column(
                    modifier = modifier
                        .padding(start = 10.dp, end = 10.dp)
                        .size(itemHeightDp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center

                ) {
                    Image(modifier = Modifier.size(25.dp), painter = navModel.icon, contentDescription = null)

                    Spacer(Modifier.height(4.dp))

                    Text(navModel.label, style = textStylePrimary12Normal())

                }
            }
        }
    }
}