package receipt

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import core.theme.Styles
import core.utils.getTextStyle

@Composable
fun ResultTotalItem(
    isPreview: Boolean = false,
    label: String?,
    value: String?,
    labelWeight: Float,
    valueWeight: Float
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        label?.let {
            Text(
                modifier = Modifier.alpha(0.5f).weight(labelWeight),
                text = it, style = getTextStyle(typography = Styles.TitleMedium.takeIf { !isPreview } ?: Styles.LabelSmall),
                textAlign = TextAlign.End
            )
        }
        value?.let {
            Text(
                modifier = Modifier.weight(valueWeight),
                text = it, style = getTextStyle(typography = Styles.TitleLarge.takeIf { !isPreview } ?: Styles.LabelSmall),
                textAlign = TextAlign.End
            )
        }
    }
}