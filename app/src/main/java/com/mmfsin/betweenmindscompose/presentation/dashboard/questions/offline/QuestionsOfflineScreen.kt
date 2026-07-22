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
        ),
        {}, {}, {}, {}
    )
}

@Composable
fun QuestionsOfflineScreen(viewModel: QuestionsOfflineViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    QuestionsOfflineContent(
        uiState = uiState,
        updateOffsetX = { viewModel.updateOffsetX(it) },
        onBlueNameChange = { viewModel.onBlueNameChanged(it) },
        onOrangeNameChange = { viewModel.onOrangeNameChanged(it) },
        updateFirstOpinionPercents = { viewModel.updateFirstOpinionPercents(it) }
    )
}

@Composable
fun QuestionsOfflineContent(
    uiState: QuestionsOfflineStates,
    updateOffsetX: (Float) -> Unit,
    onBlueNameChange: (String) -> Unit,
    onOrangeNameChange: (String) -> Unit,
    updateFirstOpinionPercents: (Int) -> Unit
) {

    val focusManager = LocalFocusManager.current
    var offsetX by remember { mutableFloatStateOf(0f) }
    var parentWidth by remember { mutableIntStateOf(0) }
    val indicatorWidthPx = with(LocalDensity.current) { 10.dp.toPx() }
    val arrowPointerWidthPx = with(LocalDensity.current) { 24.dp.toPx() }

    LaunchedEffect(parentWidth, indicatorWidthPx) {
        if (parentWidth > 0 && indicatorWidthPx > 0) {
            offsetX = (parentWidth - indicatorWidthPx) / 2f
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
                    showSecondOpinion = uiState.secondOpinionVisible
                )

                SpacerMedium()

                Box(
                    modifier = Modifier
                        .offset { IntOffset((offsetX + (indicatorWidthPx - arrowPointerWidthPx) / 2).roundToInt(), 0) }
                        .width(with(LocalDensity.current) { arrowPointerWidthPx.toDp() }),
                    contentAlignment = Alignment.Center
                ) {
                    if (parentWidth > 0) {
                        ShowAlpha(uiState.arrowPointerVisible) {
                            Icon(
                                painter = painterResource(R.drawable.ic_triangle_pointing),
                                contentDescription = null,
                                tint = White
                            )
                        }
                    }
                }
                Box {
                    Box(
                        modifier = Modifier.fillMaxWidth().height(50.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(GrayHard)
                            .onSizeChanged { parentWidth = it.width }
                    ) {
                        Box(
                            modifier = Modifier
                                .offset { IntOffset(x = offsetX.roundToInt(), y = 0) }
                                .width(with(LocalDensity.current) { indicatorWidthPx.toDp() })
                                .fillMaxHeight()
                                .background(White)
                        )
                    }
                    AnimateX(uiState.curtainLeftPosition) {
                        Box(
                            Modifier.width(300.dp).height(50.dp)
                                .clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp))
                                .background(RedHard)
                        )

                    }
                }

                Spacer(Modifier.weight(1f))

                SwipeBox()
                SpacerLarge()
                ButtonCustom(
                    onClick = {},
                    text = R.string.btn_hide,
                    modifier = Modifier.fillMaxWidth()
                )
                SpacerSmall()
            }

            ShowAlpha(uiState.showRoundView) { RoundCount(uiState.roundCount) }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { focusManager.clearFocus() },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                val maxOffset = (parentWidth - indicatorWidthPx)
                                offsetX = (offsetX + dragAmount.x).coerceIn(0f, maxOffset)

                                val percent = if (maxOffset > 0f) {
                                    ((offsetX / maxOffset) * 100).toInt()
                                } else 0

                                updateFirstOpinionPercents(percent)
                            }
                        )
                    }
            )
        }
    }
}