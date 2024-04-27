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
fun ResultItem(
    label: String?,
    value: String?,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        label?.let {
            Text(
                modifier = Modifier.alpha(0.5f),
                text = it, style = getTextStyle(typography = Styles.TitleMedium),
                textAlign = TextAlign.Start
            )
        }
        value?.let {
            Text(
                text = it, style = getTextStyle(typography = Styles.TitleLarge),
                textAlign = TextAlign.End
            )
        }
    }
}