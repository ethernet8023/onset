package computer.ethernet.onset.ui.tabs.journal.addingestion.dose.customsubstance

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import computer.ethernet.onset.data.room.experiences.ExperienceRepository
import computer.ethernet.onset.data.substances.AdministrationRoute
import computer.ethernet.onset.ui.main.navigation.graphs.ChooseCustomSubstanceDoseRoute
import computer.ethernet.onset.ui.tabs.search.substance.roa.toReadableString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CustomSubstanceChooseDoseViewModel @Inject constructor(
    experienceRepository: ExperienceRepository,
    state: SavedStateHandle,
) : ViewModel() {
    var substanceName by mutableStateOf("")
    val administrationRoute: AdministrationRoute
    var units by mutableStateOf("mg")
    var isEstimate by mutableStateOf(false)
    var doseText by mutableStateOf("")
    var estimatedDoseDeviationText by mutableStateOf("")
    var purityText by mutableStateOf("100")
    private val purity: Double?
        get() {
            val p = purityText.toDoubleOrNull()
            return if (p != null && p > 0 && p <= 100) {
                p
            } else {
                null
            }
        }
    val isPurityValid: Boolean get() = purity != null
    val impureDoseWithUnit: String?
        get() {
            dose.let {
                if (it == null) return null
                purity.let { safePurity ->
                    if (safePurity == null) return null
                    val value = it.div(safePurity).times(100)
                    return value.toReadableString() + " impure $units"
                }
            }
        }
    val dose: Double? get() = doseText.toDoubleOrNull()
    val estimatedDoseStandardDeviation: Double? get() = estimatedDoseDeviationText.toDoubleOrNull()
    val isValidDose: Boolean get() = dose != null

    fun onDoseTextChange(newDoseText: String) {
        doseText = newDoseText
    }

    fun onEstimatedDoseStandardDeviationTextChange(newDeviationText: String) {
        estimatedDoseDeviationText = newDeviationText
    }

    init {
        val chooseCustomSubstanceDoseRoute = state.toRoute<ChooseCustomSubstanceDoseRoute>()
        administrationRoute = chooseCustomSubstanceDoseRoute.administrationRoute
        viewModelScope.launch {
            val customSubstance =
                experienceRepository.getCustomSubstance(chooseCustomSubstanceDoseRoute.customSubstanceName)
                    ?: return@launch
            substanceName = customSubstance.name
            units = customSubstance.units
        }
    }

}
