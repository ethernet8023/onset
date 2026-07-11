package computer.ethernet.onset.ui.tabs.settings.customunits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import computer.ethernet.onset.data.room.experiences.ExperienceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomUnitsViewModel @Inject constructor(
    experienceRepository: ExperienceRepository,
) : ViewModel() {

    private val _searchTextFlow = MutableStateFlow("")
    val searchTextFlow = _searchTextFlow.asStateFlow()

    fun onSearch(searchText: String) = viewModelScope.launch {
        _searchTextFlow.emit(searchText)
    }

    private val customUnitsFlow = experienceRepository.getCustomUnitsFlow(false)

    val filteredCustomUnitsFlow =
        customUnitsFlow.combine(searchTextFlow) { customUnits, searchText ->
            if (searchText.isEmpty()) {
                return@combine customUnits
            }
            return@combine customUnits.filter { custom ->
                custom.name.contains(
                    other = searchText, ignoreCase = true
                ) || custom.substanceName.contains(
                    other = searchText,
                    ignoreCase = true
                ) || custom.unit.contains(
                    other = searchText,
                    ignoreCase = true
                ) || custom.note.contains(other = searchText, ignoreCase = true)
            }
        }.stateIn(
            initialValue = emptyList(),
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000)
        )
}