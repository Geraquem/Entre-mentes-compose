package com.mmfsin.betweenmindscompose.presentation.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mmfsin.betweenmindscompose.presentation.menu.MenuScreen
import kotlinx.serialization.Serializable

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Menu
    ) {
        composable<Menu> {
            MenuScreen()
        }
    }
}

/** SCREENS */
@Serializable
object Menu

@Serializable
object Selection

@Serializable
object Questions

@Serializable
object Ranges

@Serializable
object Packs