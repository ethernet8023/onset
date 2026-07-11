package computer.ethernet.onset.ui.main.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigation
import computer.ethernet.onset.ui.main.navigation.composableWithTransitions
import computer.ethernet.onset.ui.main.navigation.DrugsTopLevelRoute
import computer.ethernet.onset.ui.tabs.journal.experience.timeline.ExplainTimelineScreen
import computer.ethernet.onset.ui.tabs.safer.DoseExplanationScreen
import computer.ethernet.onset.ui.tabs.safer.VolumetricDosingScreen
import computer.ethernet.onset.ui.tabs.search.SearchScreen
import computer.ethernet.onset.ui.tabs.search.custom.AddCustomSubstanceScreen
import computer.ethernet.onset.ui.tabs.search.custom.EditCustomSubstanceScreen
import computer.ethernet.onset.ui.tabs.search.substance.SubstanceScreen
import computer.ethernet.onset.ui.tabs.search.substance.category.CategoryScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.searchGraph(navController: NavHostController) {
    navigation<DrugsTopLevelRoute>(
        startDestination = DrugsScreenRoute,
    ) {
        composableWithTransitions<DrugsScreenRoute>{
            SearchScreen(
                onSubstanceTap = { substanceModel ->
                    navController.navigate(SubstanceRoute(substanceName = substanceModel.name))
                },
                onCustomSubstanceTap = { customSubstanceId ->
                    navController.navigate(EditCustomSubstanceRoute(customSubstanceId))
                },
                navigateToAddCustomSubstanceScreen = {
                    navController.navigate(AddCustomSubstanceRouteOnSearchGraph)
                }
            )
        }
        composableWithTransitions<SubstanceRoute> {
            SubstanceScreen(
                navigateToDosageExplanationScreen = {
                    navController.navigate(DosageExplanationRouteOnSearchTab)
                },
                navigateToSaferHallucinogensScreen = {
                    navController.navigate(SaferHallucinogensRoute)
                },
                navigateToSaferStimulantsScreen = {
                    navController.navigate(SaferStimulantsRoute)
                },
                navigateToExplainTimeline = {
                    navController.navigate(ExplainTimelineOnSearchTabRoute)
                },
                navigateToCategoryScreen = { categoryName ->
                    navController.navigate(CategoryRoute(categoryName))
                },
                navigateToVolumetricDosingScreen = {
                    navController.navigate(VolumetricDosingOnSearchTabRoute)
                },
            )
        }
        composableWithTransitions<CategoryRoute> {
            CategoryScreen()
        }
        composableWithTransitions<EditCustomSubstanceRoute> {
            EditCustomSubstanceScreen(navigateBack = navController::popBackStack)
        }
        composableWithTransitions<AddCustomSubstanceRouteOnSearchGraph> {
            AddCustomSubstanceScreen(
                navigateBack = navController::popBackStack
            )
        }
        composableWithTransitions<VolumetricDosingOnSearchTabRoute> {
            VolumetricDosingScreen()
        }
        composableWithTransitions<ExplainTimelineOnSearchTabRoute> { ExplainTimelineScreen() }

        composableWithTransitions<DosageExplanationRouteOnSearchTab> { DoseExplanationScreen() }

    }
}

@Serializable
object DrugsScreenRoute

@Serializable
data class SubstanceRoute(val substanceName: String)

@Serializable
data class CategoryRoute(val categoryName: String)

@Serializable
data class EditCustomSubstanceRoute(val customSubstanceId: Int)

@Serializable
object AddCustomSubstanceRouteOnSearchGraph

@Serializable
object VolumetricDosingOnSearchTabRoute

@Serializable
object ExplainTimelineOnSearchTabRoute

@Serializable
object DosageExplanationRouteOnSearchTab