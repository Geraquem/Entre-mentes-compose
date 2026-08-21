package com.mmfsin.betweenminds.presentation.core.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mmfsin.betweenminds.presentation.dashboard.questions.online.QuestionsOnlineScreen
import kotlinx.serialization.Serializable

@Composable
fun NavigationQuestionsOnline() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = QuestionsOnline,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable<QuestionsOnline> { QuestionsOnlineScreen() }
    }
}

/** SCREENS */
@Serializable
object QuestionsOnline