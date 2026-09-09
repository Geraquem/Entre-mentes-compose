package com.mmfsin.betweenminds.presentation.core.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mmfsin.betweenminds.presentation.dashboard.ranking.offline.RankingOfflineScreen
import kotlinx.serialization.Serializable

@Composable
fun NavigationRankingOffline() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = RankingOffline,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable<RankingOffline> { RankingOfflineScreen() }
    }
}

/** SCREENS */
@Serializable
object RankingOffline