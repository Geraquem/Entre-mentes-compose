package com.mmfsin.betweenminds.presentation.core.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.mmfsin.betweenminds.presentation.choose.connection.ConnectionScreen
import com.mmfsin.betweenminds.presentation.choose.connection.roomcode.RoomCodeScreen
import com.mmfsin.betweenminds.presentation.choose.gamemode.GameTypeScreen
import com.mmfsin.betweenminds.presentation.menu.MenuScreen
import com.mmfsin.betweenminds.presentation.packs.PacksScreen
import com.mmfsin.betweenminds.presentation.packs.detail.PackDetailScreen
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
                goToGameTypeScreen = { navController.navigate(GameType) },
                goToConnectionScreen = { gameTypeId -> navController.navigate(Connection(gameTypeId = gameTypeId)) },
                goToPacksScreen = { navController.navigate(Packs()) }
            )
        }

        composable<GameType> {
            GameTypeScreen(
                goBack = { navController.popBackStack() },
                goToConnectionScreen = { gameTypeId -> navController.navigate(Connection(gameTypeId = gameTypeId)) },
            )
        }

        composable<Connection> {
            ConnectionScreen(
                goBack = { navController.popBackStack() },
                roomCreated = { roomCode, gameTypeId ->
                    navController.navigate(
                        RoomCode(
                            roomCode = roomCode,
                            gameTypeId = gameTypeId
                        )
                    )
                },
                goToPacks = { tab -> navController.navigate(Packs(tab)) }
            )
        }

        composable<RoomCode> {
            RoomCodeScreen(
                goBack = { navController.popBackStack() }
            )
        }

        composable<Packs> { args ->
            val route = args.toRoute<Packs>()
            PacksScreen(
                initialTab = route.tab,
                goBack = { navController.popBackStack() },
                goToPackDetail = { packId -> navController.navigate(PackDetail(packId)) }
            )
        }

        composable<PackDetail> {
            PackDetailScreen(
                goBack = { navController.popBackStack() }
            )
        }
    }
}

/** SCREENS */
@Serializable
object Menu

@Serializable
object GameType

@Serializable
data class Connection(val gameTypeId: String)

@Serializable
data class RoomCode(val roomCode: String, val gameTypeId: String)

@Serializable
data class Packs(val tab: Int = 0)

@Serializable
data class PackDetail(val packId: String)