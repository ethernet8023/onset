package computer.ethernet.onset.ui.tabs.journal.experience.components.rating

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import computer.ethernet.onset.data.room.experiences.entities.ShulginRating

@Composable
fun TimedRatingRow(
    modifier: Modifier = Modifier,
    ratingSign: String,
    timeText: @Composable () -> Unit
) {
    RatingRow(
        modifier = modifier,
        ratingSign = ratingSign,
        content = timeText
    )
}

@Composable
fun OverallRatingRow(
    modifier: Modifier = Modifier,
    ratingSign: String
) {
    RatingRow(
        modifier = modifier,
        ratingSign = ratingSign
    ) {
        Text(
            text = "Overall rating",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}


@Composable
private fun RatingRow(
    modifier: Modifier = Modifier,
    ratingSign: String,
    content: @Composable () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        content()
        Text(
            text = ratingSign,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview
@Composable
private fun RatingRowPreview(@PreviewParameter(RatingPreviewProvider::class) rating: ShulginRating) {
    RatingRow(
        ratingSign = rating.option.sign,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Sat 7:34")
    }
}