package orderhistory.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LineWrapper(
    modifier: Modifier = Modifier
) {
    Divider(
        modifier = modifier
            .alpha(0.5f)
            .fillMaxWidth()
            .background(Color.Black),
        thickness = 1.dp
    )
}