@file:OptIn(ExperimentalMaterial3Api::class)

package com.mmfsin.betweenminds.presentation.dashboard.questions.offline

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.betweenminds.domain.models.QuestionPhaseType.FIRST_OPINION
import com.mmfsin.betweenminds.domain.models.QuestionPhaseType.NEXT_ROUND
import com.mmfsin.betweenminds.domain.models.QuestionPhaseType.RESULTS
import com.mmfsin.betweenminds.domain.models.QuestionPhaseType.SECOND_OPINION
import com.mmfsin.betweenminds.presentation.core.components.ButtonCustom
import com.mmfsin.betweenminds.presentation.core.components.CustomToolbar
import com.mmfsin.betweenminds.presentation.core.components.ErrorDialog
import com.mmfsin.betweenminds.presentation.core.components.MediumText
import com.mmfsin.betweenminds.presentation.core.components.SpacerLarge
import com.mmfsin.betweenminds.presentation.core.components.SpacerMedium
import com.mmfsin.betweenminds.presentation.core.components.SpacerSmall
import com.mmfsin.betweenminds.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenminds.presentation.core.theme.GrayHard
import com.mmfsin.betweenminds.presentation.core.theme.RedHard
import com.mmfsin.betweenminds.presentation.core.theme.RedMedium
import com.mmfsin.betweenminds.presentation.core.theme.Transparent
import com.mmfsin.betweenminds.presentation.core.theme.White
import com.mmfsin.betweenminds.presentation.core.theme.courier
import com.mmfsin.betweenminds.presentation.dashboard.common.ExitGameDialog
import com.mmfsin.betweenminds.presentation.dashboard.common.RoundCount
import com.mmfsin.betweenminds.presentation.dashboard.common.SwipeBox
import com.mmfsin.betweenminds.presentation.dashboard.questions.offline.components.InitialOfflineQuestionsDialog
import com.mmfsin.betweenminds.presentation.dashboard.questions.components.People
import com.mmfsin.betweenminds.presentation.dashboard.questions.components.QuestionRounds
import com.mmfsin.betweenminds.presentation.dashboard.questions.components.ResultQuestionsDialog
import com.mmfsin.betweenminds.utils.AnimateX
import com.mmfsin.betweenminds.utils.NAV_INSTR_QUESTIONS_OFFLINE
import com.mmfsin.betweenminds.utils.ShowAlpha
import com.mmfsin.betweenminds.utils.getKonfettiParty
import com.mmfsin.betweenminds.utils.openBedRockActivity
import nl.dionsegijn.konfetti.compose.KonfettiView
import kotlin.math.roundToInt

@Preview
@Composable
fun QuestionsOfflinePV() {
    QuestionsOfflineContent(
        uiStates = QuestionsOfflineStates(
            isLoading = false,
            showInitialDialog = false,
            curtainsOpen = true,
            showRoundView = false,
            showWhiteIndicator = true,
            controllerEnabled = false,
        ),
        {}, {}, {}, {},
        {}, {}, {},
        {}, {}, {}, {},
        {}, {},
    )
}

@Composable
fun QuestionsOfflineScreen(viewModel: QuestionsOfflineViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val activity = LocalActivity.current
    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()

    QuestionsOfflineContent(
        uiStates = uiStates,
        goBack = { activity?.finish() },
        goToInstructions = { context.goToInstructions() },
        hideInitialDialog = { viewModel.hideInitialDialog() },
        onBlueNameChange = { viewModel.onBlueNameChanged(it) },
        onOrangeNameChange = { viewModel.onOrangeNameChanged(it) },
        updateFirstOpinionPercents = { viewModel.updateFirstOpinionPercents(it) },
        updateSecondOpinionPercents = { viewModel.updateSecondOpinionPercents(it) },
        readyOpinionOne = { viewModel.readyOpinionOne() },
        readyOpinionTwo = { viewModel.readyOpinionTwo() },
        handleNextRound = { viewModel.handleNextRound() },
        showResultDialog = { viewModel.showResultDialog(it) },
        replay = { viewModel.replay() },
        showExitDialog = { viewModel.showExitDialog(it) }
    )
}

@Composable
fun QuestionsOfflineContent(
    uiStates: QuestionsOfflineStates,
    goBack: () -> Unit,
    goToInstructions: () -> Unit,
    hideInitialDialog: () -> Unit,
    onBlueNameChange: (String) -> Unit,
    onOrangeNameChange: (String) -> Unit,
    updateFirstOpinionPercents: (Int) -> Unit,
    updateSecondOpinionPercents: (Int) -> Unit,
    readyOpinionOne: () -> Unit,
    readyOpinionTwo: () -> Unit,
    handleNextRound: () -> Unit,
    showResultDialog: (Boolean) -> Unit,
    replay: () -> Unit,
    showExitDialog: (Boolean) -> Unit
) {

    var parentWidth by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            CustomToolbar(
                goBack = { goBack() },
                goToInstructions = { goToInstructions() }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
                .background(BackgroundBlack)
                .padding(innerPadding)
                .padding(12.dp)
        ) {

            if (uiStates.confettiTrigger > 0) {
                key(uiStates.confettiTrigger) {
                    KonfettiView(
                        modifier = Modifier.fillMaxSize(),
                        parties = listOf(getKonfettiParty())
                    )
                }
            }

            Column {
                QuestionRounds(uiStates.points)

                SpacerLarge()

                Box(
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    MediumText(
                        text = uiStates.actualQuestion,
                        color = White,
                        fontFamily = courier,
                        gravity = TextAlign.Center,
                        fontSize = 18.sp
                    )
                }

                SpacerMedium()

                People(
                    blueName = uiStates.blueName,
                    onBlueNameChange = { onBlueNameChange(it) },
                    firstBlueOpinion = uiStates.firstOpinionBlue,
                    secondBlueOpinion = uiStates.secondOpinionBlue,
                    orangeName = uiStates.orangeName,
                    onOrangeNameChange = { onOrangeNameChange(it) },
                    firstOrangeOpinion = uiStates.firstOpinionOrange,
                    secondOrangeOpinion = uiStates.secondOpinionOrange,
                    showFirstOpinion = uiStates.showFirstOpinionPercents,
                    showSecondOpinion = uiStates.showSecondOpinionPercents,
                    blueHandsUp = uiStates.blueHandsUp,
                    orangeHandsUp = uiStates.orangeHandsUp
                )

                SpacerLarge()

                /********************************** Slider **********************************/
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .height(50.dp)
                        .padding(horizontal = 8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(GrayHard)
                        .onSizeChanged { parentWidth = it.width },
                ) {

                    if (uiStates.showWhiteIndicator) {
                        Slider(
                            modifier = Modifier.fillMaxWidth(),
                            value = uiStates.firstSlider,
                            onValueChange = { updateFirstOpinionPercents(it.roundToInt()) },
                            valueRange = 0f..100f,
                            enabled = uiStates.controllerEnabled,
                            thumb = {
                                Box(
                                    modifier = Modifier
                                        .width(10.dp)
                                        .fillMaxHeight()
                                        .background(White)
                                )
                            },
                            colors = SliderDefaults.colors(
                                thumbColor = White,
                                activeTrackColor = Transparent,
                                disabledActiveTrackColor = Transparent,
                                inactiveTrackColor = Transparent,
                                disabledInactiveTrackColor = Transparent,
                            ),
                        )
                    }

                    if (uiStates.showRedIndicator) {
                        Slider(
                            modifier = Modifier.fillMaxWidth(),
                            value = uiStates.secondSlider,
                            onValueChange = { updateSecondOpinionPercents(it.roundToInt()) },
                            valueRange = 0f..100f,
                            enabled = uiStates.controllerEnabled,
                            thumb = {
                                Box(
                                    modifier = Modifier
                                        .width(10.dp)
                                        .fillMaxHeight()
                                        .background(RedMedium)
                                )
                            },
                            colors = SliderDefaults.colors(
                                thumbColor = White,
                                activeTrackColor = Transparent,
                                disabledActiveTrackColor = Transparent,
                                inactiveTrackColor = Transparent,
                                disabledInactiveTrackColor = Transparent,
                            ),
                        )
                    }

                    val halfWidth = with(LocalDensity.current) { (parentWidth / 2).toDp() }

                    /** Left curtain */
                    AnimateX(if (uiStates.curtainsOpen) -parentWidth / 2f else 0f) {
                        Box(
                            modifier = Modifier
                                .width(halfWidth)
                                .fillMaxHeight()
                                .background(RedHard)
                        )
                    }

                    /** Right curtain */
                    AnimateX(if (uiStates.curtainsOpen) parentWidth.toFloat() else 0f) {
                        Box(
                            modifier = Modifier
                                .width(halfWidth)
                                .fillMaxHeight()
                                .offset(x = halfWidth)
                                .background(RedHard)
                        )
                    }
                }

                Box(modifier = Modifier.weight(1f)) {
                    SwipeBox(modifier = Modifier.align(Alignment.BottomCenter))

                    Slider(
                        value = if (uiStates.phase == FIRST_OPINION) uiStates.firstSlider else uiStates.secondSlider,
                        onValueChange = {
                            if (uiStates.phase == FIRST_OPINION) updateFirstOpinionPercents(it.roundToInt())
                            else updateSecondOpinionPercents(it.roundToInt())
                        },
                        valueRange = 0f..100f,
                        enabled = uiStates.controllerEnabled,
                        thumb = { Box(modifier = Modifier.fillMaxHeight()) },
                        colors = SliderDefaults.colors(
                            thumbColor = Transparent,
                            activeTrackColor = Transparent,
                            disabledActiveTrackColor = Transparent,
                            inactiveTrackColor = Transparent,
                            disabledInactiveTrackColor = Transparent,
                        ),
                    )
                }

                SpacerLarge()
                ButtonCustom(
                    onClick = {
                        if (uiStates.buttonEnabled) {
                            when (uiStates.phase) {
                                FIRST_OPINION -> readyOpinionOne()
                                SECOND_OPINION -> readyOpinionTwo()
                                NEXT_ROUND -> handleNextRound()
                                RESULTS -> showResultDialog(true)
                            }
                        }
                    },
                    text = uiStates.buttonText,
                    modifier = Modifier.fillMaxWidth()
                )
                SpacerSmall()
            }

            ShowAlpha(uiStates.showRoundView) { RoundCount(uiStates.roundCount) }

            if (uiStates.showInitialDialog) {
                InitialOfflineQuestionsDialog(
                    blueName = uiStates.blueName,
                    onBlueNameChanged = { onBlueNameChange(it) },
                    orangeName = uiStates.orangeName,
                    onOrangeNameChanged = { onOrangeNameChange(it) },
                    startGame = { hideInitialDialog() },
                    howToPlay = { goToInstructions() },
                    isLoading = uiStates.isLoading
                )
            }

            if (uiStates.showResultDialog) {
                ResultQuestionsDialog(
                    points = uiStates.points,
                    blueName = uiStates.blueName,
                    orangeName = uiStates.orangeName,
                    exit = { goBack() },
                    replay = { replay() },
                    changeNames = {},
                )
            }

            if (uiStates.showExitDialog) {
                ExitGameDialog(
                    exit = { goBack() },
                    cancel = { showExitDialog(false) },
                )
            }

            if (uiStates.showSwwDialog) ErrorDialog(accept = { goBack() })

            BackHandler { showExitDialog(true) }
        }
    }
}

private fun Context.goToInstructions() = openBedRockActivity(NAV_INSTR_QUESTIONS_OFFLINE)
