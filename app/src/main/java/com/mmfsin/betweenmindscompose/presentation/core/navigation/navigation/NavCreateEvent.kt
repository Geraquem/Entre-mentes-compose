package com.mmfsin.betweenmindscompose.presentation.core.navigation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

@Composable
fun NavCreateEvent() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = CreateEvent
    ) {
        composable<CreateEvent> {
//            CreateEventScreen()
        }
    }
}

/** SCREENS */
@Serializable
object CreateEvent