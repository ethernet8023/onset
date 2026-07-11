package computer.ethernet.onset.ui.tabs.settings.customunits.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import computer.ethernet.onset.data.room.experiences.ExperienceRepository
import computer.ethernet.onset.data.room.experiences.entities.CustomUnit
import computer.ethernet.onset.data.substances.AdministrationRoute
import computer.ethernet.onset.data.substances.classes.roa.DoseClass
import computer.ethernet.onset.data.substances.classes.roa.RoaDose
import computer.ethernet.onset.data.substances.repositories.SubstanceRepository
import computer.ethernet.onset.ui.main.navigation.graphs.EditCustomUnitRoute
import computer.ethernet.onset.ui.tabs.search.substance.roa.toReadableString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EditCustomUnitViewModel @Inject constructor(
    private val experienceRepo: ExperienceRepository,
    substanceRepository: SubstanceRepository,
    state: SavedStateHandle
) : ViewModel() {

    private var customUnit: CustomUnit? = null

    var roaDose: RoaDose? = null

    var name by mutableStateOf("")

    var numberOfIngestionsWithThisCustomUnit: Int? by mutableStateOf(null)

    var substanceName by mutableStateOf("")
    var administrationRoute by mutableStateOf(AdministrationRoute.ORAL)

    val currentDoseClass: DoseClass? get() = roaDose?.getDoseClass(ingestionDose = dose)

    fun onChangeOfName(newName: String) {
        name = newName
    }

    var unit by mutableStateOf("")

    fun onChangeOfUnit(newUnit: String) {
        unit = newUnit
    }

    var unitPlural by mutableStateOf("")

    fun onChangeOfUnitPlural(newUnit: String) {
        unitPlural = newUnit
    }

    var originalUnit by mutableStateOf("")

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
    val estimatedDoseStandardDeviation: Double? get() = estimatedDoseDeviationText.toDoubleOrNull()


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

    fun updateAndDismissAfter(dismiss: () -> Unit) {
        viewModelScope.launch {
            customUnit?.let {
                it.dose = dose
                it.name = name
                it.isEstimate = isEstimate
                it.originalUnit = originalUnit
                it.unitPlural = unitPlural
                it.estimatedDoseStandardDeviation = if (isEstimate) estimatedDoseStandardDeviation else null
                it.isArchived = isArchived
                it.unit = unit
                it.note = note
                experienceRepo.update(it)
            }
            withContext(Dispatchers.Main) {
                dismiss()
            }
        }
    }

    fun deleteCustomUnit(dismiss: () -> Unit) {
        viewModelScope.launch {
            customUnit?.let {
                experienceRepo.delete(customUnit = it)
            }
            withContext(Dispatchers.Main) {
                dismiss()
            }
        }
    }

    init {
        val customUnitRoute: EditCustomUnitRoute = state.toRoute()
        val customUnitId = customUnitRoute.customUnitId
        viewModelScope.launch {
            val customUnitWithIngestions = experienceRepo.getCustomUnitWithIngestions(customUnitId)
            val customUnit = customUnitWithIngestions?.customUnit
            this@EditCustomUnitViewModel.customUnit = customUnit
            if (customUnit != null) {
                substanceName = customUnit.substanceName
                administrationRoute = customUnit.administrationRoute
                val substance = substanceRepository.getSubstance(customUnit.substanceName)
                roaDose = substance?.getRoa(customUnit.administrationRoute)?.roaDose
                originalUnit = customUnit.originalUnit
                name = customUnit.name
                val pluralizableUnit = customUnit.getPluralizableUnit()
                unit = customUnit.unit
                unitPlural = pluralizableUnit.plural
                doseText = customUnit.dose?.toReadableString() ?: ""
                estimatedDoseDeviationText = customUnit.estimatedDoseStandardDeviation?.toReadableString() ?: ""
                isEstimate = customUnit.isEstimate
                isArchived = customUnit.isArchived
                note = customUnit.note
            }
            numberOfIngestionsWithThisCustomUnit = customUnitWithIngestions?.ingestions?.size
        }
    }
}