package com.mmfsin.betweenminds.presentation.dashboard.order.offline

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.betweenminds.presentation.core.components.BigText
import com.mmfsin.betweenminds.presentation.core.components.CustomToolbar
import com.mmfsin.betweenminds.presentation.core.components.MediumText
import com.mmfsin.betweenminds.presentation.core.components.SpacerMedium
import com.mmfsin.betweenminds.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenminds.presentation.core.theme.GrayHard
import com.mmfsin.betweenminds.presentation.core.theme.White

@Preview
@Composable
fun OrderOfflineScreenPV() {
    OrderOfflineContent(
        uiStates = OrderOfflineStates(

        ),
        {},
    )
}

@Composable
fun OrderOfflineScreen(viewModel: OrderOfflineViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val activity = LocalActivity.current
    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()

    OrderOfflineContent(
        uiStates = uiStates,
        goBack = { activity?.finish() }
    )
}

@Composable
fun OrderOfflineContent(
    uiStates: OrderOfflineStates,
    goBack: () -> Unit
) {

    val rankingTexts = remember { mutableStateListOf("", "", "", "") }
    val optionTexts = remember { mutableStateListOf("Desayuno", "Comida", "Merienda", "Cena") }

    var dragOffset by remember { mutableStateOf(Offset.Zero) }
    var dragBoxIndex by remember { mutableIntStateOf(-1) }
    var dragOptionIndex by remember { mutableIntStateOf(-1) }
    var isDragging by remember { mutableStateOf(false) }


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
                .padding(vertical = 12.dp, horizontal = 18.dp)
        ) {
            Column {
                repeat(4) { i ->
                    Row(
                        modifier = Modifier
                            .zIndex(if (dragBoxIndex == i) 10f else 0f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BigText(
                            text = "${i + 1}º",
                            color = White,
                            fontWeight = FontWeight.SemiBold
                        )

                        SpacerMedium(horizontal = true)

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .onGloballyPositioned { coordinates -> boxBounds[i] = coordinates.boundsInRoot() }
                                .alpha(if (!isDragging || dragBoxIndex == i) 1f else 0.25f)
                                .graphicsLayer {
                                    translationX = if (dragBoxIndex == i) dragOffset.x else 0f
                                    translationY = if (dragBoxIndex == i) dragOffset.y else 0f
                                }
                                .background(GrayHard, shape = RoundedCornerShape(12.dp))
                                .padding(16.dp)
                                .pointerInput(i) {
                                    detectDragGestures(
                                        onDragStart = {
                                            dragBoxIndex = i
                                            isDragging = true
                                            dragOffset = Offset.Zero
                                        },
                                        onDrag = { change, amount ->
                                            if (rankingTexts[i].isNotEmpty()) {
                                                change.consume()
                                                dragOffset += amount

                                                val source = boxBounds[i]

                                                val position = source?.center?.plus(dragOffset)

                                                targetIndex = boxBounds.entries
                                                    .firstOrNull { (index, bounds) ->
                                                        index != i &&
                                                                position != null &&
                                                                bounds.contains(position)
                                                    }?.key ?: -1
                                            }
                                        },
                                        onDragEnd = {
                                            if (targetIndex != -1) {
                                                val temp = rankingTexts[dragBoxIndex]
                                                rankingTexts[dragBoxIndex] = rankingTexts[targetIndex]
                                                rankingTexts[targetIndex] = temp
                                            }

                                            isDragging = false
                                            dragBoxIndex = -1
                                            targetIndex = -1
                                            dragOffset = Offset.Zero
                                        }
                                    )
                                },
                        ) {
                            MediumText(
                                text = rankingTexts[i],
                                color = White,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                    SpacerMedium()
                }

                Spacer(Modifier.weight(1f))

                Column(Modifier.fillMaxWidth()) {
                    Row() {
                        Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                            DraggableOption(
                                text = optionTexts[0],
                                index = 0,
                                sourceBounds = sourceBounds,
                                sourceBoundsIndex = 0,
                                updateBounds = { bounds -> sourceBounds[0] = bounds },
                                boxBounds = boxBounds,
                                draggedIndex = dragOptionIndex,
                                updateDraggedIndex = { dragOptionIndex = it },
                                dragOffset = dragOffset,
                                updateDragOffset = { if (it == Offset.Zero) dragOffset = it else dragOffset += it },
                                updateTargetIndex = { targetIndex = it },
                                swapTexts = {
                                    rankingTexts[targetIndex] = optionTexts[0]
                                    optionTexts[0] = ""
                                }
                            )
                        }
                        Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                            DraggableOption(
                                text = optionTexts[1],
                                index = 1,
                                sourceBounds = sourceBounds,
                                sourceBoundsIndex = 1,
                                updateBounds = { bounds -> sourceBounds[1] = bounds },
                                boxBounds = boxBounds,
                                draggedIndex = dragOptionIndex,
                                updateDraggedIndex = { dragOptionIndex = it },
                                dragOffset = dragOffset,
                                updateDragOffset = { if (it == Offset.Zero) dragOffset = it else dragOffset += it },
                                updateTargetIndex = { targetIndex = it },
                                swapTexts = {
                                    rankingTexts[targetIndex] = optionTexts[1]
                                    optionTexts[1] = ""
                                }
                            )
                        }
                    }
                    Row() {
                        Box(Modifier.weight(1f).height(50.dp), contentAlignment = Alignment.Center) {
                            BigText("Hola 3", color = White)
                        }
                        Box(Modifier.weight(1f).height(50.dp), contentAlignment = Alignment.Center) {
                            BigText("Hola 4", color = White)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DraggableOption(
    text: String,
    index: Int,

    sourceBounds: SnapshotStateMap<Int, Rect>,
    sourceBoundsIndex: Int,
    updateBounds: (Rect) -> Unit,

    boxBounds: SnapshotStateMap<Int, Rect>,

    draggedIndex: Int,
    updateDraggedIndex: (Int) -> Unit,

    dragOffset: Offset,
    updateDragOffset: (Offset) -> Unit,

    updateTargetIndex: (Int) -> Unit,

    swapTexts: () -> Unit,
) {
    MediumText(
        text,
        color = White,
        modifier = Modifier
            .onGloballyPositioned { coordinates ->
                updateBounds(coordinates.boundsInRoot())
            }.graphicsLayer {
                translationX = if (draggedIndex == index) dragOffset.x else 0f
                translationY = if (draggedIndex == index) dragOffset.y else 0f
            }
            .pointerInput(index) {
                var currentOffset = Offset.Zero
                var currentTargetIndex = -1

                detectDragGestures(
                    onDragStart = {
                        currentOffset = Offset.Zero
                        currentTargetIndex = -1

                        updateDraggedIndex(index)
                        updateDragOffset(Offset.Zero)
                    },
                    onDrag = { change, amount ->
                        change.consume()
                        currentOffset += amount
                        updateDragOffset(amount)

                        val source = sourceBounds[sourceBoundsIndex]
                        val position = source?.center?.plus(currentOffset)

                        currentTargetIndex = boxBounds.entries
                            .firstOrNull { (_, bounds) ->
                                position != null && bounds.contains(position)
                            }?.key ?: -1

                        updateTargetIndex(currentTargetIndex)
                    },
                    onDragEnd = {
                        if (currentTargetIndex != -1) {
                            swapTexts()
                        }
                        updateDraggedIndex(-1)
                        updateDragOffset(Offset.Zero)
                    }
                )
            }
    )
}