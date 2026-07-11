package computer.ethernet.onset.ui.tabs.settings.combinations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CombinationSettingsViewModel @Inject constructor(
    private val comboStorage: CombinationSettingsStorage,
) : ViewModel() {

    val optionsFlow = comboStorage.optionFlow.stateIn(
        initialValue = emptyList(),
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000)
    )

    fun toggleOption(optionName: String) {
        viewModelScope.launch {
            comboStorage.toggleSubstanceInteraction(optionName)
        }
    }
}