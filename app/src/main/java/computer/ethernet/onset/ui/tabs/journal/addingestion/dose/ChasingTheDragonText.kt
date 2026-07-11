package computer.ethernet.onset.ui.tabs.journal.addingestion.dose

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun ChasingTheDragonText(
    modifier: Modifier = Modifier,
    titleStyle: TextStyle = MaterialTheme.typography.titleMedium,
) {
    Column(
        modifier = modifier
    ) {
        Text(text = "Chasing the dragon (foiling)", style = titleStyle)
        Text(text = """
            This is likely the least clinically delivered route of administration. An overdose caused by chasing the dragon is hard to predict because this technique doesn't deliver a standardized dosage. It's virtually impossible even for skilled users to know how much of the substance that has been evaporated, burned, and inhaled.
            These combined factors may create a false sense of security when a given dose seems safe to repeat, but may cause an overdose when all the factors are randomly excluded.
                    """.trimIndent())
    }
}

