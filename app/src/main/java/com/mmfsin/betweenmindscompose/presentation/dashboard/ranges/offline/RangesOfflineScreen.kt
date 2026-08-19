package com.mmfsin.betweenmindscompose.presentation.dashboard.ranges.offline

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
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.betweenmindscompose.domain.models.RangePhaseType.MOVE_ARROW
import com.mmfsin.betweenmindscompose.domain.models.RangePhaseType.NEXT_ROUND
import com.mmfsin.betweenmindscompose.domain.models.RangePhaseType.RESULTS
import com.mmfsin.betweenmindscompose.domain.models.RangePhaseType.SHOW_BULLSEYE
import com.mmfsin.betweenmindscompose.presentation.core.components.ButtonCustom
import com.mmfsin.betweenmindscompose.presentation.core.components.CustomToolbar
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerLarge
import com.mmfsin.betweenmindscompose.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenmindscompose.presentation.core.theme.GrayHard
import com.mmfsin.betweenmindscompose.presentation.core.theme.RedHard
import com.mmfsin.betweenmindscompose.presentation.core.theme.RedLight
import com.mmfsin.betweenmindscompose.presentation.dashboard.common.RoundCount
import com.mmfsin.betweenmindscompose.presentation.dashboard.common.SwipeBox
import com.mmfsin.betweenmindscompose.presentation.dashboard.questions.components.Rounds
import com.mmfsin.betweenmindscompose.presentation.dashboard.questions.helper.getKonfettiParty
import com.mmfsin.betweenmindscompose.presentation.dashboard.ranges.common.RangeLimits
import com.mmfsin.betweenmindscompose.presentation.dashboard.ranges.offline.components.InitialOfflineRangesDialog
import com.mmfsin.betweenmindscompose.utils.AnimateX
import com.mmfsin.betweenmindscompose.utils.NAV_INSTR_RANGES_OFFLINE
import com.mmfsin.betweenmindscompose.utils.ShowAlpha
import com.mmfsin.betweenmindscompose.utils.openBedRockActivity
import nl.dionsegijn.konfetti.compose.KonfettiView

@Preview
@Composable
fun RangesOfflineScreenPV() {
    RangesOfflineContent(
        uiState = RangesOfflineStates(
            showRoundView = false,
            actualRangeLeft = "Rango izquierdo",
            actualRangeRight = "Rango derecho",

            controllerEnabled = true
        ),
        {}, {},
    )
}

@Composable
fun RangesOfflineScreen(viewModel: RangesOfflineViewModel = hiltViewModel()) {
    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()

    RangesOfflineContent(
        uiState = uiStates,
        goToInstructions = {},
        hideInitialDialog = { viewModel.hideInitialDialog() },
    )
}

@Composable
fun RangesOfflineContent(
    uiState: RangesOfflineStates,
    goToInstructions: () -> Unit,
    hideInitialDialog: () -> Unit,
) {
    var parentWidth by remember { mutableIntStateOf(0) }

    if (uiState.confettiTrigger > 0) {
        key(uiState.confettiTrigger) {
            KonfettiView(
                modifier = Modifier.fillMaxSize(),
                parties = listOf(getKonfettiParty())
            )
        }
    }

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
            Column {
                Rounds(uiState.points)

                SpacerLarge()

                Box(
                    modifier = Modifier.fillMaxWidth().height(150.dp).background(RedLight),
                    contentAlignment = Alignment.Center
                ) {

                }

                SpacerLarge()

                Box(
                    modifier = Modifier.fillMaxWidth()
                        .height(50.dp)
                        .padding(horizontal = 8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(GrayHard)
                        .onSizeChanged { parentWidth = it.width },
                ) {
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
                    modifier = Modifier.weight(1f).background(RedLight)
                ) {
                    ShowAlpha(uiState.controllerEnabled) {
                        SwipeBox(modifier = Modifier.align(Alignment.BottomCenter))
                    }

                    RangeLimits(
                        leftRange = uiState.actualRangeLeft,
                        rightRange = uiState.actualRangeRight
                    )
                }

                ButtonCustom(
                    onClick = {
                        if (uiState.buttonEnabled) {
                            when (uiState.phase) {
                                SHOW_BULLSEYE -> {}
                                MOVE_ARROW -> {}
                                NEXT_ROUND -> {}
                                RESULTS -> {}
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
                    howToPlay = {}
                )
            }

            if (uiState.showResultDialog) {
                //                ResultDialog(
                //                            points = uiState.points,
                //                    blueName = uiState.blueName,
                //                    orangeName = uiState.orangeName,
                //                    exit = {},
                //                    replay = { replay() },
                //                    changeNames = {},
                //                )
            }
        }
    }
}

private fun Context.goToInstructions() = openBedRockActivity(NAV_INSTR_RANGES_OFFLINE)
