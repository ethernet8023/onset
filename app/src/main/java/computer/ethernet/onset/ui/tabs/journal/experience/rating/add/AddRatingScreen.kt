package computer.ethernet.onset.ui.tabs.journal.experience.rating.add

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
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
fun AddRatingScreenPreview() {
    AddRatingScreen(
        onDone = {},
        selectedTime = LocalDateTime.now(),
        onTimeChange = {},
        selectedRating = ShulginRatingOption.TWO_PLUS,
        onRatingChange = {},
        canAddOverallRating = true,
        isOverallRating = false,
        onChangeIsOverallRating = {}
    )
}

@Composable
fun AddRatingScreen(
    viewModel: AddRatingViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    AddRatingScreen(
        onDone = {
            viewModel.onDoneTap()
            navigateBack()
        },
        selectedTime = viewModel.localDateTimeFlow.collectAsState().value,
        onTimeChange = viewModel::onChangeTime,
        selectedRating = viewModel.selectedRating,
        onRatingChange = viewModel::onChangeRating,
        canAddOverallRating = !viewModel.isThereAlreadyAnOverallRatingFlow.collectAsState().value,
        isOverallRating = viewModel.isThisOverallRating,
        onChangeIsOverallRating = { viewModel.isThisOverallRating = it }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRatingScreen(
    onDone: () -> Unit,
    selectedTime: LocalDateTime,
    onTimeChange: (LocalDateTime) -> Unit,
    selectedRating: ShulginRatingOption,
    onRatingChange: (ShulginRatingOption) -> Unit,
    canAddOverallRating: Boolean,
    isOverallRating: Boolean,
    onChangeIsOverallRating: (Boolean) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Shulgin rating") },
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
            if (canAddOverallRating) {
                ElevatedCard(
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.padding(horizontal = horizontalPadding, vertical = 3.dp)
                    ) {
                        Switch(
                            checked = isOverallRating,
                            onCheckedChange = { onChangeIsOverallRating(it) }
                        )
                        Text(
                            text = "Overall rating",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
            AnimatedVisibility(visible = !isOverallRating) {
                TimePickerSection(selectedTime = selectedTime, onTimeChange = onTimeChange)
            }
            RatingPickerSection(selectedRating = selectedRating, onRatingChange = onRatingChange)
            RatingsExplanationSection()
        }
    }
}