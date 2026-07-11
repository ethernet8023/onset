package computer.ethernet.onset.ui.tabs.journal.addingestion.dose

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun OptionalDosageUnitDisclaimer(substanceName: String) {
    when (substanceName) {
        "Alcohol" -> {
            Text(text = "The dosages are given in mg of pure Ethanol.")
        }
    }
}