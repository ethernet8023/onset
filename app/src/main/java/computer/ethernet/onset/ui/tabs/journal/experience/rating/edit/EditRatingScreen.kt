package computer.ethernet.onset.ui.tabs.journal.experience.rating.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import computer.ethernet.onset.data.room.experiences.entities.ShulginRatingOption
import computer.ethernet.onset.ui.tabs.journal.experience.rating.FloatingDoneButton
import computer.ethernet.onset.ui.tabs.journal.experience.rating.RatingPickerSection
import computer.ethernet.onset.ui.tabs.journal.experience.rating.RatingsExplanationSection
import computer.ethernet.onset.ui.tabs.journal.experience.rating.TimePickerSection
import computer.ethernet.onset.ui.theme.horizontalPadding
import java.time.LocalDateTime

@Preview
@Composable
fun EditRatingScreenPreview() {
    EditRatingScreen(
        onDone = {},
        selectedTime = LocalDateTime.now(),
        onTimeChange = {},
        selectedRating = ShulginRatingOption.TWO_PLUS,
        onRatingChange = {},
        onDelete = {},
        isOverallRating = false
    )
}

@Composable
fun EditRatingScreen(
    viewModel: EditRatingViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    EditRatingScreen(
        onDone = {
            viewModel.onDoneTap()
            navigateBack()
        },
        selectedTime = viewModel.localDateTimeFlow.collectAsState().value,
        onTimeChange = viewModel::onChangeTime,
        selectedRating = viewModel.selectedRatingOption,
        onRatingChange = viewModel::onChangeRating,
        onDelete = {
            viewModel.delete()
            navigateBack()
        },
        isOverallRating = viewModel.isOverallRatingFlow.collectAsState().value
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditRatingScreen(
    onDone: () -> Unit,
    selectedTime: LocalDateTime,
    onTimeChange: (LocalDateTime) -> Unit,
    selectedRating: ShulginRatingOption,
    onRatingChange: (ShulginRatingOption) -> Unit,
    onDelete: () -> Unit,
    isOverallRating: Boolean
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Shulgin rating") },
                actions = {
                    IconButton(
                        onClick = { onDelete() },
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete rating",
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingDoneButton(
                onDone = onDone,
                modifier = Modifier.imePadding(),
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(horizontal = horizontalPadding)
        ) {
            Spacer(modifier = Modifier.height(3.dp))
            if (!isOverallRating) {
                TimePickerSection(selectedTime = selectedTime, onTimeChange = onTimeChange)
            }
            RatingPickerSection(selectedRating = selectedRating, onRatingChange = onRatingChange)
            RatingsExplanationSection()
        }
    }
}