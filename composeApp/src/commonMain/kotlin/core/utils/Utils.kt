package core.utils

import androidx.compose.foundation.Canvas
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.formatWithSkeleton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.WindowInfo
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import cafe.adriel.voyager.navigator.Navigator
import core.theme.PrimaryColor
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
import kotlinx.serialization.StringFormat
import org.jetbrains.compose.resources.ExperimentalResourceApi
import poscomposemultiplatform.composeapp.generated.resources.Res
import poscomposemultiplatform.composeapp.generated.resources.ic_cereal
import poscomposemultiplatform.composeapp.generated.resources.ic_coffee
import poscomposemultiplatform.composeapp.generated.resources.ic_dessert
import poscomposemultiplatform.composeapp.generated.resources.ic_juice
import poscomposemultiplatform.composeapp.generated.resources.ic_ramen
import poscomposemultiplatform.composeapp.generated.resources.ic_salads
import poscomposemultiplatform.composeapp.generated.resources.ic_smoothie
import poscomposemultiplatform.composeapp.generated.resources.ic_snack
import poscomposemultiplatform.composeapp.generated.resources.ic_soup
import kotlin.math.round
import kotlin.math.roundToInt

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


val dateFormatList = arrayListOf(
    "8 Digits (YYYY/MM/DD)",
    "8 Digits (DD/MM/YYYY)",
    "6 Digits (YY/MM/DD)",
    "6 Digits (DD/MM/YY)",
)

val countingSequence = arrayListOf(
    "1 Digit (1)",
    "2 Digits (01)",
    "3 Digits (001)",
    "4 Digits (0001)",
    "5 Digits (00001)",
)

val vats = arrayListOf(
    "5",
    "10",
    "15",
    "20",
)

@OptIn(ExperimentalResourceApi::class)
val menuIcons = arrayListOf(
    Res.drawable.ic_coffee,
    Res.drawable.ic_dessert,
    Res.drawable.ic_juice,
    Res.drawable.ic_snack,
    Res.drawable.ic_salads,
    Res.drawable.ic_soup,
    Res.drawable.ic_ramen,
    Res.drawable.ic_cereal,
    Res.drawable.ic_smoothie,
)


fun String?.stringToBoolean(): Boolean = if(this.isNullOrEmpty()) false else this.toBoolean()



infix fun Int.percentOf(value: Double): Double = if (this == 0) 0.0 else (value / 100) * this




object RedRippleTheme: RippleTheme {
    @Composable
    override fun defaultColor() =
        RippleTheme.defaultRippleColor(
            PrimaryColor.copy(alpha = 0.5f),
            lightTheme = true
        )

    @Composable
    override fun rippleAlpha(): RippleAlpha =
        RippleTheme.defaultRippleAlpha(
            PrimaryColor.copy(alpha = 0.5f),
            lightTheme = true
        )
}


data class DottedShape(
    val step: Dp,
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ) = Outline.Generic(Path().apply {
        val stepPx = with(density) { step.toPx() }
        val stepsCount = (size.width / stepPx).roundToInt()
        val actualStep = size.width / stepsCount
        val dotSize = Size(width = actualStep / 2, height = size.height)
        for (i in 0 until stepsCount) {
            addRect(
                Rect(
                    offset = Offset(x = i * actualStep, y = 0f),
                    size = dotSize
                )
            )
        }
        close()
    })
}

fun Double.dollar(): String = if (this == 0.0) "$0.00" else "$${formatDouble(this)}"

/**
 * 12.23123 to 12.23
 * 12.3 to 12.30
 * 1.0 to 1.00
*/
fun formatDouble(value: Double): String {
    val factor = 100.0
    val roundedValue = round(value * factor) / factor
    return roundedValue.toString().let {
        // Ensure it has at least one decimal point
        if (it.indexOf('.') >= 0) {
            // Split into integer and fractional parts
            val parts = it.split('.')
            val integerPart = parts[0]
            val fractionalPart = parts.getOrElse(1) { "0" }

            // Ensure the fractional part has exactly two digits
            val formattedFractionalPart = fractionalPart.padEnd(2, '0').take(2)

            // Combine the integer and formatted fractional parts
            "$integerPart.$formattedFractionalPart"
        } else {
            // No decimal point, add ".00"
            "$it.00"
        }
    }
}


fun formatNumberWithCommas(number: String): String {
    val parts = number.split(".")
    val integerPart = parts[0]
    val reversedNumber = integerPart.reversed()
    val chunks = reversedNumber.chunked(3)
    val joinedChunks = chunks.joinToString(",") { it.reversed() }
    val formattedNumber = joinedChunks.reversed()
    return if (parts.size > 1) {
        "$formattedNumber.${parts[1]}"
    } else {
        formattedNumber
    }
}

@Stable
class CursorVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val out = text.text
        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int = out.length
            override fun transformedToOriginal(offset: Int): Int = out.length
        }
        return TransformedText(AnnotatedString(out), offsetMapping)
    }
}

