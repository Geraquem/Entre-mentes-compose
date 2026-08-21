package com.mmfsin.betweenminds.presentation.core.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mmfsin.betweenminds.presentation.choose.ChooseScreen
import com.mmfsin.betweenminds.presentation.choose.createroom.RoomCodeScreen
import com.mmfsin.betweenminds.presentation.menu.MenuScreen
import kotlinx.serialization.Serializable

@Composable
fun NavigationMain() {
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
                goToChooseFragment = { gameTypeId ->
                    navController.navigate(Choose(gameTypeId = gameTypeId))
                }
            )
        }

        composable<Choose> {
            ChooseScreen(
                goBack = { navController.popBackStack() },
                roomJoined = {},
                roomCreated = { roomCode, gameTypeId ->
                    navController.navigate(
                        RoomCode(
                            roomCode = roomCode,
                            gameTypeId = gameTypeId
                        )
                    )
                }
            )
        }

        composable<RoomCode> {
            RoomCodeScreen(
                goBack = { navController.popBackStack() }
            )
        }
    }
}

/** SCREENS */
@Serializable
object Menu

@Serializable
data class Choose(val gameTypeId: String)

@Serializable
data class RoomCode(val roomCode: String, val gameTypeId: String)