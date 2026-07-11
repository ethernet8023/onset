package computer.ethernet.onset.ui.tabs.settings.customunits.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import computer.ethernet.onset.data.room.experiences.ExperienceRepository
import computer.ethernet.onset.data.room.experiences.entities.CustomUnit
import computer.ethernet.onset.data.substances.classes.Substance
import computer.ethernet.onset.data.substances.classes.roa.DoseClass
import computer.ethernet.onset.data.substances.repositories.SubstanceRepository
import computer.ethernet.onset.ui.main.navigation.graphs.FinishAddCustomUnitRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FinishAddCustomUnitViewModel @Inject constructor(
    private val experienceRepo: ExperienceRepository,
    substanceRepository: SubstanceRepository,
    state: SavedStateHandle
) : ViewModel() {
    private val finishAddCustomUnitRoute = state.toRoute<FinishAddCustomUnitRoute>()
    var substanceName by mutableStateOf("")
    val administrationRoute = finishAddCustomUnitRoute.administrationRoute

    var substance by mutableStateOf<Substance?>(null)
    val roaDose get() = substance?.getRoa(administrationRoute)?.roaDose

    var name by mutableStateOf("")
    var isUnitsFieldShown by mutableStateOf(false)

    val currentDoseClass: DoseClass? get() = roaDose?.getDoseClass(ingestionDose = dose)

    fun onChangeOfName(newName: String) {
        name = newName
    }

    var unit by mutableStateOf("")

    fun onChangeOfUnit(newUnit: String) {
        unit = newUnit
        unitPlural = if (newUnit != "mg" && newUnit != "g" && newUnit.lowercase() != "ml" && newUnit.lastOrNull() != 's') {
            newUnit + "s"
        } else {
            newUnit
        }
    }

    var unitPlural by mutableStateOf("")

    fun onChangeOfUnitPlural(newUnit: String) {
        unitPlural = newUnit
    }

    var originalUnit by mutableStateOf("mg")

    fun onChangeOfOriginalUnit(newUnit: String) {
        originalUnit = newUnit
    }

    var doseText by mutableStateOf("")
    fun onChangeOfDose(newDose: String) {
        doseText = newDose
    }

    val dose: Double? get() = doseText.toDoubleOrNull()

    var estimatedDoseDeviationText by mutableStateOf("")
    fun onChangeOfEstimatedDoseDeviation(newEstimatedDoseDeviation: String) {
        estimatedDoseDeviationText = newEstimatedDoseDeviation
    }

    private val estimatedDoseDeviation: Double? get() = estimatedDoseDeviationText.toDoubleOrNull()

    var isEstimate by mutableStateOf(false)
    fun onChangeOfIsEstimate(newIsEstimate: Boolean) {
        isEstimate = newIsEstimate
    }

    var isArchived by mutableStateOf(false)
    fun onChangeOfIsArchived(newIsArchived: Boolean) {
        isArchived = newIsArchived
    }

    var note by mutableStateOf("")

    fun onChangeOfNote(newNote: String) {
        note = newNote
    }

    init {
        originalUnit = roaDose?.units ?: ""
        viewModelScope.launch {
            substanceName = finishAddCustomUnitRoute.substanceName
            substance = substanceRepository.getSubstance(finishAddCustomUnitRoute.substanceName)
            originalUnit = roaDose?.units ?: "mg"
            isUnitsFieldShown = roaDose?.units?.isBlank() ?: true
            if (substance == null || roaDose?.units == null) {
                val customSubstance =
                    experienceRepo.getCustomSubstance(finishAddCustomUnitRoute.substanceName)
                if (customSubstance != null) {
                    originalUnit = customSubstance.units
                    isUnitsFieldShown = false
                }
            }
        }
    }

    fun createSaveAndDismissAfter(dismiss: (customUnitId: Int) -> Unit) {
        viewModelScope.launch {
            val customUnit = CustomUnit(
                substanceName = substanceName,
                name = name,
                administrationRoute = administrationRoute,
                dose = dose,
                isEstimate = isEstimate,
                estimatedDoseStandardDeviation = if (isEstimate) estimatedDoseDeviation else null,
                isArchived = isArchived,
                unit = unit,
                unitPlural = unitPlural,
                originalUnit = originalUnit,
                note = note
            )
            val customUnitId = experienceRepo.insert(customUnit)
            withContext(Dispatchers.Main) {
                dismiss(customUnitId)
            }
        }
    }
}