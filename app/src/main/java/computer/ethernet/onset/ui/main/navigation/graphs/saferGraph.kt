package computer.ethernet.onset.ui.main.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigation
import computer.ethernet.onset.ui.main.navigation.composableWithTransitions
import computer.ethernet.onset.ui.main.navigation.SaferUseTopLevelRoute
import computer.ethernet.onset.ui.tabs.safer.DoseExplanationScreen
import computer.ethernet.onset.ui.tabs.safer.DoseGuideScreen
import computer.ethernet.onset.ui.tabs.safer.DrugTestingScreen
import computer.ethernet.onset.ui.tabs.safer.ReagentTestingScreen
import computer.ethernet.onset.ui.tabs.safer.RouteExplanationScreen
import computer.ethernet.onset.ui.tabs.safer.SaferHallucinogensScreen
import computer.ethernet.onset.ui.tabs.safer.SaferUseScreen
import computer.ethernet.onset.ui.tabs.safer.VolumetricDosingScreen
import computer.ethernet.onset.ui.tabs.search.substance.SaferStimulantsScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.saferGraph(navController: NavHostController) {
    navigation<SaferUseTopLevelRoute>(
        startDestination = SaferUseScreenRoute
    ) {
        composableWithTransitions<SaferUseScreenRoute> {
            SaferUseScreen(
                navigateToDrugTestingScreen = {
                    navController.navigate(DrugTestingRoute)
                },
                navigateToSaferHallucinogensScreen = {
                    navController.navigate(SaferHallucinogensRoute)
                },
                navigateToVolumetricDosingScreen = {
                    navController.navigate(VolumetricDosingOnSaferTabRoute)
                },
                navigateToDosageGuideScreen = {
                    navController.navigate(DosageGuideRoute)
                },
                navigateToDosageClassificationScreen = {
                    navController.navigate(DosageExplanationRouteOnSaferTab)
                },
                navigateToRouteExplanationScreen = {
                    navController.navigate(AdministrationRouteExplanationRouteOnSaferTab)
                },
                navigateToReagentTestingScreen = {
                    navController.navigate(ReagentTestingRoute)
                }
            )
        }
        composableWithTransitions<SaferHallucinogensRoute> { SaferHallucinogensScreen() }
        composableWithTransitions<SaferStimulantsRoute> { SaferStimulantsScreen() }
        composableWithTransitions<DosageExplanationRouteOnSaferTab> { DoseExplanationScreen() }
        composableWithTransitions<AdministrationRouteExplanationRouteOnSaferTab> {
            RouteExplanationScreen()
        }
        composableWithTransitions<DrugTestingRoute> { DrugTestingScreen() }
        composableWithTransitions<DosageGuideRoute> {
            DoseGuideScreen(
                navigateToDoseClassification = {
                    navController.navigate(DosageExplanationRouteOnSaferTab)
                },
                navigateToVolumetricDosing = {
                    navController.navigate(VolumetricDosingOnSaferTabRoute)
                },
            )
        }
        composableWithTransitions<VolumetricDosingOnSaferTabRoute> {
            VolumetricDosingScreen()
        }
        composableWithTransitions<ReagentTestingRoute> {
            ReagentTestingScreen()
        }
    }
}


@Serializable
object SaferUseScreenRoute

@Serializable
object SaferHallucinogensRoute

@Serializable
object SaferStimulantsRoute

@Serializable
object DosageExplanationRouteOnSaferTab

@Serializable
object ReagentTestingRoute

@Serializable
object DrugTestingRoute

@Serializable
object AdministrationRouteExplanationRouteOnSaferTab

@Serializable
object DosageGuideRoute

@Serializable
object VolumetricDosingOnSaferTabRoute
