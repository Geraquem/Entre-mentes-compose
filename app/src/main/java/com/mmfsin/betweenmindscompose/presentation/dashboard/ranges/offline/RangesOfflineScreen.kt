@file:OptIn(ExperimentalMaterial3Api::class)

package com.mmfsin.betweenmindscompose.presentation.dashboard.ranges.offline

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.betweenmindscompose.R
import com.mmfsin.betweenmindscompose.domain.models.RangePhaseType.MOVE_ARROW
import com.mmfsin.betweenmindscompose.domain.models.RangePhaseType.NEXT_ROUND
import com.mmfsin.betweenmindscompose.domain.models.RangePhaseType.RESULTS
import com.mmfsin.betweenmindscompose.domain.models.RangePhaseType.SHOW_BULLSEYE
import com.mmfsin.betweenmindscompose.presentation.core.components.ButtonCustom
import com.mmfsin.betweenmindscompose.presentation.core.components.CustomToolbar
import com.mmfsin.betweenmindscompose.presentation.core.components.MediumText
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerLarge
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerMini
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerSmall
import com.mmfsin.betweenmindscompose.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenmindscompose.presentation.core.theme.GrayHard
import com.mmfsin.betweenmindscompose.presentation.core.theme.GreenHard
import com.mmfsin.betweenmindscompose.presentation.core.theme.RedHard
import com.mmfsin.betweenmindscompose.presentation.core.theme.Transparent
import com.mmfsin.betweenmindscompose.presentation.core.theme.White
import com.mmfsin.betweenmindscompose.presentation.core.theme.alphazet
import com.mmfsin.betweenmindscompose.presentation.dashboard.common.ExitGameDialog
import com.mmfsin.betweenmindscompose.presentation.dashboard.common.RoundCount
import com.mmfsin.betweenmindscompose.presentation.dashboard.common.SwipeBox
import com.mmfsin.betweenmindscompose.presentation.dashboard.ranges.common.Bullseye
import com.mmfsin.betweenmindscompose.presentation.dashboard.ranges.common.RangeLimits
import com.mmfsin.betweenmindscompose.presentation.dashboard.ranges.common.RangeRounds
import com.mmfsin.betweenmindscompose.presentation.dashboard.ranges.components.InitialOfflineRangesDialog
import com.mmfsin.betweenmindscompose.presentation.dashboard.ranges.components.ResultRangesDialog
import com.mmfsin.betweenmindscompose.utils.AnimateX
import com.mmfsin.betweenmindscompose.utils.NAV_INSTR_RANGES_OFFLINE
import com.mmfsin.betweenmindscompose.utils.ShowAlpha
import com.mmfsin.betweenmindscompose.utils.getKonfettiParty
import com.mmfsin.betweenmindscompose.utils.openBedRockActivity
import nl.dionsegijn.konfetti.compose.KonfettiView
import kotlin.math.roundToInt

@Preview
@Composable
fun RangesOfflineScreenPV() {
    RangesOfflineContent(
        uiState = RangesOfflineStates(
            showRoundView = false,
            showInitialDialog = false,
            actualRangeLeft = "Rango izquierdo",
            actualRangeRight = "Rango derecho",
            hint = "El caballo blanco de Santiago",
            phase = MOVE_ARROW,
            curtainsOpen = true,
            showSlider = true
        ),
        {}, {}, {}, {},
        {}, {}, {},
        {}, {}, {}, {},
    )
}

@Composable
fun RangesOfflineScreen(viewModel: RangesOfflineViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val activity = LocalActivity.current
    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()

    RangesOfflineContent(
        uiState = uiStates,
        goBack = { activity?.finish() },
        goToInstructions = { context.goToInstructions() },
        hideInitialDialog = { viewModel.hideInitialDialog() },
        updateHint = { viewModel.updateHint(it) },
        updateSliderValue = { viewModel.updateSliderValue(it) },
        readyBullseyePhase = { viewModel.readyBullseyePhase() },
        readySliderPhase = { viewModel.readySliderPhase() },
        nextRound = { viewModel.nextRound() },
        showResultDialog = { viewModel.showResultDialog(true) },
        replay = { viewModel.replay() },
        showExitDialog = { viewModel.showExitDialog(it) },
    )
}

@Composable
fun RangesOfflineContent(
    uiState: RangesOfflineStates,
    goBack: () -> Unit,
    goToInstructions: () -> Unit,
    hideInitialDialog: () -> Unit,
    updateHint: (String) -> Unit,
    updateSliderValue: (Int) -> Unit,
    readyBullseyePhase: () -> Unit,
    readySliderPhase: () -> Unit,
    nextRound: () -> Unit,
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
                .padding(vertical = 12.dp, horizontal = 18.dp)
        ) {

            if (uiState.confettiTrigger > 0) {
                key(uiState.confettiTrigger) {
                    KonfettiView(
                        modifier = Modifier.fillMaxSize(),
                        parties = listOf(getKonfettiParty(uiState.confettiTrigger))
                    )
                }
            }

            Column {
                RangeRounds(uiState.points)

                SpacerLarge()

                Box(
                    modifier = Modifier.fillMaxWidth().height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    ShowAlpha(uiState.showEditTextHint) {
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
                                enabled = uiState.phase == SHOW_BULLSEYE,
                                modifier = Modifier.fillMaxWidth()
                                    .border(
                                        width = 2.dp,
                                        color = GrayHard,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 12.dp, vertical = 18.dp),
                                value = uiState.hint,
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

                    ShowAlpha(!uiState.showEditTextHint) {
                        MediumText(
                            text = uiState.hint,
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

                    if (uiState.showBullseye) {
                        Bullseye(uiState.bullsEyeStart)
                    }

                    if (uiState.showSlider) {
                        Slider(
                            modifier = Modifier.fillMaxWidth(),
                            enabled = uiState.sliderEnabled,
                            value = uiState.sliderValue,
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

                SpacerMini()

                Box(modifier = Modifier.weight(1f)) {
                    Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                        ShowAlpha(uiState.showSlider) { SwipeBox() }
                    }

                    RangeLimits(
                        leftRange = uiState.sliderValue.toString(),
                        rightRange = uiState.bullsEyeStart.toString()
                    )

                    if (uiState.showSlider) {
                        Slider(
                            modifier = Modifier.fillMaxWidth(),
                            enabled = uiState.sliderEnabled,
                            value = uiState.sliderValue,
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
                        if (uiState.buttonEnabled) {
                            when (uiState.phase) {
                                SHOW_BULLSEYE -> readyBullseyePhase()
                                MOVE_ARROW -> readySliderPhase()
                                NEXT_ROUND -> nextRound()
                                RESULTS -> showResultDialog(true)
                            }
                        }
                    },
                    text = uiState.buttonText,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            ShowAlpha(uiState.showRoundView) { RoundCount(uiState.roundCount) }

            if (uiState.showInitialDialog) {
                InitialOfflineRangesDialog(
                    startGame = { hideInitialDialog() },
                    howToPlay = { goToInstructions() }
                )
            }

            if (uiState.showResultDialog) {
                ResultRangesDialog(
                    points = uiState.points,
                    exit = { goBack() },
                    replay = { replay() },
                )
            }

            if (uiState.showExitDialog) {
                ExitGameDialog(
                    exit = { goBack() },
                    cancel = { showExitDialog(false) }
                )
            }

            BackHandler { showExitDialog(true) }
        }
    }
}

private fun Context.goToInstructions() = openBedRockActivity(NAV_INSTR_RANGES_OFFLINE)
