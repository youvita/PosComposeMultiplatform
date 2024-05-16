package core.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import core.theme.Black

@Composable
fun LineWrapper(
    modifier: Modifier = Modifier
) {
    HorizontalDivider(
        modifier = modifier
            .alpha(0.5f)
            .fillMaxWidth()
            .background(Black),
        thickness = 1.dp
    )
}