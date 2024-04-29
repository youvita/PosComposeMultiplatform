package core.utils

import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import cafe.adriel.voyager.navigator.Navigator
import core.theme.Styles
import core.theme.textStyleBlack13Bold
import core.theme.textStyleBlack13Medium
import core.theme.textStyleBlack13Small
import core.theme.textStyleBlack14Bold
import core.theme.textStyleBlack14Medium
import core.theme.textStyleBlack14Small
import core.theme.textStyleBlack15Bold
import core.theme.textStyleBlack15Medium
import core.theme.textStyleBlack15Small
import core.theme.textStyleBlack17Bold
import core.theme.textStyleBlack17Medium
import core.theme.textStyleBlack17Small
import core.theme.textStyleBlack20Bold
import core.theme.textStyleBlack20Medium
import core.theme.textStyleBlack20Small
import core.theme.textStyleBlack25Bold
import core.theme.textStyleBlack25Medium
import core.theme.textStyleBlack25Small
import core.theme.textStyleBlack30Bold
import core.theme.textStyleBlack30Medium
import core.theme.textStyleBlack30Small

val LocalAppNavigator: ProvidableCompositionLocal<Navigator?> = staticCompositionLocalOf { null }

@Composable
fun ProvideAppNavigator(navigator: Navigator, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalAppNavigator provides navigator) {
        content()
    }
}

@Composable
fun getTextStyle(typography: Styles): TextStyle {
    return when(SharePrefer.getPrefer("paper_width").toFloat()) {
        78f -> {
            when(typography) {
                Styles.HeaderLarge -> {
                    textStyleBlack30Bold()
                }
                Styles.HeaderMedium -> {
                    textStyleBlack30Medium()
                }
                Styles.HeaderSmall -> {
                    textStyleBlack30Small()
                }
                Styles.TitleLarge -> {
                    textStyleBlack25Bold()
                }
                Styles.TitleMedium -> {
                    textStyleBlack25Medium()
                }
                Styles.TitleSmall -> {
                    textStyleBlack25Small()
                }
                Styles.DisplayLarge -> {
                    textStyleBlack20Bold()
                }
                Styles.DisplayMedium -> {
                    textStyleBlack20Medium()
                }
                Styles.DisplaySmall -> {
                    textStyleBlack20Small()
                }
                Styles.BodyLarge -> {
                    textStyleBlack15Bold()
                }
                Styles.BodyMedium -> {
                    textStyleBlack15Medium()
                }
                Styles.BodySmall -> {
                    textStyleBlack15Small()
                }
                Styles.LabelLarge -> {
                    textStyleBlack14Bold()
                }
                Styles.LabelMedium -> {
                    textStyleBlack14Medium()
                }
                Styles.LabelSmall -> {
                    textStyleBlack14Small()
                }
            }
        } else -> {
            // default pos paper width 58f
            when(typography) {
                Styles.HeaderLarge -> {
                    textStyleBlack20Bold()
                }
                Styles.HeaderMedium -> {
                    textStyleBlack20Medium()
                }
                Styles.HeaderSmall -> {
                    textStyleBlack20Small()
                }
                Styles.TitleLarge -> {
                    textStyleBlack17Bold()
                }
                Styles.TitleMedium -> {
                    textStyleBlack17Medium()
                }
                Styles.TitleSmall -> {
                    textStyleBlack17Small()
                }
                Styles.DisplayLarge -> {
                    textStyleBlack15Bold()
                }
                Styles.DisplayMedium -> {
                    textStyleBlack15Medium()
                }
                Styles.DisplaySmall -> {
                    textStyleBlack15Small()
                }
                Styles.BodyLarge -> {
                    textStyleBlack14Bold()
                }
                Styles.BodyMedium -> {
                    textStyleBlack14Medium()
                }
                Styles.BodySmall -> {
                    textStyleBlack14Small()
                }
                Styles.LabelLarge -> {
                    textStyleBlack13Bold()
                }
                Styles.LabelMedium -> {
                    textStyleBlack13Medium()
                }
                Styles.LabelSmall -> {
                    textStyleBlack13Small()
                }
            }
        }
    }
}

@Composable
fun DashedDivider(
    thickness: Dp,
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    phase: Float = 10f,
    intervals: FloatArray = floatArrayOf(10f, 10f),
    modifier: Modifier
) {
    Canvas(
        modifier = modifier
    ) {
        val dividerHeight = thickness.toPx()
        drawRoundRect(
            color = color, style = Stroke(
                width = dividerHeight, pathEffect = PathEffect.dashPathEffect(
                    intervals = intervals, phase = phase
                )
            )
        )
    }
}

fun calculateWeight(value: Int): Float {
    return value.toFloat() / 10
}