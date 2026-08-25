package com.mmfsin.betweenminds.presentation.dashboard.questions.online.creator

import androidx.lifecycle.viewModelScope
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.domain.models.Question
import com.mmfsin.betweenminds.domain.models.QuestionPhaseType.FIRST_OPINION
import com.mmfsin.betweenminds.domain.models.QuestionPhaseType.NEXT_ROUND
import com.mmfsin.betweenminds.domain.models.QuestionPhaseType.RESULTS
import com.mmfsin.betweenminds.domain.usecases.GetQuestionsUseCase
import com.mmfsin.betweenminds.domain.usecases.RestartGameAndResetRoomUseCase
import com.mmfsin.betweenminds.domain.usecases.SendOpinionOQuestionsToRoomUseCase
import com.mmfsin.betweenminds.domain.usecases.SetOQuestionsInRoomUseCase
import com.mmfsin.betweenminds.domain.usecases.WaitOtherPlayerOpinionOQuestionsUseCase
import com.mmfsin.betweenminds.presentation.core.base.BaseViewModel
import com.mmfsin.betweenminds.presentation.dashboard.questions.helper.calculatePoints
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class QuestionsOnlineCreatorViewModel @Inject constructor(
    private val getQuestionsUseCase: GetQuestionsUseCase,
    private val setOQuestionsInRoomUseCase: SetOQuestionsInRoomUseCase,
    private val sendOpinionOQuestionsToRoomUseCase: SendOpinionOQuestionsToRoomUseCase,
    private val waitOtherPlayerOpinionOQuestionsUseCase: WaitOtherPlayerOpinionOQuestionsUseCase,
    private val restartGameAndResetRoomUseCase: RestartGameAndResetRoomUseCase,
) : BaseViewModel<QuestionsOnlineCreatorStates>(QuestionsOnlineCreatorStates()) {

    init {
        getQuestions()
    }

    fun updateRoomCode(code: String?) {
        if (code == null) sww()
        else _uiState.update { it.copy(roomCode = code) }
    }

    private fun getQuestions() {
        executeUseCase(
            { getQuestionsUseCase() },
            { questions ->
                _uiState.update { it.copy(questions = questions) }
                getQuestionsToRoom()
            },
            { sww() }
        )
    }

    private fun setQuestion() {
        val states = uiState.value
        val roomQuestions = states.roomQuestions

        if (roomQuestions.isEmpty()) sww()
        else {
            val newQuestion = if (states.roomQuestionPos >= states.roomQuestions.size) {
                _uiState.update { it.copy(roomQuestionPos = 0) }
                roomQuestions[0].question
            } else roomQuestions[states.roomQuestionPos].question

            _uiState.update {
                it.copy(
                    actualQuestion = newQuestion,
                    isLoading = false
                )
            }
        }
    }

    private fun setDataInRoom() {
        val states = uiState.value
        executeUseCase(
            {
                setOQuestionsInRoomUseCase.execute(
                    roomId = states.roomCode,
                    names = Pair(states.blueName, states.orangeName),
                    questions = states.roomQuestions,
                    gameNumber = states.gameNumber
                )
            },
            { startMyOpinion() },
            { sww() }
        )
    }

    private fun getQuestionsToRoom(): List<Question> {
        val states = uiState.value
        if (states.questionPos >= states.questions.size) sww()

        val roomQuestions = states.questions.subList(
            states.questionPos, (states.questionPos + 4).coerceAtMost(states.questions.size)
        )

        _uiState.update {
            it.copy(
                roomQuestions = roomQuestions,
                questionPos = states.questionPos + 4
            )
        }
        setQuestion()
        return roomQuestions
    }

    fun hideInitialDialog() {
        setDataInRoom()
        _uiState.update { it.copy(showInitialDialog = false) }
        viewModelScope.launch {
            delay(1000)
            _uiState.update { it.copy(showRoundView = false) }
            delay(1000)
        }
    }

    fun updateMyOpinionPercents(value: Int) {
        val firstOpBlue = 100 - value
        handleHandsUp(percent = firstOpBlue)

        _uiState.update {
            it.copy(
                whiteSlider = value.toFloat(),
                firstOpinionBlue = firstOpBlue,
                firstOpinionOrange = value
            )
        }
    }

    private fun handleHandsUp(percent: Int) {
        if (percent > 50) {
            _uiState.update { it.copy(blueHandsUp = true, orangeHandsUp = false) }
        } else if (percent == 50) {
            _uiState.update { it.copy(blueHandsUp = false, orangeHandsUp = false) }
        } else {
            _uiState.update { it.copy(blueHandsUp = false, orangeHandsUp = true) }
        }
    }

    private fun startMyOpinion() {
        _uiState.update {
            it.copy(
                phase = FIRST_OPINION,
                showWhiteIndicator = true,
                showFirstOpinionPercents = true,
                buttonText = R.string.btn_ready,
                controllerEnabled = true,
                buttonEnabled = true,
            )
        }
        openCurtains()
    }

    fun readyMyOpinion() {
        showWaitingOtherPlayerDialog(true)

        _uiState.update {
            it.copy(
                buttonEnabled = false,
                controllerEnabled = false
            )
        }

        val states = uiState.value
        executeUseCase(
            {
                sendOpinionOQuestionsToRoomUseCase.execute(
                    roomId = states.roomCode,
                    isCreator = true,
                    round = states.roundCount,
                    orangeOpinion = states.whiteSlider.roundToInt(),
                )
            },
            { waitForOtherPlayerOpinion() },
            { sww() })
    }

    private fun waitForOtherPlayerOpinion() {
        val states = uiState.value
        executeUseCase(
            {
                waitOtherPlayerOpinionOQuestionsUseCase.execute(
                    roomId = states.roomCode,
                    isCreator = true,
                    round = states.roundCount
                )
            },
            { otherPlayerOpinion ->
                showWaitingOtherPlayerDialog(false)
                showOtherPlayerOpinion(otherPlayerOpinion)
            },
            { sww() }
        )
    }

    private fun showOtherPlayerOpinion(otherPlayerOpinion: Int) {
        val secondOpBlue = 100 - otherPlayerOpinion
        handleHandsUp(percent = secondOpBlue)

        val states = uiState.value
        val roundPoints = calculatePoints(states.firstOpinionBlue, secondOpBlue)

        _uiState.update {
            it.copy(
                redSlider = otherPlayerOpinion.toFloat(),
                secondOpinionBlue = secondOpBlue,
                secondOpinionOrange = otherPlayerOpinion,
                phase = if (states.roundCount != 1) NEXT_ROUND else RESULTS,
                roomQuestionPos = states.roomQuestionPos + 1,
                roundCount = states.roundCount + 1
            )
        }

        viewModelScope.launch {
            delay(750)

            _uiState.update {
                it.copy(
                    points = states.points.toMutableList().apply { this[states.roundCount] = roundPoints },
                    confettiTrigger = if (roundPoints > 9) states.confettiTrigger + 1 else 0,
                    showSecondOpinionPercents = true,
                    showRedIndicator = true,
                    buttonEnabled = true,
                    buttonText = if (states.roundCount != 3) R.string.btn_next_round else R.string.btn_see_result
                )
            }
        }
    }

    fun handleNextRound() {
        closeCurtains()
        handleHandsUp(50)

        _uiState.update {
            it.copy(
                showRoundView = true,
                controllerEnabled = false,
                buttonEnabled = false,
                showFirstOpinionPercents = false,
                showSecondOpinionPercents = false,
            )
        }

        viewModelScope.launch {
            delay(1500)
            _uiState.update {
                it.copy(
                    showWhiteIndicator = false,
                    showRedIndicator = false,
                    showRoundView = false,
                    whiteSlider = 50f,
                    firstOpinionBlue = 50,
                    secondOpinionBlue = 50,
                    redSlider = 50f,
                    firstOpinionOrange = 50,
                    secondOpinionOrange = 50
                )
            }
            setQuestion()

            delay(250)
            startMyOpinion()
        }
    }

    fun replay() {
        val states = uiState.value

        _uiState.update {
            it.copy(
                isLoading = true,
                gameNumber = states.gameNumber + 1,
                roundCount = 0,
                showRoundView = true,
                points = listOf(null, null, null, null),
                controllerEnabled = false,
                buttonEnabled = false,
                showWhiteIndicator = false,
                showRedIndicator = false,
                showFirstOpinionPercents = false,
                showSecondOpinionPercents = false,
            )
        }
        closeCurtains()

        executeUseCase(
            { restartGameAndResetRoomUseCase.execute(states.roomCode) },
            {
                showResultDialog(false)
                viewModelScope.launch {
                    delay(1500)
                    _uiState.update {
                        it.copy(
                            showRoundView = false,
                            whiteSlider = 50f,
                            redSlider = 50f,
                            firstOpinionBlue = 50,
                            secondOpinionBlue = 50,
                            firstOpinionOrange = 50,
                            secondOpinionOrange = 50
                        )
                    }
                    getQuestionsToRoom()
                    setDataInRoom()
                }
            },
            { sww() }
        )
    }


    fun onBlueNameChanged(value: String) = _uiState.update { it.copy(blueName = value) }
    fun onOrangeNameChanged(value: String) = _uiState.update { it.copy(orangeName = value) }

    fun openCurtains() = _uiState.update { it.copy(curtainsOpen = true) }
    fun closeCurtains() = _uiState.update { it.copy(curtainsOpen = false) }

    fun showResultDialog(value: Boolean) = _uiState.update { it.copy(showResultDialog = value) }
    fun showWaitingOtherPlayerDialog(value: Boolean) = _uiState.update { it.copy(showWaitingOtherPlayerDialog = value) }
    fun showExitDialog(value: Boolean) = _uiState.update { it.copy(showExitDialog = value) }

    private fun sww() = _uiState.update { it.copy(showSwwDialog = true) }
}