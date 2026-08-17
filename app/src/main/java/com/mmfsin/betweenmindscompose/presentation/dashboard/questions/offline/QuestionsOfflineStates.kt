package com.mmfsin.betweenmindscompose.presentation.dashboard.questions.offline

import com.mmfsin.betweenmindscompose.R
import com.mmfsin.betweenmindscompose.domain.models.Question
import com.mmfsin.betweenmindscompose.domain.models.QuestionPhaseType
import com.mmfsin.betweenmindscompose.domain.models.QuestionPhaseType.FIRST_OPINION

data class QuestionsOfflineStates(
    val isLoading: Boolean = true,

    val showInitialDialog: Boolean = true,
    val showResultDialog: Boolean = false,

    val showRoundView: Boolean = true,
    var roundCount: Int = 0,
    val phase: QuestionPhaseType = FIRST_OPINION,

    val initialOffsetX: Float = 0f,
    val offsetXWhite: Float = 0f,
    val offsetXRed: Float = 0f,

    val questions: List<Question> = emptyList(),
    var questionPos: Int = 0,
    val actualQuestion: String = "",

    val points: List<Int?> = listOf(null, null, null, null),

    val blueName: String = "",
    val firstOpinionBlue: Int = 50,
    val secondOpinionBlue: Int = 50,
    val orangeName: String = "",
    val firstOpinionOrange: Int = 50,
    val secondOpinionOrange: Int = 50,

    val blueHandsUp: Boolean = false,
    val orangeHandsUp: Boolean = false,

    val showFirstOpinionPercents: Boolean = true,
    val showSecondOpinionPercents: Boolean = false,

    val showWhiteIndicator: Boolean = false,
    val showRedIndicator: Boolean = false,

    val curtainLeftPosition: Float = 0f,
    val curtainRightPosition: Float = 0f,

    val controllerEnabled: Boolean = false,
    val buttonEnabled: Boolean = false,

    val buttonText: Int = R.string.btn_ready
)