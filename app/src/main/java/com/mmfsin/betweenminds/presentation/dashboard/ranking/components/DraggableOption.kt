package com.mmfsin.betweenminds.presentation.dashboard.ranking.components

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mmfsin.betweenminds.presentation.core.components.MediumText
import com.mmfsin.betweenminds.presentation.core.theme.White
import com.mmfsin.betweenminds.presentation.core.theme.alphazet


@Composable
fun DraggableOption(
    text: String,
    index: Int,
    dragEnabled: Boolean,

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
        gravity = TextAlign.Center,
        fontFamily = alphazet,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
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
                        if (dragEnabled) {
                            currentOffset = Offset.Zero
                            currentTargetIndex = -1

                            updateDraggedIndex(index)
                            updateDragOffset(Offset.Zero)
                        }
                    },
                    onDrag = { change, amount ->
                        if (dragEnabled) {
                            change.consume()
                            currentOffset += amount
                            updateDragOffset(amount)

                            val source = sourceBounds[sourceBoundsIndex]
                            val position = source?.center?.plus(currentOffset)

                            currentTargetIndex = boxBounds.entries
                                .firstOrNull { (_, bounds) ->
                                    position != null && bounds.contains(position)
                                }?.key ?: -1

                            println("----> target: $currentTargetIndex")
                            updateTargetIndex(currentTargetIndex)
                        }
                    },
                    onDragEnd = {
                        if (dragEnabled) {
                            if (currentTargetIndex != -1) {
                                swapTexts()
                            }
                            updateDraggedIndex(-1)
                            updateDragOffset(Offset.Zero)
                        }
                    }
                )
            }
    )
}