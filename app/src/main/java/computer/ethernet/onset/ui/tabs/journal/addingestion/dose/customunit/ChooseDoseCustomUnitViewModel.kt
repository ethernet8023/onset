package computer.ethernet.onset.ui.tabs.journal.addingestion.dose.customunit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import computer.ethernet.onset.data.room.experiences.ExperienceRepository
import computer.ethernet.onset.data.room.experiences.entities.CustomUnit
import computer.ethernet.onset.data.substances.classes.roa.DoseClass
import computer.ethernet.onset.data.substances.classes.roa.RoaDose
import computer.ethernet.onset.data.substances.repositories.SubstanceRepository
import computer.ethernet.onset.ui.main.navigation.graphs.ChooseDoseCustomUnitRoute
import computer.ethernet.onset.ui.tabs.journal.addingestion.search.suggestion.models.CustomUnitDose
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseDoseCustomUnitViewModel @Inject constructor(
    experienceRepo: ExperienceRepository,
    substanceRepo: SubstanceRepository,
    state: SavedStateHandle
) : ViewModel() {

    var customUnit: CustomUnit? by mutableStateOf(null)
    var doseRemark: String? by mutableStateOf(null)
    var roaDose: RoaDose? = null

    init {
        val chooseDoseCustomUnitRoute = state.toRoute<ChooseDoseCustomUnitRoute>()
        viewModelScope.launch {
            val customUnit = experienceRepo.getCustomUnit(chooseDoseCustomUnitRoute.customUnitId)
            this@ChooseDoseCustomUnitViewModel.customUnit = customUnit
            if (customUnit != null) {
                val substance = substanceRepo.getSubstance(customUnit.substanceName)
                doseRemark = substance?.dosageRemark
                roaDose = substance?.getRoa(customUnit.administrationRoute)?.roaDose
            }
        }
    }

    // editable fields
    var doseText by mutableStateOf("")
    var isEstimate by mutableStateOf(false)
    var estimatedDoseDeviationText by mutableStateOf("")

    fun onDoseTextChange(newDoseText: String) {
        doseText = newDoseText
    }

    fun onEstimatedDoseDeviationChange(newEstimatedDeviationText: String) {
        estimatedDoseDeviationText = newEstimatedDeviationText
    }

    val dose: Double? get() = doseText.toDoubleOrNull()
    val estimatedDoseDeviation: Double? get() = estimatedDoseDeviationText.toDoubleOrNull()
    val isValidDose: Boolean get() = dose != null

    private val customUnitDose: CustomUnitDose?
        get() {
            return dose?.let { doseUnwrapped ->
                return@let customUnit?.let {
                    CustomUnitDose(
                        dose = doseUnwrapped,
                        isEstimate = isEstimate,
                        estimatedDoseStandardDeviation = estimatedDoseDeviation,
                        customUnit = it
                    )
                }
            }
        }
    val currentDoseClass: DoseClass? get() = roaDose?.getDoseClass(ingestionDose = customUnitDose?.calculatedDose)
    val customUnitCalculationText: String?
        get() {
            return customUnitDose?.let {
                it.doseDescription + " x " + it.customUnit.getDoseOfOneUnitDescription() + " = " + it.calculatedDoseDescription
            }
        }
}
