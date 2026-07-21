package com.mmfsin.betweenmindscompose.presentation.core.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mmfsin.betweenmindscompose.presentation.dashboard.questions.offline.QuestionsOfflineScreen
import kotlinx.serialization.Serializable

@Composable
fun NavigationQuestionsOffline() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = QuestionsOffline,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable<QuestionsOffline> { QuestionsOfflineScreen() }
    }
}

/** SCREENS */
@Serializable
object QuestionsOffline