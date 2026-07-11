package computer.ethernet.onset.ui.tabs.settings.combinations

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import computer.ethernet.onset.ui.theme.horizontalPadding

@Composable
fun CombinationSettingsScreen(viewModel: CombinationSettingsViewModel = hiltViewModel()) {
    CombinationSettingsScreen(
        substanceInteractions = viewModel.optionsFlow.collectAsState().value.map {
            SubstanceInteraction(
                name = it.name,
                isOn = it.enabled,
                toggle = {
                    viewModel.toggleOption(it.name)
                }
            )
        }
    )
}

class SubstanceInteraction(
    val name: String,
    val isOn: Boolean,
    val toggle: () -> Unit,
)

@Preview
@Composable
fun CombinationSettingsPreview() {
    CombinationSettingsScreen(
        substanceInteractions = listOf(
            SubstanceInteraction(name = "Alcohol", toggle = {}, isOn = true),
            SubstanceInteraction(name = "Caffeine", toggle = {}, isOn = false),
            SubstanceInteraction(name = "Cannabis", toggle = {}, isOn = false),
            SubstanceInteraction(name = "Grapefruit", toggle = {}, isOn = true),
            SubstanceInteraction(name = "Hormonal birth control", toggle = {}, isOn = false),
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CombinationSettingsScreen(
    substanceInteractions: List<SubstanceInteraction>
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Combinations") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(padding)
        ) {
            Spacer(modifier = Modifier.height(5.dp))
            ElevatedCard(modifier = Modifier.padding(horizontal = horizontalPadding, vertical = 4.dp)) {
                Column(
                    modifier = Modifier.padding(
                        horizontal = horizontalPadding,
                        vertical = 10.dp
                    )
                ) {
                    Text(
                        text = "Interaction alerts",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Get warnings about interactions with following substances, even if you haven't logged them.",
                        style = MaterialTheme.typography.bodySmall
                    )
                    substanceInteractions.forEach { substanceInteraction ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = substanceInteraction.name)
                            Switch(
                                checked = substanceInteraction.isOn,
                                onCheckedChange = { substanceInteraction.toggle() },
                                modifier = Modifier.semantics { contentDescription = substanceInteraction.name }
                            )
                        }
                    }
                }
            }
        }
    }
}




