package computer.ethernet.onset.ui.tabs.search.substancerow

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import computer.ethernet.onset.ui.tabs.search.CategoryModel
import computer.ethernet.onset.ui.tabs.search.SubstanceModel
import computer.ethernet.onset.ui.theme.horizontalPadding

@Preview(showBackground = true)
@Composable
fun SubstanceRowPreview(
    @PreviewParameter(SubstanceModelPreviewProvider::class) substanceModel: SubstanceModel
) {
    SubstanceRow(substanceModel = substanceModel, onTap = {})
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SubstanceRow(
    substanceModel: SubstanceModel,
    onTap: (substanceName: String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onTap(substanceModel.name)
            }
            .padding(horizontal = horizontalPadding, vertical = 3.dp),
    ) {
        Text(
            text = substanceModel.name,
            style = MaterialTheme.typography.titleMedium,
        )
        if (substanceModel.commonNames.isNotEmpty()) {
            val commaSeparatedNames = substanceModel.commonNames.joinToString(separator = ", ")
            Text(text = commaSeparatedNames, style = MaterialTheme.typography.bodySmall)
        }
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(3.dp),
            verticalArrangement = Arrangement.spacedBy(3.dp),
            modifier = Modifier.padding(vertical = 3.dp)
        ) {
            substanceModel.categories.forEach {
                CategoryChipStatic(categoryModel = it)
            }
        }
    }
}

@Composable
fun CategoryChipStatic(categoryModel: CategoryModel) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(shape = CircleShape)
            .background(color = categoryModel.color.copy(alpha = 0.2f))
            .padding(vertical = 2.dp, horizontal = 8.dp)

    ) {
        Text(text = categoryModel.name, style = MaterialTheme.typography.bodySmall)
    }
}