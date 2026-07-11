package computer.ethernet.onset.ui.tabs.journal.addingestion.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import computer.ethernet.onset.ui.tabs.search.SubstanceModel
import computer.ethernet.onset.ui.tabs.search.substancerow.SubstanceModelPreviewProvider
import computer.ethernet.onset.ui.theme.horizontalPadding

@Preview(showBackground = true)
@Composable
fun SubstanceRowAddIngestionPreview(
    @PreviewParameter(SubstanceModelPreviewProvider::class) substanceModel: SubstanceModel
) {
    SubstanceRowAddIngestion(substanceModel = substanceModel, onTap = {})
}

@Composable
fun SubstanceRowAddIngestion(
    substanceModel: SubstanceModel,
    onTap: (substanceName: String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onTap(substanceModel.name)
            }
            .padding(horizontal = horizontalPadding, vertical = 8.dp),
    ) {
        Text(
            text = substanceModel.name,
            style = MaterialTheme.typography.titleMedium,
        )
        if (substanceModel.commonNames.isNotEmpty()) {
            val commaSeparatedNames = substanceModel.commonNames.joinToString(separator = ", ")
            Text(text = commaSeparatedNames, style = MaterialTheme.typography.bodySmall)
        }
    }
}