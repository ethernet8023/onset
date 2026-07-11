package computer.ethernet.onset.ui.tabs.journal.addingestion.saferuse

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import computer.ethernet.onset.ui.tabs.journal.addingestion.NextFAB
import computer.ethernet.onset.ui.tabs.search.substance.BulletPoints
import computer.ethernet.onset.ui.theme.horizontalPadding


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckSaferUseScreen(
    navigateToNext: () -> Unit,
    viewModel: SaferUseViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("${viewModel.substanceName} safer use") }) },
        floatingActionButton = {
            NextFAB(navigateToNext)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(padding)
        ) {
            BulletPoints(
                points = viewModel.substance.saferUse,
                modifier = Modifier.padding(horizontal = horizontalPadding)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}