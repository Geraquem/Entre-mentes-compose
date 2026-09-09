package com.mmfsin.betweenminds.presentation.dashboard.ranking.offline

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.domain.models.GameType.RANKING
import com.mmfsin.betweenminds.domain.models.RankingBox
import com.mmfsin.betweenminds.domain.models.RankingPhaseType.NEXT_ROUND
import com.mmfsin.betweenminds.domain.models.RankingPhaseType.ORDER_FIRST
import com.mmfsin.betweenminds.domain.models.RankingPhaseType.ORDER_SECOND
import com.mmfsin.betweenminds.domain.models.RankingPhaseType.RESULTS
import com.mmfsin.betweenminds.presentation.core.components.ButtonCustom
import com.mmfsin.betweenminds.presentation.core.components.CustomToolbar
import com.mmfsin.betweenminds.presentation.core.components.ErrorDialog
import com.mmfsin.betweenminds.presentation.core.components.MediumText
import com.mmfsin.betweenminds.presentation.core.components.SmallText
import com.mmfsin.betweenminds.presentation.core.components.SpacerLarge
import com.mmfsin.betweenminds.presentation.core.components.SpacerMini
import com.mmfsin.betweenminds.presentation.core.components.SpacerSmall
import com.mmfsin.betweenminds.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenminds.presentation.core.theme.White
import com.mmfsin.betweenminds.presentation.core.theme.alphazet
import com.mmfsin.betweenminds.presentation.core.theme.courier
import com.mmfsin.betweenminds.presentation.dashboard.common.ExitGameDialog
import com.mmfsin.betweenminds.presentation.dashboard.common.RoundCount
import com.mmfsin.betweenminds.presentation.dashboard.ranking.components.DraggableOption
import com.mmfsin.betweenminds.presentation.dashboard.ranking.components.RankingRounds
import com.mmfsin.betweenminds.presentation.dashboard.ranking.helper.checkBoxColor
import com.mmfsin.betweenminds.presentation.dashboard.ranking.offline.components.InitialOfflineRankingDialog
import com.mmfsin.betweenminds.utils.ShowAlpha
import com.mmfsin.betweenminds.utils.getKonfettiParty
import com.mmfsin.betweenminds.utils.shakeItem
import nl.dionsegijn.konfetti.compose.KonfettiView
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

@Preview
@Composable
fun RankingOfflineScreenPV() {
    RankingOfflineContent(
        uiStates = RankingOfflineStates(
            isLoading = true,
            showInitialDialog = false,
            showRoundView = false,
            actualRankingText = "El mejor superpoder",
            rankingBoxList = mutableListOf(RankingBox(0, "Item 1 adsf sd sdf gszñldf glfklñgkl ´k"), RankingBox(1, "Item 2")),
            actualRankings = mutableListOf("Desayuno asd as das df", "Comida asdas sa das ddfsdf sd  ds a da s a ad", "Merienda", "Cena"),
            actualRankingTopText = "Mejor",
            actualRankingBottomText = "Peor",

            showComparativeList = true,
            secondSortedList = mutableListOf(RankingBox(0, "Item 1"), RankingBox(1, "Item 2")),
        ),
        {}, {}, {}, { _, _ -> },
        {}, {}, {}, {},
    )
}

@Composable
fun RankingOfflineScreen(viewModel: RankingOfflineViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val activity = LocalActivity.current
    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()

    RankingOfflineContent(
        uiStates = uiStates,
        goBack = { activity?.finish() },
        goToInstructions = { /*context.goToInstructions()*/ },
        hideInitialDialog = { viewModel.hideInitialDialog() },
        swapTexts = { targetIndex, sourceIndex -> viewModel.swapTexts(targetIndex, sourceIndex) },
        readyOrderOne = { viewModel.readyOrderOne() },
        readyOrderTwo = { viewModel.readyOrderTwo() },
        handleNextRound = { viewModel.handleNextRound() },

        showExitDialog = { viewModel.showExitDialog(it) }
    )
}

@Composable
fun RankingOfflineContent(
    uiStates: RankingOfflineStates,
    goBack: () -> Unit,
    goToInstructions: () -> Unit,
    hideInitialDialog: () -> Unit,
    swapTexts: (Int, Int) -> Unit,
    readyOrderOne: () -> Unit,
    readyOrderTwo: () -> Unit,
    handleNextRound: () -> Unit,

    showExitDialog: (Boolean) -> Unit,
) {
    val lazyListState = rememberLazyListState()

    val reorderableState = rememberReorderableLazyListState(
        lazyListState = lazyListState
    ) { from, to ->
        uiStates.rankingBoxList.add(to.index, uiStates.rankingBoxList.removeAt(from.index))
    }

    var dragOffset by remember { mutableStateOf(Offset.Zero) }
    var dragOptionIndex by remember { mutableIntStateOf(-1) }

    val boxBounds = remember { mutableStateMapOf<Int, Rect>() }
    val sourceBounds = remember { mutableStateMapOf<Int, Rect>() }
    var targetIndex by remember { mutableIntStateOf(-1) }

    Scaffold(
        topBar = {
            CustomToolbar(
                goBack = { goBack() },
                goToInstructions = { /*goToInstructions()*/ }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
                .background(BackgroundBlack)
                .padding(innerPadding)
                .padding(horizontal = 18.dp)
        ) {
            Column {
                RankingRounds(uiStates.points)

                SpacerLarge()

                MediumText(
                    text = uiStates.actualRankingText,
                    color = White,
                    fontFamily = courier,
                    gravity = TextAlign.Center,
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                SpacerLarge()

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_vertical),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = White
                    )

                    SpacerMini(horizontal = true)

                    SmallText(
                        text = uiStates.actualRankingTopText,
                        color = White,
                        fontFamily = alphazet,
                    )
                }

                SpacerSmall()

                LazyColumn(
                    modifier = Modifier.shakeItem(uiStates.shakeTrigger),
                    state = lazyListState,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(
                        items = uiStates.rankingBoxList,
                        key = { _, item -> item.id }
                    ) { index, item ->

                        val originalPosition = uiStates.firstSortedList.indexOfFirst { it.text == item.text }

                        ReorderableItem(
                            state = reorderableState,
                            key = item.id
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .onGloballyPositioned { coordinates ->
                                        boxBounds[index] = coordinates.boundsInRoot()
                                    }
                                    .background(
                                        color = checkBoxColor(
                                            isEnabled = uiStates.showComparativeList,
                                            firstText = uiStates.firstSortedList[index].text,
                                            secondText = uiStates.secondSortedList[index].text
                                        ),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .padding(16.dp)
                                    .draggableHandle(enabled = uiStates.dragEnabled)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    MediumText(
                                        text = item.text,
                                        color = White,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.weight(1f)
                                    )

                                    if (uiStates.showComparativeList) {
                                        SpacerSmall(horizontal = true)
                                        MediumText(
                                            text = "${originalPosition + 1}º",
                                            color = White,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                SpacerSmall()

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_vertical),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp).graphicsLayer { scaleY = -1f },
                        tint = White
                    )

                    SpacerMini(horizontal = true)

                    SmallText(
                        text = uiStates.actualRankingBottomText,
                        color = White,
                        fontFamily = alphazet,
                    )
                }

                SpacerLarge()

                Column(Modifier.fillMaxWidth().weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(Modifier.weight(1f).fillMaxHeight(), contentAlignment = Alignment.Center) {
                            DraggableOption(
                                text = uiStates.actualRankings[0],
                                index = 0,
                                dragEnabled = uiStates.dragEnabled,
                                sourceBounds = sourceBounds,
                                sourceBoundsIndex = 0,
                                updateBounds = { bounds -> sourceBounds[0] = bounds },
                                boxBounds = boxBounds,
                                draggedIndex = dragOptionIndex,
                                updateDraggedIndex = { dragOptionIndex = it },
                                dragOffset = dragOffset,
                                updateDragOffset = { if (it == Offset.Zero) dragOffset = it else dragOffset += it },
                                updateTargetIndex = { targetIndex = it },
                                swapTexts = { swapTexts(targetIndex, 0) }
                            )
                        }
                        Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                            DraggableOption(
                                text = uiStates.actualRankings[1],
                                index = 1,
                                dragEnabled = uiStates.dragEnabled,
                                sourceBounds = sourceBounds,
                                sourceBoundsIndex = 1,
                                updateBounds = { bounds -> sourceBounds[1] = bounds },
                                boxBounds = boxBounds,
                                draggedIndex = dragOptionIndex,
                                updateDraggedIndex = { dragOptionIndex = it },
                                dragOffset = dragOffset,
                                updateDragOffset = { if (it == Offset.Zero) dragOffset = it else dragOffset += it },
                                updateTargetIndex = { targetIndex = it },
                                swapTexts = { swapTexts(targetIndex, 1) }
                            )
                        }
                    }

                    SpacerSmall()

                    Row(
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(Modifier.weight(1f).fillMaxHeight(), contentAlignment = Alignment.Center) {
                            DraggableOption(
                                text = uiStates.actualRankings[2],
                                index = 2,
                                dragEnabled = uiStates.dragEnabled,
                                sourceBounds = sourceBounds,
                                sourceBoundsIndex = 2,
                                updateBounds = { bounds -> sourceBounds[2] = bounds },
                                boxBounds = boxBounds,
                                draggedIndex = dragOptionIndex,
                                updateDraggedIndex = { dragOptionIndex = it },
                                dragOffset = dragOffset,
                                updateDragOffset = { if (it == Offset.Zero) dragOffset = it else dragOffset += it },
                                updateTargetIndex = { targetIndex = it },
                                swapTexts = { swapTexts(targetIndex, 2) }
                            )
                        }
                        Box(Modifier.weight(1f).fillMaxHeight(), contentAlignment = Alignment.Center) {
                            DraggableOption(
                                text = uiStates.actualRankings[3],
                                index = 3,
                                dragEnabled = uiStates.dragEnabled,
                                sourceBounds = sourceBounds,
                                sourceBoundsIndex = 3,
                                updateBounds = { bounds -> sourceBounds[3] = bounds },
                                boxBounds = boxBounds,
                                draggedIndex = dragOptionIndex,
                                updateDraggedIndex = { dragOptionIndex = it },
                                dragOffset = dragOffset,
                                updateDragOffset = { if (it == Offset.Zero) dragOffset = it else dragOffset += it },
                                updateTargetIndex = { targetIndex = it },
                                swapTexts = { swapTexts(targetIndex, 3) }
                            )
                        }
                    }
                }

                SpacerLarge()

                ButtonCustom(
                    onClick = {
                        if (uiStates.buttonEnabled) {
                            when (uiStates.phase) {
                                ORDER_FIRST -> readyOrderOne()
                                ORDER_SECOND -> readyOrderTwo()
                                NEXT_ROUND -> handleNextRound()
                                RESULTS -> {} //showResultDialog(true)
                            }
                        }
                    },
                    text = uiStates.buttonText,
                    modifier = Modifier.fillMaxWidth()
                )
                SpacerLarge()
            }

            if (uiStates.confettiTrigger > 0) {
                key(uiStates.confettiTrigger) {
                    KonfettiView(
                        modifier = Modifier.fillMaxSize(),
                        parties = listOf(getKonfettiParty(RANKING, uiStates.confettiTrigger))
                    )
                }
            }

            ShowAlpha(uiStates.showRoundView) { RoundCount(uiStates.roundCount) }

            if (uiStates.showInitialDialog) {
                InitialOfflineRankingDialog(
                    startGame = { hideInitialDialog() },
                    howToPlay = { goToInstructions() },
                    exit = { goBack() },
                    isLoading = uiStates.isLoading
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