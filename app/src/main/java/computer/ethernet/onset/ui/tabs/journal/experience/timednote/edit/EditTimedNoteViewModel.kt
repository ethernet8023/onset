package computer.ethernet.onset.ui.tabs.journal.experience.timednote.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import computer.ethernet.onset.data.room.experiences.ExperienceRepository
import computer.ethernet.onset.data.room.experiences.entities.AdaptiveColor
import computer.ethernet.onset.data.room.experiences.entities.TimedNote
import computer.ethernet.onset.ui.main.navigation.graphs.EditTimedNoteRoute
import computer.ethernet.onset.ui.utils.getInstant
import computer.ethernet.onset.ui.utils.getLocalDateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class EditTimedNoteViewModel @Inject constructor(
    private val experienceRepo: ExperienceRepository,
    state: SavedStateHandle
) : ViewModel() {
    var note by mutableStateOf("")
    var color by mutableStateOf(AdaptiveColor.BLUE)
    var isPartOfTimeline by mutableStateOf(true)
    var localDateTimeFlow = MutableStateFlow(LocalDateTime.now())
    private var timedNote: TimedNote? = null
    private val editTimedNoteRoute = state.toRoute<EditTimedNoteRoute>()
    val experienceId = editTimedNoteRoute.experienceId

    private val timedNoteId: Int

    init {
        val timedNoteId = editTimedNoteRoute.timedNoteId
        this.timedNoteId = timedNoteId
        viewModelScope.launch {
            val loadedNote = experienceRepo.getTimedNote(id = timedNoteId) ?: return@launch
            timedNote = loadedNote
            loadedNote.time.let { time ->
                localDateTimeFlow.emit(time.getLocalDateTime())
            }
            note = loadedNote.note
            color = loadedNote.color
            isPartOfTimeline = loadedNote.isPartOfTimeline
        }
    }


    fun onChangeTime(newLocalDateTime: LocalDateTime) {
        viewModelScope.launch {
            localDateTimeFlow.emit(newLocalDateTime)
        }
    }

    fun onChangeIsPartOfTimeline(newIsPartOfTimeline: Boolean) {
        isPartOfTimeline = newIsPartOfTimeline
    }

    fun onChangeNote(newNote: String) {
        note = newNote
    }

    fun onChangeColor(newColor: AdaptiveColor) {
        color = newColor
    }

    private val ingestionsFlow = experienceRepo.getIngestionsWithCompanionsFlow(experienceId)
    private val timedNotesFlow = experienceRepo.getTimedNotesFlowSorted(experienceId)

    val alreadyUsedColorsFlow: StateFlow<List<AdaptiveColor>> =
        ingestionsFlow.combine(timedNotesFlow) { ingestions, notes ->
            val companionColors = ingestions.mapNotNull { it.substanceCompanion?.color }
            val noteColors = notes.map { it.color }
            return@combine (companionColors + noteColors).distinct()
        }.stateIn(
            initialValue = emptyList(),
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000)
        )

    val otherColorsFlow: StateFlow<List<AdaptiveColor>> =
        alreadyUsedColorsFlow.map { alreadyUsedColors ->
            AdaptiveColor.entries.filter {
                !alreadyUsedColors.contains(it)
            }
        }.stateIn(
            initialValue = emptyList(),
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000)
        )

    fun delete() {
        viewModelScope.launch {
            timedNote?.let {
                experienceRepo.delete(it)
            }
        }
    }

    fun onDoneTap() {
        viewModelScope.launch {
            val selectedInstant = localDateTimeFlow.firstOrNull()?.getInstant() ?: return@launch
            timedNote?.let {
                it.time = selectedInstant
                it.note = note
                it.color = color
                it.isPartOfTimeline = isPartOfTimeline
                experienceRepo.update(it)
            }
        }
    }
}