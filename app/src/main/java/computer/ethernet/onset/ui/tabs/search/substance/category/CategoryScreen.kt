package computer.ethernet.onset.ui.tabs.search.substance.category

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import computer.ethernet.onset.data.substances.classes.Category
import computer.ethernet.onset.ui.tabs.stats.EmptyScreenDisclaimer
import computer.ethernet.onset.ui.theme.horizontalPadding

@Composable
fun CategoryScreen(
    viewModel: CategoryViewModel = hiltViewModel()
) {
    CategoryScreen(category = viewModel.category)
}

@Preview
@Composable
fun CategoryPreview() {
    CategoryScreen(
        category = Category(
            name = "psychedelic",
            description = "Psychedelics are drugs which alter the perception, causing a number of mental effects which manifest in many forms including altered states of consciousness, visual or tactile effects.",
            url = "https://psy.st/wiki/Psychedelics",
            color = Color.Red
        ),
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(category: Category?) {
    if (category == null) {
        EmptyScreenDisclaimer(
            title = "Category not found",
            description = "An error happened, please navigate back."
        )
    } else {
        val uriHandler = LocalUriHandler.current
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(category.name.replaceFirstChar { it.uppercase() }) },
                    actions = {
                        if (category.url != null) {
                            TextButton(
                                onClick = { uriHandler.openUri(category.url) },
                            ) {
                                Text("Article")
                            }
                        }
                    }
                )
            },
        ) { padding ->
            Text(
                text = category.description,
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = horizontalPadding, vertical = 10.dp)
            )
        }
    }
}