package computer.ethernet.onset.ui.tabs.journal.addingestion.route

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import computer.ethernet.onset.data.substances.AdministrationRoute
import computer.ethernet.onset.data.substances.repositories.SubstanceRepository
import computer.ethernet.onset.ui.main.navigation.graphs.ChooseRouteOfAddIngestionRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChooseRouteViewModel @Inject constructor(
    substanceRepo: SubstanceRepository,
    state: SavedStateHandle
) : ViewModel() {
    val route = state.toRoute<ChooseRouteOfAddIngestionRoute>()
    val substanceName = route.substanceName
    val substance = substanceRepo.getSubstance(substanceName)

    var showOtherRoutes by mutableStateOf(false)
    val pwRoutes = substance?.roas?.map { it.route } ?: emptyList()
    private val otherRoutes = AdministrationRoute.entries.filter { route ->
        !pwRoutes.contains(route)
    }
    val otherRoutesChunked = otherRoutes.chunked(2)

    var isShowingInjectionDialog by mutableStateOf(false)
    var currentRoute by mutableStateOf(AdministrationRoute.INTRAVENOUS)
}