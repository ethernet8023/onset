package computer.ethernet.onset.ui.tabs.journal.experience.rating

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import computer.ethernet.onset.data.room.experiences.entities.ShulginRatingOption
import computer.ethernet.onset.ui.tabs.journal.experience.components.CardWithTitle

@Composable
@Preview
fun RatingPickerSectionPreview() {
    RatingPickerSection(
        selectedRating = ShulginRatingOption.TWO_PLUS,
        onRatingChange = {}
    )
}

@Composable
fun RatingPickerSection(
    selectedRating: ShulginRatingOption,
    onRatingChange: (ShulginRatingOption) -> Unit
) {
    CardWithTitle(title = "Shulgin rating") {
        val ratings = ShulginRatingOption.entries
        // Modifier.selectableGroup() is essential to ensure correct accessibility behavior
        Column(Modifier.selectableGroup()) {
            ratings.forEach { oneRating ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .selectable(
                            selected = (oneRating == selectedRating),
                            onClick = { onRatingChange(oneRating) },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (oneRating == selectedRating),
                        onClick = null // null recommended for accessibility with screen readers
                    )
                    Text(
                        text = oneRating.sign,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    Text(
                        text = oneRating.shortDescription,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
    }
}