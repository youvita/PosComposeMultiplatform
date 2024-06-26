package core.scanner

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import poscomposemultiplatform.composeapp.generated.resources.Res
import poscomposemultiplatform.composeapp.generated.resources.ic_exit
import qrscanner.QrCodeScanner

@OptIn(ExperimentalResourceApi::class)
@Composable
fun QrScannerScreen(
    result: (String) -> Unit = {}
) {
    val factory: PermissionsControllerFactory = rememberPermissionsControllerFactory()
    val controller: PermissionsController = remember(factory) { factory.createPermissionsController() }
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    var isGranted by remember { mutableStateOf(false) }

    coroutineScope.launch {
        controller.providePermission(Permission.CAMERA)
        if (controller.isPermissionGranted(Permission.CAMERA)) {
            isGranted = true
        }
    }

    BindEffect(controller)
    if (isGranted) {
        Dialog(
            onDismissRequest = {}
        ) {
            Box(modifier = Modifier.fillMaxSize().statusBarsPadding()) {
                Column(
                    modifier = Modifier
                        .background(color = Color.Transparent)
                        .windowInsetsPadding(WindowInsets.safeDrawing)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .background(color = Color.Transparent)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(250.dp)
                                .clip(shape = RoundedCornerShape(size = 14.dp))
                                .clipToBounds()
                                .border(2.dp, Color.Gray, RoundedCornerShape(size = 14.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            QrCodeScanner(
                                modifier = Modifier
                                    .clipToBounds()
                                    .clip(shape = RoundedCornerShape(size = 14.dp)),
                                flashlightOn = false,
                                onCompletion = {
                                    result(it)
                                },
                            )
                        }

                        Icon(
                            modifier = Modifier
                                .padding(5.dp)
                                .align(Alignment.TopEnd)
                                .clickable {
                                    result("")
                                },
                            painter = painterResource(resource = Res.drawable.ic_exit),
                            contentDescription = ""
                        )
                    }
                }
            }
        }
    }

}