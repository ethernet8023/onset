package computer.ethernet.onset.ui.tabs.journal.experience.rating

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import computer.ethernet.onset.data.room.experiences.entities.ShulginRatingOption
import computer.ethernet.onset.ui.tabs.journal.experience.components.CardWithTitle

@Preview
@Composable
fun RatingsExplanationSection() {
    CardWithTitle(title = "Longer explanations") {
        val ratings = ShulginRatingOption.entries
        ratings.forEach { oneRating ->
            Column(modifier = Modifier.padding(vertical = 5.dp)) {
                Text(
                    text = oneRating.sign,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = oneRating.longDescription,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
