@file:OptIn(ExperimentalMaterial3Api::class)

package com.mmfsin.betweenmindscompose.presentation.dashboard.questions.offline

import android.content.Context
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
import com.mmfsin.betweenmindscompose.domain.models.QuestionPhaseType.FIRST_OPINION
import com.mmfsin.betweenmindscompose.domain.models.QuestionPhaseType.NEXT_ROUND
import com.mmfsin.betweenmindscompose.domain.models.QuestionPhaseType.RESULTS
import com.mmfsin.betweenmindscompose.domain.models.QuestionPhaseType.SECOND_OPINION
import com.mmfsin.betweenmindscompose.presentation.core.components.ButtonCustom
import com.mmfsin.betweenmindscompose.presentation.core.components.CustomToolbar
import com.mmfsin.betweenmindscompose.presentation.core.components.MediumText
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerLarge
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerMedium
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerSmall
import com.mmfsin.betweenmindscompose.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenmindscompose.presentation.core.theme.GrayHard
import com.mmfsin.betweenmindscompose.presentation.core.theme.RedHard
import com.mmfsin.betweenmindscompose.presentation.core.theme.RedMedium
import com.mmfsin.betweenmindscompose.presentation.core.theme.Transparent
import com.mmfsin.betweenmindscompose.presentation.core.theme.White
import com.mmfsin.betweenmindscompose.presentation.core.theme.courier
import com.mmfsin.betweenmindscompose.presentation.dashboard.common.RoundCount
import com.mmfsin.betweenmindscompose.presentation.dashboard.common.SwipeBox
import com.mmfsin.betweenmindscompose.presentation.dashboard.questions.components.InitialOfflineDialog
import com.mmfsin.betweenmindscompose.presentation.dashboard.questions.components.People
import com.mmfsin.betweenmindscompose.presentation.dashboard.questions.components.ResultDialog
import com.mmfsin.betweenmindscompose.presentation.dashboard.questions.components.Rounds
import com.mmfsin.betweenmindscompose.presentation.dashboard.questions.helper.getKonfettiParty
import com.mmfsin.betweenmindscompose.utils.AnimateX
import com.mmfsin.betweenmindscompose.utils.NAV_INSTR_QUESTIONS_OFFLINE
import com.mmfsin.betweenmindscompose.utils.ShowAlpha
import com.mmfsin.betweenmindscompose.utils.openBedRockActivity
import nl.dionsegijn.konfetti.compose.KonfettiView
import kotlin.math.roundToInt

@Preview
@Composable
fun QuestionsOfflinePV() {
    QuestionsOfflineContent(
        uiState = QuestionsOfflineStates(
            isLoading = false,
            showInitialDialog = false,
            curtainsOpen = true,
            showRoundView = false,
            showWhiteIndicator = true
        ),
        {}, {}, {}, {},
        {}, {}, {},
        {}, {}, {}, {},
    )
}

@Composable
fun QuestionsOfflineScreen(viewModel: QuestionsOfflineViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    QuestionsOfflineContent(
        uiState = uiState,
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
        replay = { viewModel.replay() }
    )
}

@Composable
fun QuestionsOfflineContent(
    uiState: QuestionsOfflineStates,
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
) {

    var parentWidth by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            CustomToolbar(
                goBack = {},
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

            if (uiState.confettiTrigger > 0) {
                key(uiState.confettiTrigger) {
                    KonfettiView(
                        modifier = Modifier.fillMaxSize(),
                        parties = listOf(getKonfettiParty())
                    )
                }
            }

            Column {
                Rounds(uiState.points)

                SpacerLarge()

                Box(
                    modifier = Modifier.fillMaxWidth().height(80.dp),
                    contentAlignment = Alignment.Center
                ) {
                    MediumText(
                        text = uiState.actualQuestion,
                        color = White,
                        fontFamily = courier,
                        gravity = TextAlign.Center,
                        fontSize = 18.sp
                    )
                }

                SpacerMedium()

                People(
                    blueName = uiState.blueName,
                    onBlueNameChange = { onBlueNameChange(it) },
                    firstBlueOpinion = uiState.firstOpinionBlue,
                    secondBlueOpinion = uiState.secondOpinionBlue,
                    orangeName = uiState.orangeName,
                    onOrangeNameChange = { onOrangeNameChange(it) },
                    firstOrangeOpinion = uiState.firstOpinionOrange,
                    secondOrangeOpinion = uiState.secondOpinionOrange,
                    showFirstOpinion = uiState.showFirstOpinionPercents,
                    showSecondOpinion = uiState.showSecondOpinionPercents,
                    blueHandsUp = uiState.blueHandsUp,
                    orangeHandsUp = uiState.orangeHandsUp
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

                    if (uiState.showWhiteIndicator) {
                        Slider(
                            modifier = Modifier.fillMaxWidth(),
                            value = uiState.firstSlider,
                            onValueChange = { updateFirstOpinionPercents(it.roundToInt()) },
                            valueRange = 0f..100f,
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
                                inactiveTrackColor = Transparent
                            ),
                        )
                    }

                    if (uiState.showRedIndicator) {
                        Slider(
                            modifier = Modifier.fillMaxWidth(),
                            value = uiState.secondSlider,
                            onValueChange = { updateSecondOpinionPercents(it.roundToInt()) },
                            valueRange = 0f..100f,
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
                                inactiveTrackColor = Transparent
                            ),
                        )
                    }

                    val halfWidth = with(LocalDensity.current) { (parentWidth / 2).toDp() }

                    /** Left curtain */
                    AnimateX(if (uiState.curtainsOpen) -parentWidth / 2f else 0f) {
                        Box(
                            modifier = Modifier
                                .width(halfWidth)
                                .fillMaxHeight()
                                .background(RedHard)
                        )
                    }

                    /** Right curtain */
                    AnimateX(if (uiState.curtainsOpen) parentWidth.toFloat() else 0f) {
                        Box(
                            modifier = Modifier
                                .width(halfWidth)
                                .fillMaxHeight()
                                .offset(x = halfWidth)
                                .background(RedHard)
                        )
                    }
                }

                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    SwipeBox(modifier = Modifier.align(Alignment.BottomCenter))
                    Slider(
                        value = if (uiState.phase == FIRST_OPINION) uiState.firstSlider else uiState.secondSlider,
                        onValueChange = {
                            if (uiState.phase == FIRST_OPINION) updateFirstOpinionPercents(it.roundToInt())
                            else updateSecondOpinionPercents(it.roundToInt())
                        },
                        valueRange = 0f..100f,
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
                            inactiveTrackColor = Transparent
                        ),
                    )
                }

                //                SwipeBox()
                SpacerLarge()
                ButtonCustom(
                    onClick = {
                        if (uiState.buttonEnabled) {
                            when (uiState.phase) {
                                FIRST_OPINION -> readyOpinionOne()
                                SECOND_OPINION -> readyOpinionTwo()
                                NEXT_ROUND -> handleNextRound()
                                RESULTS -> showResultDialog(true)
                            }
                        }
                    },
                    text = uiState.buttonText,
                    modifier = Modifier.fillMaxWidth()
                )
                SpacerSmall()
            }

            ShowAlpha(uiState.showRoundView) { RoundCount(uiState.roundCount) }

            if (uiState.showInitialDialog) {
                InitialOfflineDialog(
                    blueName = uiState.blueName,
                    onBlueNameChanged = { onBlueNameChange(it) },
                    orangeName = uiState.orangeName,
                    onOrangeNameChanged = { onOrangeNameChange(it) },
                    startGame = { hideInitialDialog() },
                    howToPlay = {}
                )
            }

            if (uiState.showResultDialog) {
                ResultDialog(
                    points = uiState.points,
                    blueName = uiState.blueName,
                    orangeName = uiState.orangeName,
                    exit = {},
                    replay = { replay() },
                    changeNames = {},
                )
            }
        }
    }
}

private fun Context.goToInstructions() = openBedRockActivity(NAV_INSTR_QUESTIONS_OFFLINE)
