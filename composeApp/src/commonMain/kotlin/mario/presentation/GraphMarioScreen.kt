package mario.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import mario.presentation.component.MarioItem
import mario.presentation.component.MarioToolbar
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import poscomposemultiplatform.composeapp.generated.resources.Res
import poscomposemultiplatform.composeapp.generated.resources.img_employee
import ui.stock.presentation.InventoryScreen

data class GraphMarioScreen(
    val screenType: String = ScreenMario.MainScreen.route,
    val wrapContent: Boolean = false
) : Screen , KoinComponent {

    override val key = uniqueScreenKey // This key will be used to find the screen in the Navigator

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        var image by remember { mutableStateOf(ImageBitmap(1,1)) }

        val marioViewModel = get<MarioViewModel>()
        val marioState = marioViewModel.state.collectAsState().value

        when(screenType){
            ScreenMario.MainScreen.route -> {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    MarioToolbar(screenType)
                    Spacer(modifier = Modifier.height(16.dp))

                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Fixed(3),
                        verticalItemSpacing = 8.dp,
                        horizontalArrangement = Arrangement.spacedBy(15.dp),
                        modifier = Modifier.fillMaxSize()
                    ){
//                        item {
//                            MarioItem(
//                                name = "Menu Management",
//                                image = Res.drawable.img_menu
//                            ){
//                                navigator.push(
//                                    GraphMarioScreen(
//                                        screenType = ScreenMario.MenuScreen.route,
//                                        wrapContent = false
//                                    )
//                                )
//                            }
//                        }
//
//                        item{
//                            MarioItem(
//                                name = "Employee Management",
//                                image = Res.drawable.img_employee
//                            ) {
//                                navigator.push(
//                                    GraphMarioScreen(
//                                        screenType = ScreenMario.EmployeeScreen.route,
//                                        wrapContent = false
//                                    )
//                                )
//                            }
//                        }

                        item{
                            MarioItem(
                                name = "Product & Stock",
                                image = Res.drawable.img_employee
                            ) {
                                navigator.push(
                                    InventoryScreen()
                                )
                            }
                        }
                    }

                }
            }

//            ScreenMario.MenuScreen.route -> {
//                Column(
//                    modifier = Modifier.padding(16.dp)
//                ) {
//                    //Toolbar
//                    MarioToolbar(screenType, navigator)
//                    Spacer(modifier = Modifier.height(16.dp))
//                    MarioMenuScreen(
//                        marioState = marioState,
//                        marioEvent = marioViewModel::onEvent,
//                    )
//                }
//            }
//
//            ScreenMario.EmployeeScreen.route -> {
//                Column(
//                    modifier = Modifier.padding(16.dp)
//                ) {
//                    MarioToolbar(screenType, navigator)
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    val scope = rememberCoroutineScope()
//
//                    val singleImagePicker = rememberImagePickerLauncher(
//                        selectionMode = SelectionMode.Single,
//                        scope = scope,
//                        onResult = { byteArrays ->
//                            byteArrays.firstOrNull()?.let {
//                                // Process the selected images' ByteArrays.
//                                byteArrays.firstOrNull()?.let {byteArrays ->
//                                    image = byteArrays.toImageBitmap()
//                                }
//
//                            }
//                        }
//                    )
//
//                    Button(
//                        onClick = {
//                            singleImagePicker.launch()
//                        }
//                    ) {
//                        Text("Pick Single Image")
//                    }
//
//
//                    Image(
//                        bitmap = image,
//                        contentDescription = "Selected Image",
//                        modifier =
//                        Modifier
//                            .size(200.dp)
//                            .clip(shape = RoundedCornerShape(12.dp)),
//                        contentScale = ContentScale.Crop,
//                    )
//
//                }
//            }
        }
    }
}


