package com.mmfsin.betweenmindscompose.presentation.dashboard.questions.offline

import com.mmfsin.betweenmindscompose.domain.models.Question

data class QuestionsOfflineStates(
    val isLoading: Boolean = true,

    val questions: List<Question> = emptyList(),

    val blueName: String = "",
    val firstOpinionBlue: Int = 50,
    val secondOpinionBlue: Int = 50,
    val orangeName: String = "",
    val firstOpinionOrange: Int = 50,
    val secondOpinionOrange: Int = 50,

    val firstOpinionVisible: Boolean = true,
    val secondOpinionVisible: Boolean = false,

    val showRoundView: Boolean = true,
    val roundCount: Int = 1,
)