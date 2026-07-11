package computer.ethernet.onset.ui.tabs.journal.addingestion.route

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import computer.ethernet.onset.data.substances.AdministrationRoute

@Preview
@Composable
fun CustomChooseRouteScreenPreview() {
    CustomSubstanceChooseRouteScreen(onRouteTap = {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSubstanceChooseRouteScreen(onRouteTap: (AdministrationRoute) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Route") })
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            LinearProgressIndicator(
                progress = { 0.5f },
                modifier = Modifier.fillMaxWidth(),
            )
            val spacing = 6
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(spacing.dp)
            ) {
                val routesChunked = AdministrationRoute.entries.toList().chunked(2)
                routesChunked.forEach { chunk ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(spacing.dp)
                    ) {
                        chunk.forEach { route ->
                            ElevatedCard(
                                modifier = Modifier
                                    .clickable {
                                        onRouteTap(route)
                                    }
                                    .fillMaxHeight()
                                    .weight(1f)
                            ) {
                                RouteBox(
                                    route = route,
                                    titleStyle = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                        if (chunk.size == 1) {
                            Box(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}