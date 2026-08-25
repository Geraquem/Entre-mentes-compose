package com.mmfsin.betweenminds.presentation.core.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mmfsin.betweenminds.presentation.dashboard.ranges.online.RangesOnlineScreen
import kotlinx.serialization.Serializable

@Composable
fun NavigationRangesOnline(roomCode: String?, isCreator: Boolean?) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = RangesOnline,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable<RangesOnline> {
            RangesOnlineScreen(
                roomCode = roomCode,
                isCreator = isCreator
            )
        }
    }
}

/** SCREENS */
@Serializable
object RangesOnline