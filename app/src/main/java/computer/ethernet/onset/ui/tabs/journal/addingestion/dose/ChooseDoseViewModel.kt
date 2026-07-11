package computer.ethernet.onset.ui.tabs.journal.addingestion.dose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import computer.ethernet.onset.data.substances.AdministrationRoute
import computer.ethernet.onset.data.substances.classes.Substance
import computer.ethernet.onset.data.substances.classes.roa.DoseClass
import computer.ethernet.onset.data.substances.classes.roa.RoaDose
import computer.ethernet.onset.data.substances.repositories.SubstanceRepository
import computer.ethernet.onset.ui.main.navigation.graphs.ChooseDoseRoute
import computer.ethernet.onset.ui.tabs.search.substance.roa.toReadableString
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChooseDoseViewModel @Inject constructor(
    repository: SubstanceRepository,
    state: SavedStateHandle,
) : ViewModel() {
    private val chooseDoseRoute = state.toRoute<ChooseDoseRoute>()
    val substance: Substance = repository.getSubstance(chooseDoseRoute.substanceName)!!
    val administrationRoute: AdministrationRoute = chooseDoseRoute.administrationRoute
    val roaDose: RoaDose? = substance.getRoa(administrationRoute)?.roaDose
    var isEstimate by mutableStateOf(false)
    var doseText by mutableStateOf("")
    var estimatedDoseStandardDeviationText by mutableStateOf("")
    var purityText by mutableStateOf("100")
    var units by mutableStateOf("")
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
                    val result = it.div(safePurity).times(100)
                    return result.toReadableString() + " impure ${roaDose?.units ?: ""}"
                }
            }
        }
    val dose: Double? get() = doseText.toDoubleOrNull()
    val estimatedDoseStandardDeviation: Double? get() = estimatedDoseStandardDeviationText.toDoubleOrNull()
    val isValidDose: Boolean get() = dose != null
    val currentDoseClass: DoseClass? get() = roaDose?.getDoseClass(ingestionDose = dose)

    fun onDoseTextChange(newDoseText: String) {
        doseText = newDoseText
    }

    fun onEstimatedDoseStandardDeviationChange(newEstimatedStandardDeviationText: String) {
        estimatedDoseStandardDeviationText = newEstimatedStandardDeviationText
    }

    init {
        units = roaDose?.units ?: ""
    }

}
