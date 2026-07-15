package com.mmfsin.betweenmindscompose.presentation.core.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mmfsin.betweenmindscompose.presentation.choose.ChooseScreen
import com.mmfsin.betweenmindscompose.presentation.menu.MenuScreen
import kotlinx.serialization.Serializable

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Menu,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable<Menu> {
            MenuScreen(
                goToChooseFragment = { type ->
                    navController.navigate(Choose(gameType = type))
                }
            )
        }

        composable<Choose> {
            ChooseScreen()
        }
    }
}

/** SCREENS */
@Serializable
object Menu

@Serializable
data class Choose(val gameType: String)

@Serializable
object Questions

@Serializable
object Ranges

@Serializable
object Packs