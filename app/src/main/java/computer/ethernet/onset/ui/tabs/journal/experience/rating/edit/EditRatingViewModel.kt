package computer.ethernet.onset.ui.tabs.journal.experience.rating.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import computer.ethernet.onset.data.room.experiences.ExperienceRepository
import computer.ethernet.onset.data.room.experiences.entities.ShulginRating
import computer.ethernet.onset.data.room.experiences.entities.ShulginRatingOption
import computer.ethernet.onset.ui.main.navigation.graphs.EditRatingRoute
import computer.ethernet.onset.ui.utils.getInstant
import computer.ethernet.onset.ui.utils.getLocalDateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class EditRatingViewModel @Inject constructor(
    private val experienceRepo: ExperienceRepository,
    state: SavedStateHandle
) : ViewModel() {
    private val ratingId: Int
    var selectedRatingOption by mutableStateOf(ShulginRatingOption.TWO_PLUS)
    var localDateTimeFlow = MutableStateFlow(LocalDateTime.now())
    var rating: ShulginRating? = null
    var isOverallRatingFlow = MutableStateFlow(false)

    init {
        val editRatingRoute = state.toRoute<EditRatingRoute>()
        val ratingId = editRatingRoute.ratingId
        this.ratingId = ratingId
        viewModelScope.launch {
            val loadedRating = experienceRepo.getRating(id = ratingId) ?: return@launch
            rating = loadedRating
            loadedRating.time?.also { time ->
                localDateTimeFlow.emit(time.getLocalDateTime())
            } ?: run {
                isOverallRatingFlow.value = true
            }
            selectedRatingOption = loadedRating.option
        }
    }

    fun onChangeTime(newLocalDateTime: LocalDateTime) {
        viewModelScope.launch {
            localDateTimeFlow.emit(newLocalDateTime)
        }
    }

    fun onChangeRating(newRating: ShulginRatingOption) {
        selectedRatingOption = newRating
    }

    fun delete() {
        viewModelScope.launch {
            rating?.let {
                experienceRepo.delete(it)
            }
        }
    }

    fun onDoneTap() {
        viewModelScope.launch {
            val selectedInstant = localDateTimeFlow.firstOrNull()?.getInstant() ?: return@launch
            rating?.let {
                it.time = if (isOverallRatingFlow.value) null else selectedInstant
                it.option = selectedRatingOption
                experienceRepo.update(it)
            }
        }
    }
}