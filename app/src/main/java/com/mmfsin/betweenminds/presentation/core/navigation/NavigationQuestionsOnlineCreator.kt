package com.mmfsin.betweenminds.presentation.core.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mmfsin.betweenminds.presentation.dashboard.questions.online.creator.QuestionsOnlineCreatorScreen
import kotlinx.serialization.Serializable

@Composable
fun NavigationQuestionsOnlineCreator(roomCode: String?) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = QuestionsOnlineCreator,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable<QuestionsOnlineCreator> { QuestionsOnlineCreatorScreen(roomCode = roomCode) }
    }
}

/** SCREENS */
@Serializable
object QuestionsOnlineCreator