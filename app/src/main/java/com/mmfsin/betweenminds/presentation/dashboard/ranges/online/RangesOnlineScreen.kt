@file:OptIn(ExperimentalMaterial3Api::class)

package com.mmfsin.betweenminds.presentation.dashboard.ranges.online

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.domain.models.RangePhaseType.MOVE_ARROW
import com.mmfsin.betweenminds.domain.models.RangePhaseType.NEXT_ROUND
import com.mmfsin.betweenminds.domain.models.RangePhaseType.RESULTS
import com.mmfsin.betweenminds.domain.models.RangePhaseType.SHOW_BULLSEYE
import com.mmfsin.betweenminds.presentation.core.components.ButtonCustom
import com.mmfsin.betweenminds.presentation.core.components.CustomToolbar
import com.mmfsin.betweenminds.presentation.core.components.ErrorDialog
import com.mmfsin.betweenminds.presentation.core.components.MediumText
import com.mmfsin.betweenminds.presentation.core.components.SpacerLarge
import com.mmfsin.betweenminds.presentation.core.components.SpacerMini
import com.mmfsin.betweenminds.presentation.core.components.SpacerSmall
import com.mmfsin.betweenminds.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenminds.presentation.core.theme.GrayHard
import com.mmfsin.betweenminds.presentation.core.theme.RedHard
import com.mmfsin.betweenminds.presentation.core.theme.Transparent
import com.mmfsin.betweenminds.presentation.core.theme.White
import com.mmfsin.betweenminds.presentation.core.theme.alphazet
import com.mmfsin.betweenminds.presentation.dashboard.common.ExitGameDialog
import com.mmfsin.betweenminds.presentation.dashboard.common.RoundCount
import com.mmfsin.betweenminds.presentation.dashboard.common.SwipeBox
import com.mmfsin.betweenminds.presentation.dashboard.common.WaitingPartnerDialog
import com.mmfsin.betweenminds.presentation.dashboard.ranges.components.Bullseye
import com.mmfsin.betweenminds.presentation.dashboard.ranges.components.RangeLimits
import com.mmfsin.betweenminds.presentation.dashboard.ranges.components.RangeRounds
import com.mmfsin.betweenminds.presentation.dashboard.ranges.components.ResultRangesDialog
import com.mmfsin.betweenminds.presentation.dashboard.ranges.online.components.InitialOnlineRangesDialog
import com.mmfsin.betweenminds.presentation.dashboard.ranges.online.components.OtherPlayerRangesDialog
import com.mmfsin.betweenminds.utils.AnimateX
import com.mmfsin.betweenminds.utils.NAV_INSTR_RANGES_ONLINE
import com.mmfsin.betweenminds.utils.ShowAlpha
import com.mmfsin.betweenminds.utils.getKonfettiParty
import com.mmfsin.betweenminds.utils.openBedRockActivity
import nl.dionsegijn.konfetti.compose.KonfettiView
import kotlin.math.roundToInt

@Preview
@Composable
fun RangesOnlinePV() {
    RangesOnlineContent(
        uiStates = RangesOnlineStates(
            showInitialDialog = false,
            showRoundView = false,
        ),
        {}, {}, {}, {},
        {}, {}, {}, {},
        {}, {},
    )
}

@Composable
fun RangesOnlineScreen(
    viewModel: RangesOnlineViewModel = hiltViewModel(),
    roomCode: String?,
    isCreator: Boolean?
) {
    val context = LocalContext.current
    val activity = LocalActivity.current
    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(roomCode, isCreator) {
        viewModel.updateRoomCodeAndStatus(roomCode, isCreator)
    }

    RangesOnlineContent(
        uiStates = uiStates,
        goBack = { activity?.finish() },
        goToInstructions = { context.goToInstructions() },
        hideInitialDialog = { viewModel.hideInitialDialog() },
        updateHint = { viewModel.updateHint(it) },
        updateSliderValue = { viewModel.updateSliderValue(it) },
        checkBullseyePhase = { viewModel.checkBullseyePhase() },
        checkSliderPhase = { viewModel.checkSliderPhase() },
        nextRound = { viewModel.nextRound() },
        showResultDialog = { viewModel.showResultDialog(it) },
        //        replay = { viewModel.replay() },
        showExitDialog = { viewModel.showExitDialog(it) },
    )
}

@Composable
fun RangesOnlineContent(
    uiStates: RangesOnlineStates,
    goBack: () -> Unit,
    goToInstructions: () -> Unit,
    hideInitialDialog: () -> Unit,
    updateHint: (String) -> Unit,
    updateSliderValue: (Int) -> Unit,
    checkBullseyePhase: () -> Unit,
    checkSliderPhase: () -> Unit,
    nextRound: () -> Unit,
    showResultDialog: (Boolean) -> Unit,
    //    replay: () -> Unit,
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
                .padding(vertical = 12.dp, horizontal = 18.dp)
        ) {

            if (uiStates.confettiTrigger > 0) {
                key(uiStates.confettiTrigger) {
                    KonfettiView(
                        modifier = Modifier.fillMaxSize(),
                        parties = listOf(getKonfettiParty(uiStates.confettiTrigger))
                    )
                }
            }

            Column {
                RangeRounds(uiStates.points)

                SpacerLarge()

                Box(
                    modifier = Modifier.fillMaxWidth().height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    ShowAlpha(uiStates.showEditTextHint) {
                        Column(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            MediumText(
                                text = R.string.ranges_write_a_clue,
                                color = White,
                                fontFamily = alphazet
                            )

                            SpacerSmall()

                            BasicTextField(
                                enabled = uiStates.phase == SHOW_BULLSEYE,
                                modifier = Modifier.fillMaxWidth()
                                    .border(
                                        width = 2.dp,
                                        color = GrayHard,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 12.dp, vertical = 18.dp),
                                value = uiStates.hint,
                                onValueChange = { updateHint(it.take(150)) },
                                singleLine = true,
                                textStyle = MaterialTheme.typography.bodyLarge.copy(color = White),
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Done,
                                    capitalization = KeyboardCapitalization.Sentences
                                ),
                                cursorBrush = SolidColor(GrayHard),
                            )
                        }
                    }

                    ShowAlpha(!uiStates.showEditTextHint) {
                        MediumText(
                            text = uiStates.hint,
                            color = White
                        )
                    }
                }

                SpacerLarge()

                Box(
                    modifier = Modifier.fillMaxWidth()
                        .height(50.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(GrayHard)
                        .onSizeChanged { parentWidth = it.width },
                ) {

                    if (uiStates.showBullseye) {
                        Bullseye(uiStates.bullsEyeStart)
                    }

                    if (uiStates.showSlider) {
                        Slider(
                            modifier = Modifier.fillMaxWidth(),
                            enabled = uiStates.sliderEnabled,
                            value = uiStates.sliderValue,
                            onValueChange = { updateSliderValue(it.roundToInt()) },
                            valueRange = 0f..100f,
                            thumb = {
                                Box(
                                    modifier = Modifier
                                        .width(4.dp)
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

                SpacerMini()

                Box(modifier = Modifier.weight(1f)) {
                    Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                        ShowAlpha(uiStates.showSlider) { SwipeBox() }
                    }

                    RangeLimits(
                        //                        leftRange = uiStates.sliderValue.toString(),
                        //                        rightRange = uiStates.bullsEyeStart.toString()
                        leftRange = uiStates.actualRangeLeft,
                        rightRange = uiStates.actualRangeRight
                    )

                    if (uiStates.showSlider) {
                        Slider(
                            modifier = Modifier.fillMaxWidth(),
                            enabled = uiStates.sliderEnabled,
                            value = uiStates.sliderValue,
                            onValueChange = { updateSliderValue(it.roundToInt()) },
                            valueRange = 0f..100f,
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
                }

                SpacerSmall()

                ButtonCustom(
                    onClick = {
                        if (uiStates.buttonEnabled) {
                            when (uiStates.phase) {
                                SHOW_BULLSEYE -> checkBullseyePhase()
                                MOVE_ARROW -> checkSliderPhase()
                                NEXT_ROUND -> nextRound()
                                RESULTS -> showResultDialog(true)
                            }
                        }
                    },
                    text = uiStates.buttonText,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            ShowAlpha(uiStates.showRoundView) { RoundCount(uiStates.roundCount) }

            if (uiStates.showInitialDialog) {
                InitialOnlineRangesDialog(
                    startGame = { hideInitialDialog() },
                    howToPlay = { goToInstructions() },
                    exit = { goBack() },
                    isLoading = uiStates.isLoading
                )
            }

            if (uiStates.showWaitingOtherPlayerDialog) {
                WaitingPartnerDialog(goBack = { showExitDialog(true) })
            }

            if (uiStates.showOtherPlayerRangesDialog) {
                OtherPlayerRangesDialog()
            }

            if (uiStates.showResultDialog) {
                ResultRangesDialog(
                    points = uiStates.points,
                    isOnline = true,
                    exit = { goBack() },
                    replay = { /*replay() */ },
                )
            }

            if (uiStates.showExitDialog) {
                ExitGameDialog(
                    exit = { goBack() },
                    cancel = { showExitDialog(false) }
                )
            }

            if (uiStates.showSwwDialog) ErrorDialog(accept = { goBack() })

            BackHandler { showExitDialog(true) }
        }
    }
}

private fun Context.goToInstructions() = openBedRockActivity(NAV_INSTR_RANGES_ONLINE)