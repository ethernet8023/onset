package computer.ethernet.onset.ui.tabs.settings.customunits.add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import computer.ethernet.onset.data.substances.AdministrationRoute
import computer.ethernet.onset.ui.tabs.journal.addingestion.route.AdministrationRoutePicker
import computer.ethernet.onset.ui.tabs.journal.addingestion.route.ChooseRouteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseRouteDuringAddCustomUnitScreen(
    onRouteChosen: (administrationRoute: AdministrationRoute) -> Unit,
    viewModel: ChooseRouteViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("${viewModel.substanceName} route") },
                navigationIcon = {
                    if (viewModel.showOtherRoutes && viewModel.pwRoutes.isNotEmpty()) {
                        IconButton(onClick = { viewModel.showOtherRoutes = false }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            AdministrationRoutePicker(
                showOtherRoutes = viewModel.showOtherRoutes,
                onChangeOfShowOtherRoutes = {
                    viewModel.showOtherRoutes = it
                },
                pwRoutes = viewModel.pwRoutes,
                otherRoutesChunked = viewModel.otherRoutesChunked,
                onRouteTapped = {
                    onRouteChosen(it)
                }
            )
        }
    }
}