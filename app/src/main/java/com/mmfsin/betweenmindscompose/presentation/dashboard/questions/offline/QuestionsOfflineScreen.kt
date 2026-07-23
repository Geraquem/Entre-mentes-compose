package com.mmfsin.betweenmindscompose.presentation.dashboard.questions.offline

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.betweenmindscompose.R
import com.mmfsin.betweenmindscompose.domain.models.QuestionPhaseType.FIRST_OPINION
import com.mmfsin.betweenmindscompose.domain.models.QuestionPhaseType.RESULTS
import com.mmfsin.betweenmindscompose.domain.models.QuestionPhaseType.SECOND_OPINION
import com.mmfsin.betweenmindscompose.presentation.choose.components.ChooseToolbar
import com.mmfsin.betweenmindscompose.presentation.core.components.ButtonCustom
import com.mmfsin.betweenmindscompose.presentation.core.components.MediumText
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerLarge
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerMedium
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerMini
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerSmall
import com.mmfsin.betweenmindscompose.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenmindscompose.presentation.core.theme.GrayHard
import com.mmfsin.betweenmindscompose.presentation.core.theme.RedHard
import com.mmfsin.betweenmindscompose.presentation.core.theme.RedMedium
import com.mmfsin.betweenmindscompose.presentation.core.theme.White
import com.mmfsin.betweenmindscompose.presentation.core.theme.alphazet
import com.mmfsin.betweenmindscompose.presentation.core.theme.courier
import com.mmfsin.betweenmindscompose.presentation.dashboard.common.RoundCount
import com.mmfsin.betweenmindscompose.presentation.dashboard.common.SwipeBox
import com.mmfsin.betweenmindscompose.presentation.dashboard.questions.components.People
import com.mmfsin.betweenmindscompose.utils.AnimateX
import com.mmfsin.betweenmindscompose.utils.ShowAlpha
import kotlin.math.roundToInt

@Preview
@Composable
fun QuestionsOfflinePV() {
    QuestionsOfflineContent(
        uiState = QuestionsOfflineStates(
            isLoading = false,
            showRoundView = false,

            curtainLeftPosition = -500f,
            curtainRightPosition = 500f,
            showWhiteIndicator = true
        ),
        {}, {}, {}, {},
        {}, {}, {},
        {},
    )
}

@Composable
fun QuestionsOfflineScreen(viewModel: QuestionsOfflineViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    QuestionsOfflineContent(
        uiState = uiState,
        setInitialOffsets = { viewModel.setInitialOffsetsX(it) },
        updateOffsetXWhite = { viewModel.updateOffsetXWhite(it) },
        updateOffsetXRed = { viewModel.updateOffsetXRed(it) },
        onBlueNameChange = { viewModel.onBlueNameChanged(it) },
        onOrangeNameChange = { viewModel.onOrangeNameChanged(it) },
        updateFirstOpinionPercents = { viewModel.updateFirstOpinionPercents(it) },
        updateSecondOpinionPercents = { viewModel.updateSecondOpinionPercents(it) },
        readyOpinionOne = { viewModel.readyOpinionOne() },
    )
}

@Composable
fun QuestionsOfflineContent(
    uiState: QuestionsOfflineStates,
    setInitialOffsets: (Float) -> Unit,
    updateOffsetXWhite: (Float) -> Unit,
    updateOffsetXRed: (Float) -> Unit,
    onBlueNameChange: (String) -> Unit,
    onOrangeNameChange: (String) -> Unit,
    updateFirstOpinionPercents: (Int) -> Unit,
    updateSecondOpinionPercents: (Int) -> Unit,

    readyOpinionOne: () -> Unit,
) {

    val focusManager = LocalFocusManager.current
    var dragOffsetXWhite by remember { mutableFloatStateOf(uiState.offsetXWhite) }
    var dragOffsetXRed by remember { mutableFloatStateOf(uiState.offsetXRed) }
    var parentWidth by remember { mutableIntStateOf(0) }
    val indicatorWidthPx = with(LocalDensity.current) { 10.dp.toPx() }
    val arrowPointerWidthPx = with(LocalDensity.current) { 24.dp.toPx() }

    val currentRoundType by rememberUpdatedState(uiState.phase)

    LaunchedEffect(parentWidth, indicatorWidthPx) {
        if (parentWidth > 0 && indicatorWidthPx > 0) {
            val initialOffset = (parentWidth - indicatorWidthPx) / 2f
            dragOffsetXWhite = initialOffset
            dragOffsetXRed = initialOffset
            setInitialOffsets(initialOffset)
        }
    }

    Scaffold(
        topBar = {
            ChooseToolbar(
                goBack = {},
                goToInstructions = {}
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
                MediumText(
                    text = stringResource(R.string.scoreboard_rounds),
                    fontFamily = alphazet,
                    color = White,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                SpacerMini()
                Box(Modifier.fillMaxWidth().height(40.dp).background(RedHard))

                SpacerLarge()

                Box(
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    MediumText(
                        text = "¿Quién es más amable cuando se trata de ser fiel a su equipo de fútbol a las 15 de la tarde un domingo?",
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
                    showFirstOpinion = uiState.firstOpinionVisible,
                    showSecondOpinion = uiState.secondOpinionVisible,
                    blueHandsUp = uiState.blueHandsUp,
                    orangeHandsUp = uiState.orangeHandsUp
                )

                SpacerMedium()

                Box {
                    /** WHITE ARROW INDICATOR */
                    Box(
                        modifier = Modifier
                            .offset { IntOffset((uiState.offsetXWhite + (indicatorWidthPx - arrowPointerWidthPx) / 2).roundToInt(), 0) }
                            .width(with(LocalDensity.current) { arrowPointerWidthPx.toDp() }),
                        contentAlignment = Alignment.Center
                    ) {
                        if (parentWidth > 0) {
                            ShowAlpha(uiState.showWhiteIndicator) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_triangle_pointing),
                                    contentDescription = null,
                                    tint = White
                                )
                            }
                        }
                    }

                    /** RED ARROW INDICATOR */
                    Box(
                        modifier = Modifier
                            .offset { IntOffset((uiState.offsetXRed + (indicatorWidthPx - arrowPointerWidthPx) / 2).roundToInt(), 0) }
                            .width(with(LocalDensity.current) { arrowPointerWidthPx.toDp() }),
                        contentAlignment = Alignment.Center
                    ) {
                        if (parentWidth > 0) {
                            ShowAlpha(uiState.showRedIndicator) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_triangle_pointing),
                                    contentDescription = null,
                                    tint = RedMedium
                                )
                            }
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .onSizeChanged { parentWidth = it.width }
                ) {

                    /** Fondo */
                    Box(Modifier.matchParentSize().background(GrayHard))

                    /** White Indicator */
                    Box(
                        modifier = Modifier
                            .offset { IntOffset(uiState.offsetXWhite.roundToInt(), 0) }
                            .width(with(LocalDensity.current) { indicatorWidthPx.toDp() })
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (parentWidth > 0) {
                            ShowAlpha(uiState.showWhiteIndicator) {
                                Box(modifier = Modifier.fillMaxSize().background(White))
                            }
                        }
                    }

                    /** Red Indicator */
                    Box(
                        modifier = Modifier
                            .offset { IntOffset(uiState.offsetXRed.roundToInt(), 0) }
                            .width(with(LocalDensity.current) { indicatorWidthPx.toDp() })
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (parentWidth > 0) {
                            ShowAlpha(uiState.showRedIndicator) {
                                Box(modifier = Modifier.fillMaxSize().background(RedMedium))
                            }
                        }
                    }

                    val halfWidth = with(LocalDensity.current) { (parentWidth / 2).toDp() }

                    /** Left curtain */
                    AnimateX(uiState.curtainLeftPosition) {
                        Box(
                            Modifier.width(halfWidth)
                                .fillMaxHeight()
                                .background(RedHard)
                        )
                    }

                    /** Right curtain */
                    AnimateX(uiState.curtainRightPosition) {
                        Box(
                            Modifier.offset(x = halfWidth)
                                .width(halfWidth)
                                .fillMaxHeight()
                                .background(RedHard)
                        )
                    }
                }

                Spacer(Modifier.weight(1f))

                SwipeBox()
                SpacerLarge()
                ButtonCustom(
                    onClick = {
                        when (uiState.phase) {
                            FIRST_OPINION -> {
                                //                                val initialOffset = (parentWidth - indicatorWidthPx) / 2f
                                //                                dragOffsetX = initialOffset
                                //                                updateOffsetX(initialOffset)
                                readyOpinionOne()
                            }

                            SECOND_OPINION -> {}
                            RESULTS -> {}
                        }
                    },
                    text = R.string.btn_hide,
                    modifier = Modifier.fillMaxWidth()
                )
                SpacerSmall()
            }

            ShowAlpha(uiState.showRoundView) { RoundCount(uiState.roundCount) }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 64.dp)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { focusManager.clearFocus() },
                            onDrag = { change, dragAmount ->
                                change.consume()

                                when (currentRoundType) {
                                    FIRST_OPINION -> {
                                        val maxOffset = (parentWidth - indicatorWidthPx)
                                        dragOffsetXWhite = (dragOffsetXWhite + dragAmount.x).coerceIn(0f, maxOffset)

                                        val percent = if (maxOffset > 0f) ((dragOffsetXWhite / maxOffset) * 100).toInt()
                                        else 0

                                        updateFirstOpinionPercents(percent)
                                        updateOffsetXWhite(dragOffsetXWhite)
                                    }

                                    SECOND_OPINION -> {
                                        val maxOffset = (parentWidth - indicatorWidthPx)
                                        dragOffsetXRed = (dragOffsetXRed + dragAmount.x).coerceIn(0f, maxOffset)

                                        val percent = if (maxOffset > 0f) ((dragOffsetXRed / maxOffset) * 100).toInt()
                                        else 0

                                        updateSecondOpinionPercents(percent)
                                        updateOffsetXRed(dragOffsetXRed)
                                    }

                                    RESULTS -> Unit
                                }
                            }
                        )
                    }
            )
        }
    }
}