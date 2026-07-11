package computer.ethernet.onset.ui.tabs.search.substance.roa.dose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import computer.ethernet.onset.data.substances.classes.roa.DoseClass
import computer.ethernet.onset.ui.tabs.search.substance.roa.toReadableString


@Preview
@Composable
fun DoseClassificationPreview() {
    DoseClassificationRow(
        lightMin = 20.0,
        commonMin = 50.0,
        strongMin = 90.0,
        heavyMin = 120.0,
        unit = "mg"
    )
}
@Composable
fun DoseClassificationRow(
    lightMin: Double?,
    commonMin: Double?,
    strongMin: Double?,
    heavyMin: Double?,
    unit: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val isDarkTheme = isSystemInDarkTheme()
        val threshColor = DoseClass.THRESHOLD.getComposeColor(isDarkTheme)
        val lightColor = DoseClass.LIGHT.getComposeColor(isDarkTheme)
        val commonColor = DoseClass.COMMON.getComposeColor(isDarkTheme)
        val strongColor = DoseClass.STRONG.getComposeColor(isDarkTheme)
        val heavyColor = DoseClass.HEAVY.getComposeColor(isDarkTheme)
        val labelStyle = MaterialTheme.typography.labelSmall
        val numberStyle = MaterialTheme.typography.labelLarge
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.textBrush(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        threshColor, lightColor
                    )
                )
            )
        ) {
            Text(text = lightMin?.toReadableString() ?: "..", style = numberStyle)
            Text("thresh  ", style = labelStyle)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("-", color = lightColor)
            Text("light", style = labelStyle, color = lightColor)
        }
        Text(
            text = commonMin?.toReadableString() ?: "..",
            style = numberStyle,
            modifier = Modifier.textBrush(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        lightColor, commonColor
                    )
                )
            )
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("-", color = commonColor, style = numberStyle)
            Text("common", style = labelStyle, color = commonColor)
        }
        Text(
            text = strongMin?.toReadableString() ?: "..",
            style = numberStyle,
            modifier = Modifier.textBrush(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        commonColor, strongColor
                    )
                )
            )
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("-", color = strongColor, style = numberStyle)
            Text("strong", style = labelStyle, color = strongColor)
        }
        Text(
            text = heavyMin?.toReadableString() ?: "..",
            style = numberStyle,
            modifier = Modifier.textBrush(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        strongColor, heavyColor
                    )
                )
            )
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("-", color = heavyColor, style = numberStyle)
            Text("heavy", style = labelStyle, color = heavyColor)
        }
        Text(text = unit, style = numberStyle)
    }
}