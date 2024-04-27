package receipt

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import core.theme.Styles
import core.utils.getTextStyle
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import poscomposemultiplatform.composeapp.generated.resources.Res
import poscomposemultiplatform.composeapp.generated.resources.ic_coffee

@OptIn(ExperimentalResourceApi::class)
@Composable
fun BillHeader(

) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
    ) {
        Box(
            modifier = Modifier
                .padding(top = 10.dp)
                .size(90.dp)
        ) {
            Image(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(8)),
                painter = painterResource(resource = Res.drawable.ic_coffee),
                contentScale = ContentScale.Fit,
                contentDescription = null,
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "ក្រុមហ៊ុន", style = getTextStyle(typography = Styles.HeaderLarge),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Company", style = getTextStyle(typography = Styles.HeaderLarge),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "VAT TIN: E008-15000037167", style = getTextStyle(typography = Styles.TitleMedium),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text ="#120, 93 Street, Sen Sok, Phnom Penh, Cambodia",
                style = getTextStyle(typography = Styles.TitleMedium),
                modifier = Modifier.padding(horizontal = 5.dp),
                textAlign = TextAlign.Center
            )

        }
    }

    Spacer(modifier = Modifier.height(5.dp))
}