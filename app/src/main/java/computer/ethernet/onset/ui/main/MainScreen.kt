package computer.ethernet.onset.ui.main

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import computer.ethernet.onset.ui.main.navigation.graphs.journalGraph
import computer.ethernet.onset.ui.main.navigation.graphs.saferGraph
import computer.ethernet.onset.ui.main.navigation.graphs.searchGraph
import computer.ethernet.onset.ui.main.navigation.graphs.settingsGraph
import computer.ethernet.onset.ui.main.navigation.graphs.statsGraph
import computer.ethernet.onset.ui.main.navigation.JournalTopLevelRoute
import computer.ethernet.onset.ui.main.navigation.topLevelRoutes

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    if (viewModel.isAcceptedFlow.collectAsState().value) {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        NavigationSuiteScaffold(
            navigationSuiteItems = {
                val currentDestination = navBackStackEntry?.destination
                topLevelRoutes.forEach { topLevelRoute ->
                    val selected =
                        currentDestination?.hierarchy?.any { it.hasRoute(topLevelRoute.route::class) } == true
                    item(
                        icon = {
                            Icon(
                                if (selected) topLevelRoute.filledIcon else topLevelRoute.outlinedIcon,
                                contentDescription = topLevelRoute.name
                            )
                        },
                        label = { Text(topLevelRoute.name) },
                        selected = selected,
                        onClick = {
                            if (selected) {
                                val isAlreadyOnTopOfTab =
                                    topLevelRoutes.any { it.route == currentDestination?.route }
                                if (!isAlreadyOnTopOfTab) {
                                    navController.popBackStack()
                                }
                            } else {
                                navController.navigate(topLevelRoute.route) {
                                    // Pop up to the start destination of the graph to
                                    // avoid building up a large stack of destinations
                                    // on the back stack as users select items
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    // Avoid multiple copies of the same destination when
                                    // reselecting the same item
                                    launchSingleTop = true
                                    // Restore state when reselecting a previously selected item
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        ) {
            NavHost(
                navController,
                startDestination = JournalTopLevelRoute
            ) {
                journalGraph(navController)
                statsGraph(navController)
                searchGraph(navController)
                saferGraph(navController)
                settingsGraph(navController)
            }
        }
    } else {
        AcceptConditionsScreen(onTapAccept = viewModel::accept)
    }
}