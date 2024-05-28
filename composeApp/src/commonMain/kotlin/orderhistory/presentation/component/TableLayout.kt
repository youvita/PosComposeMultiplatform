package orderhistory.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import core.theme.Shapes

@Composable
fun TableLayout(
    modifier: Modifier = Modifier,
    shapes: Shape = Shapes.extraSmall,
    border: BorderStroke = BorderStroke(0.5.dp, Gray),
    dataColumn: @Composable (Modifier) -> Unit,
    dataRow: @Composable (Modifier) -> Unit,
) {
    Card(
        modifier = modifier,
        shape = shapes,
        colors = CardDefaults.cardColors(White),
        border = border,
    ) {
        Column {
            dataColumn(modifier)

            dataRow(modifier.weight(1f))
        }
    }
}