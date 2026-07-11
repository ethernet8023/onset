package computer.ethernet.onset.ui.main.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

inline fun <reified T : Any> NavGraphBuilder.composableWithTransitions(
    noinline content: @Composable (AnimatedVisibilityScope.(NavBackStackEntry) -> Unit)
) {
    val withinTabTransitionTimeInMs = 300
    val tabSwitchTimeInMs = 200
    composable<T>(
        exitTransition = {
            if (isChangingTab()) {
                fadeOut(animationSpec = tween(tabSwitchTimeInMs))
            } else {
                slideOutHorizontally(
                    targetOffsetX = { -300 },
                    animationSpec = tween(withinTabTransitionTimeInMs)
                ) + fadeOut(animationSpec = tween(withinTabTransitionTimeInMs))
            }
        },
        popEnterTransition = {
            if (isChangingTab()) {
                fadeIn(animationSpec = tween(tabSwitchTimeInMs))
            } else {
                slideInHorizontally(
                    initialOffsetX = { -300 },
                    animationSpec = tween(withinTabTransitionTimeInMs)
                ) + fadeIn(animationSpec = tween(withinTabTransitionTimeInMs))
            }
        },
        enterTransition = {
            if (isChangingTab()) {
                fadeIn(animationSpec = tween(tabSwitchTimeInMs))
            } else {
                slideInHorizontally(
                    initialOffsetX = { 300 },
                    animationSpec = tween(withinTabTransitionTimeInMs)
                ) + fadeIn(animationSpec = tween(withinTabTransitionTimeInMs))
            }
        },
        popExitTransition = {
            if (isChangingTab()) {
                fadeOut(animationSpec = tween(tabSwitchTimeInMs))
            } else {
                slideOutHorizontally(
                    targetOffsetX = { 300 },
                    animationSpec = tween(withinTabTransitionTimeInMs)
                ) + fadeOut(animationSpec = tween(withinTabTransitionTimeInMs))
            }
        },
        content = content
    )
}

fun AnimatedContentTransitionScope<NavBackStackEntry>.isChangingTab(): Boolean {
    val initialTopLevel = initialState.destination.hierarchy.firstOrNull { navDestination ->
        topLevelRoutes.any { navDestination.hasRoute(it.route::class) } }
    val targetTopLevel = targetState.destination.hierarchy.firstOrNull { navDestination ->
        topLevelRoutes.any { navDestination.hasRoute(it.route::class) } }
    return initialTopLevel != targetTopLevel
}
