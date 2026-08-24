package com.mmfsin.betweenminds.presentation.dashboard.questions.online.join

import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.domain.models.Question
import com.mmfsin.betweenminds.domain.models.QuestionPhaseType
import com.mmfsin.betweenminds.domain.models.QuestionPhaseType.FIRST_OPINION

data class QuestionsOnlineJoinStates(
    val isLoading: Boolean = true,

    val roomCode: String = "",

    val showInitialDialog: Boolean = true,
    val showResultDialog: Boolean = false,
    val showWaitingOtherPlayerDialog: Boolean = false,
    val showExitDialog: Boolean = false,
    val showSwwDialog: Boolean = false,

    val showRoundView: Boolean = true,
    var roundCount: Int = 0,
    var gameNumber: Int = 0,
    val phase: QuestionPhaseType = FIRST_OPINION,
    val questions: List<Question> = emptyList(),
    var questionPos: Int = 0,
    val actualQuestion: String = "",

    val points: List<Int?> = listOf(null, null, null, null),
    val confettiTrigger: Int = 0,

    val blueName: String = "",
    val whiteSlider: Float = 50f,
    val firstOpinionBlue: Int = 50,
    val secondOpinionBlue: Int = 50,

    val orangeName: String = "",
    val redSlider: Float = 50f,
    val firstOpinionOrange: Int = 50,
    val secondOpinionOrange: Int = 50,

    val blueHandsUp: Boolean = false,
    val orangeHandsUp: Boolean = false,

    val showFirstOpinionPercents: Boolean = false,
    val showSecondOpinionPercents: Boolean = true,

    val showWhiteIndicator: Boolean = false,
    val showRedIndicator: Boolean = false,

    val curtainsOpen: Boolean = false,

    val controllerEnabled: Boolean = false,
    val buttonEnabled: Boolean = false,

    val buttonText: Int = R.string.btn_ready
)
