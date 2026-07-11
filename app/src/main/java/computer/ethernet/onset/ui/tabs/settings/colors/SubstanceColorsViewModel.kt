package computer.ethernet.onset.ui.tabs.settings.colors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import computer.ethernet.onset.data.room.experiences.ExperienceRepository
import computer.ethernet.onset.data.room.experiences.entities.AdaptiveColor
import computer.ethernet.onset.data.room.experiences.entities.SubstanceCompanion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubstanceColorsViewModel @Inject constructor(
    private val experienceRepository: ExperienceRepository,
) : ViewModel() {

    private val _substanceCompanionsFlow = MutableStateFlow<List<SubstanceCompanion>>(emptyList())
    val substanceCompanionsFlow: StateFlow<List<SubstanceCompanion>> = _substanceCompanionsFlow

    init {
        viewModelScope.launch {
            experienceRepository.getAllSubstanceCompanionsFlow().collect {
                _substanceCompanionsFlow.value = it
            }
        }
    }

    fun deleteUnusedSubstanceCompanions() = viewModelScope.launch {
        experienceRepository.deleteUnusedSubstanceCompanions()
    }

    fun updateColor(color: AdaptiveColor, substanceName: String) {
        viewModelScope.launch {
            val updatedList = _substanceCompanionsFlow.value.map { companion ->
                if (companion.substanceName == substanceName) {
                    companion.copy(color = color).also {
                        experienceRepository.update(it)
                    }
                } else {
                    companion
                }
            }
            _substanceCompanionsFlow.value = updatedList
        }
    }

    val alreadyUsedColorsFlow: StateFlow<List<AdaptiveColor>> =
        _substanceCompanionsFlow.map { companions ->
            companions.map { it.color }.distinct()
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

}