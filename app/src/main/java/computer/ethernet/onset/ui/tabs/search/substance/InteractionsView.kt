package computer.ethernet.onset.ui.tabs.search.substance

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.OpenInBrowser
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import computer.ethernet.onset.data.substances.classes.InteractionType
import computer.ethernet.onset.data.substances.classes.Interactions
import computer.ethernet.onset.ui.theme.horizontalPadding
import computer.ethernet.onset.ui.utils.getInteractionExplanationURLForSubstance

@Preview
@Composable
fun InteractionsPreview(@PreviewParameter(InteractionsPreviewProvider::class) interactions: Interactions) {
    InteractionsView(interactions, substanceURL = "")
}

@Composable
fun InteractionsView(
    interactions: Interactions,
    substanceURL: String
) {
    Column {
        if (interactions.dangerous.isNotEmpty()) {
            interactions.dangerous.forEach {
                InteractionRowSubstanceScreen(
                    text = it,
                    interactionType = InteractionType.DANGEROUS
                )
            }
        }
        if (interactions.unsafe.isNotEmpty()) {
            interactions.unsafe.forEach {
                InteractionRowSubstanceScreen(text = it, interactionType = InteractionType.UNSAFE)
            }
        }
        if (interactions.uncertain.isNotEmpty()) {
            interactions.uncertain.forEach {
                InteractionRowSubstanceScreen(
                    text = it,
                    interactionType = InteractionType.UNCERTAIN
                )
            }
        }
        InteractionExplanationButton(substanceURL = substanceURL)
    }
}

@Composable
fun InteractionExplanationButton(substanceURL: String) {
    val uriHandler = LocalUriHandler.current
    TextButton(onClick = {
        val interactionURL = getInteractionExplanationURLForSubstance(substanceURL)
        uriHandler.openUri(interactionURL)
    }) {
        Icon(
            Icons.Outlined.OpenInBrowser,
            contentDescription = "Open link"
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text("Explanations")
    }
}

@Composable
fun InteractionRowSubstanceScreen(
    text: String,
    interactionType: InteractionType,
    verticalPaddingInside: Dp = 2.dp
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RectangleShape,
        color = interactionType.color
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = horizontalPadding,
                vertical = verticalPaddingInside
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                color = Color.Black
            )
            Spacer(modifier = Modifier.weight(1f))
            LazyRow {
                items(interactionType.dangerCount) {
                    Icon(
                        imageVector = Icons.Outlined.WarningAmber,
                        contentDescription = "Warning",
                        tint = Color.Black,
                        modifier = Modifier.size(17.dp)
                    )
                }
            }
        }
    }
}