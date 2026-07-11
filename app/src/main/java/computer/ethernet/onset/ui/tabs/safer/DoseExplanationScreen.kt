package computer.ethernet.onset.ui.tabs.safer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import computer.ethernet.onset.data.substances.classes.roa.DoseClass
import computer.ethernet.onset.ui.tabs.search.substance.SectionWithTitle
import computer.ethernet.onset.ui.tabs.search.substance.VerticalSpace
import computer.ethernet.onset.ui.theme.horizontalPadding

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DoseExplanationScreen() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Dosage classification") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
        ) {
            VerticalSpace()
            ElevatedCard(modifier = Modifier.padding(vertical = 5.dp, horizontal = horizontalPadding)) {
                Text(
                    text = "The range and intensity of the effects of a substance depends on upon a number of factors. These include route of administration, dosage, set and setting, and personal and environmental factors.\n" +
                            "Effective doses can be divided into five categories: threshold, light, common, strong, and heavy.",
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .padding(vertical = 10.dp, horizontal = horizontalPadding)
                )
            }
            DoseClass.entries.forEach {
                SectionWithTitle(title = it.name) {
                    Text(
                        text = it.description,
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                            .padding(horizontal = horizontalPadding)
                    )
                }
            }
        }
    }
}