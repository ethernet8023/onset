package computer.ethernet.onset.ui.tabs.journal.experience.components.ingestion

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import computer.ethernet.onset.data.room.experiences.entities.AdaptiveColor
import computer.ethernet.onset.ui.tabs.journal.experience.components.DotRows
import computer.ethernet.onset.ui.tabs.journal.experience.models.IngestionElement

@Preview(showBackground = true)
@Composable
fun IngestionRowPreview(@PreviewParameter(IngestionRowPreviewProvider::class) ingestionElement: IngestionElement) {
    IngestionRow(
        ingestionElement = ingestionElement,
        areDosageDotsHidden = false,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Fri 07:17",
            style = MaterialTheme.typography.labelMedium
        )
    }
}


@Composable
fun IngestionRow(
    ingestionElement: IngestionElement,
    areDosageDotsHidden: Boolean,
    modifier: Modifier = Modifier,
    time: @Composable () -> Unit,
) {
    val ingestionWithCompanionAndCustomUnit = ingestionElement.ingestionWithCompanionAndCustomUnit
    val ingestion = ingestionWithCompanionAndCustomUnit.ingestion
    val customUnit = ingestionWithCompanionAndCustomUnit.customUnit
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier.height(intrinsicSize = IntrinsicSize.Min)
    ) {
        VerticalLine(color = ingestionWithCompanionAndCustomUnit.substanceCompanion?.color ?: AdaptiveColor.RED)
        Column {
            time()
            val title = if (customUnit != null) {
                "${ingestion.substanceName}, ${customUnit.name}"
            } else {
                ingestion.substanceName
            }
            Text(
                modifier = Modifier.weight(1f),
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                val text = buildAnnotatedString {
                    append(ingestionWithCompanionAndCustomUnit.doseDescription)
                    withStyle(style = SpanStyle(color = Color.Gray)) {
                        if (customUnit == null) {
                            append(" " + ingestion.administrationRoute.displayText.lowercase())
                        } else {
                            ingestionWithCompanionAndCustomUnit.customUnitDose?.calculatedDoseDescription?.let {
                                append(" = $it ${ingestion.administrationRoute.displayText.lowercase()}")
                            } ?: run {
                                append(" = unknown dose ${ingestion.administrationRoute.displayText.lowercase()}")
                            }
                        }
                    }
                }
                Text(text = text, style = MaterialTheme.typography.titleSmall)
                val numDots = ingestionElement.numDots
                if (numDots != null && !areDosageDotsHidden) {
                    DotRows(numDots = numDots)
                }
            }
            val note = ingestion.notes
            if (!note.isNullOrBlank()) {
                Text(text = note, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}