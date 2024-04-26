package org.topteam.pos.capture

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.onSizeChanged
import core.theme.White

@Composable
fun ScreenCaptureView(
    bitmap: (Bitmap) -> Unit,
    content: @Composable () -> Unit
) {
    val captureState = rememberScreenCaptureState()
    var height by remember {
        mutableIntStateOf(0)
    }

    println("image state:::: ${captureState.imageState}")
    LaunchedEffect(key1 = height) {

        captureState.bitmap?.let { image ->
            bitmap(image)
        }
    }

    ScreenCapture(screenCaptureState = captureState) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .background(White)
        ) {
            content()
        }
    }

    Box(
        modifier = Modifier
            .alpha(0f)
            .fillMaxWidth()
            .background(White)
            .onSizeChanged {
                height = it.height * 2
                println("height:::: $height")
                captureState.capture(options = ScreenCaptureOptions(height = height))
            },
    ) {
        content()
    }

}