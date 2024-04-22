package main.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import core.theme.ColorDDE3F9
import core.theme.PrimaryColor
import core.theme.Shapes
import main.model.NavModel
import main.model.SlideState
import core.theme.White

@Composable
fun NavigationTabScaffold(
    containerColor: Color = White,
    navModels: Array<NavModel>,
    content: @Composable (Int) -> Unit
) {
    val navList = remember { mutableStateListOf(*navModels) }
    val slideStates = remember {
        mutableStateMapOf<NavModel, SlideState>()
            .apply {
                navList.map { navItem ->
                    navItem to SlideState.NONE
                }.toMap().also {
                    putAll(it)
                }
            }
    }
    var indexSelected by rememberSaveable {
        mutableStateOf(0)
    }
    Scaffold(
        containerColor = containerColor
    ) { paddingValues ->
        Row(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        ) {
            NavItemList(
                navList = navList,
                slideStates = slideStates,
                updateSlideState = { navItem, slideState -> slideStates[navItem] = slideState },
                updateItemPosition = { currentIndex, destinationIndex ->
                    val nav = navList[currentIndex]
                    navList.removeAt(currentIndex)
                    navList.add(destinationIndex, nav)
                    slideStates.apply {
                        navList.associateWith {
                            SlideState.NONE
                        }.also {
                            putAll(it)
                        }
                    }
                },
                onSelectedItem = {
                    indexSelected = it
                }
            )
            Box(
                modifier = Modifier
                    .alpha(0.5f)
                    .fillMaxHeight()
                    .width(1.dp)
                    .shadow(
                        elevation = 10.dp
                    )
            )
            Box(modifier = Modifier.fillMaxWidth()) {
                content(indexSelected)
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavItemList(
    navList: MutableList<NavModel>,
    slideStates: Map<NavModel, SlideState>,
    updateSlideState: (navModel: NavModel, slideState: SlideState) -> Unit,
    updateItemPosition: (currentIndex: Int, destinationIndex: Int) -> Unit,
    onSelectedItem: (Int) -> Unit
) {
    var itemSelected by rememberSaveable {
        mutableStateOf(0)
    }
    val lazyListState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier.fillMaxHeight(),
        state = lazyListState,
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
        }
        items(navList.size) { index ->
            val navItem = navList.getOrNull(index)
            if (navItem != null) {
                key(navItem) {
                    val slideState = slideStates[navItem] ?: SlideState.NONE
                    NavCard(
                        modifier = Modifier.then(
                        if (itemSelected == navItem.id) {
                            Modifier
                                .background(color = ColorDDE3F9, shape = Shapes.medium)
                                .border(2.dp, color = PrimaryColor, shape = Shapes.medium)
                        } else {
                            Modifier
                        }
                        ),
                        navModel = navItem,
                        slideState = slideState,
                        navModelList = navList,
                        updateSlideState = updateSlideState,
                        updateItemPosition = updateItemPosition,
                        onSelectedItem = {
                            itemSelected = it.id
                            onSelectedItem(itemSelected)
                        }
                    )
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}