package computer.ethernet.onset.ui.tabs.journal.experience.timednote.add

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
import computer.ethernet.onset.ui.main.navigation.graphs.AddTimedNoteRoute
import computer.ethernet.onset.ui.utils.getInstant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class AddTimedNoteViewModel @Inject constructor(
    private val experienceRepo: ExperienceRepository,
    state: SavedStateHandle
) : ViewModel() {
    var note by mutableStateOf("")
    var color by mutableStateOf(AdaptiveColor.BLUE)
    var isPartOfTimeline by mutableStateOf(true)
    val experienceId = state.toRoute<AddTimedNoteRoute>().experienceId
    var localDateTimeFlow = MutableStateFlow(LocalDateTime.now())
    var alreadyUsedColors by mutableStateOf(emptyList<AdaptiveColor>())
    var otherColors by mutableStateOf(emptyList<AdaptiveColor>())

    init {
        viewModelScope.launch {
            val ingestionsWithCompanions = experienceRepo.getIngestionsWithCompanions(experienceId)
            val substanceColors = ingestionsWithCompanions.mapNotNull { it.substanceCompanion?.color }.distinct()
            val notes = experienceRepo.getTimedNotes(experienceId)
            val noteColors = notes.map { it.color }.distinct()
            alreadyUsedColors = (substanceColors + noteColors).distinct()
            otherColors = AdaptiveColor.entries.filter {
                !alreadyUsedColors.contains(it)
            }
            color = otherColors.randomOrNull() ?: AdaptiveColor.BLUE
            val lastIngestionTime = ingestionsWithCompanions.maxOfOrNull { it.ingestion.time }
            if (lastIngestionTime != null) {
                if (Duration.between(lastIngestionTime, Instant.now()).toHours() > 12) {
                    isPartOfTimeline = false
                }
            }
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

    fun onDoneTap() {
        if (note.isNotBlank()) {
            viewModelScope.launch {
                val newTimedNote = TimedNote(
                    time = localDateTimeFlow.firstOrNull()?.getInstant() ?: Instant.now(),
                    creationDate = Instant.now(),
                    note = note,
                    color = color,
                    experienceId = experienceId,
                    isPartOfTimeline = isPartOfTimeline
                )
                experienceRepo.insert(newTimedNote)
            }
        }
    }
}