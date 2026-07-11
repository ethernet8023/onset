package computer.ethernet.onset.ui.tabs.journal.experience.timednote.add

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import computer.ethernet.onset.ui.tabs.journal.experience.timednote.TimedNoteScreenContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTimedNoteScreen(
    viewModel: AddTimedNoteViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add timed note") },
                actions = {
                    IconButton(onClick = {
                        viewModel.onDoneTap()
                        navigateBack()
                    }) {
                        Icon(
                            Icons.Filled.Done,
                            contentDescription = "Done icon"
                        )
                    }
                }
            )
        }
    ) { padding ->
        TimedNoteScreenContent(
            selectedTime = viewModel.localDateTimeFlow.collectAsState().value,
            onTimeChange = viewModel::onChangeTime,
            note = viewModel.note,
            onNoteChange = viewModel::onChangeNote,
            color = viewModel.color,
            onColorChange = viewModel::onChangeColor,
            modifier = Modifier.padding(padding),
            alreadyUsedColors = viewModel.alreadyUsedColors,
            otherColors = viewModel.otherColors,
            isPartOfTimeline = viewModel.isPartOfTimeline,
            onChangeOfIsPartOfTimeline = viewModel::onChangeIsPartOfTimeline,
            shouldFocusTextFieldOnAppear = true
        )
    }
}