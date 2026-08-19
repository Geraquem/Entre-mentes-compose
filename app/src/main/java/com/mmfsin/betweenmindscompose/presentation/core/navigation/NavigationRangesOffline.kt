package com.mmfsin.betweenmindscompose.presentation.core.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mmfsin.betweenmindscompose.presentation.dashboard.ranges.offline.RangesOfflineScreen
import kotlinx.serialization.Serializable

@Composable
fun NavigationRangesOffline() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = RangesOffline,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable<RangesOffline> { RangesOfflineScreen() }
    }
}

/** SCREENS */
@Serializable
object RangesOffline