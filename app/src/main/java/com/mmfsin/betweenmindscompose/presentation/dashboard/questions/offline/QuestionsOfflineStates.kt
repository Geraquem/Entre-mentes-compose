package com.mmfsin.betweenmindscompose.presentation.dashboard.questions.offline

import com.mmfsin.betweenmindscompose.domain.models.Question
import com.mmfsin.betweenmindscompose.domain.models.QuestionRoundType
import com.mmfsin.betweenmindscompose.domain.models.QuestionRoundType.FIRST_OPINION

data class QuestionsOfflineStates(
    val isLoading: Boolean = true,

    val showRoundView: Boolean = true,
    val roundCount: Int = 1,
    val roundType: QuestionRoundType = FIRST_OPINION,

    val offsetXWhite: Float = 0f,
    val offsetXRed: Float = 0f,

    val questions: List<Question> = emptyList(),

    val blueName: String = "",
    val firstOpinionBlue: Int = 50,
    val secondOpinionBlue: Int = 50,
    val orangeName: String = "",
    val firstOpinionOrange: Int = 50,
    val secondOpinionOrange: Int = 50,

    val blueHandsUp: Boolean = false,
    val orangeHandsUp: Boolean = false,

    val firstOpinionVisible: Boolean = true,
    val secondOpinionVisible: Boolean = false,

    val curtainLeftPosition: Float = 0f,
    val curtainRightPosition: Float = 0f,

    val arrowPointerVisible: Boolean = false,
)