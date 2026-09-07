package com.mmfsin.betweenminds.presentation.core.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mmfsin.betweenminds.presentation.dashboard.order.offline.OrderOfflineScreen
import kotlinx.serialization.Serializable

@Composable
fun NavigationOrderOffline() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = OrderOffline,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable<OrderOffline> { OrderOfflineScreen() }
    }
}

/** SCREENS */
@Serializable
object OrderOffline