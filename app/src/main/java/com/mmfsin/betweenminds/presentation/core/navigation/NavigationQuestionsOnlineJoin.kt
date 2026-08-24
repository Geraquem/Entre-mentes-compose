package com.mmfsin.betweenminds.presentation.core.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mmfsin.betweenminds.presentation.dashboard.questions.online.creator.QuestionsOnlineCreatorScreen
import com.mmfsin.betweenminds.presentation.dashboard.questions.online.join.QuestionsOnlineJoinScreen
import kotlinx.serialization.Serializable

@Composable
fun NavigationQuestionsOnlineJoin(roomCode: String?) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = QuestionsOnlineJoin,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable<QuestionsOnlineJoin> { QuestionsOnlineJoinScreen(roomCode = roomCode) }
    }
}

/** SCREENS */
@Serializable
object QuestionsOnlineJoin