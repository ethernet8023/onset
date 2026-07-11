package computer.ethernet.onset.ui.main.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigation
import computer.ethernet.onset.ui.main.navigation.composableWithTransitions
import computer.ethernet.onset.ui.main.navigation.StatsTopLevelRoute
import computer.ethernet.onset.ui.tabs.stats.StatsScreen
import computer.ethernet.onset.ui.tabs.stats.substancecompanion.SubstanceCompanionScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.statsGraph(navController: NavHostController) {
    navigation<StatsTopLevelRoute>(
        startDestination = StatsScreenRoute,
    ) {
        composableWithTransitions<StatsScreenRoute> {
            StatsScreen(
                navigateToSubstanceCompanion = { substanceName, consumerName ->
                    navController.navigate(
                        SubstanceCompanionRoute(
                            substanceName = substanceName,
                            consumerName = consumerName
                        )
                    )
                },
            )
        }
        composableWithTransitions<SubstanceCompanionRoute> {
            SubstanceCompanionScreen()
        }
    }
}

@Serializable
object StatsScreenRoute

@Serializable
data class SubstanceCompanionRoute(val substanceName: String, val consumerName: String?)