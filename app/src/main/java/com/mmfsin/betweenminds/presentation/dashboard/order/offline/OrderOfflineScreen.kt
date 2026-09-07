package com.mmfsin.betweenminds.presentation.dashboard.order.offline

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.betweenminds.presentation.core.components.BigText
import com.mmfsin.betweenminds.presentation.core.components.CustomToolbar
import com.mmfsin.betweenminds.presentation.core.components.SpacerMedium
import com.mmfsin.betweenminds.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenminds.presentation.core.theme.BlueMedium
import com.mmfsin.betweenminds.presentation.core.theme.GrayHard
import com.mmfsin.betweenminds.presentation.core.theme.GrayMedium
import com.mmfsin.betweenminds.presentation.core.theme.GreenMedium
import com.mmfsin.betweenminds.presentation.core.theme.OrangeMedium
import com.mmfsin.betweenminds.presentation.core.theme.RedMedium
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

    var boxCount = 4

    val texts = remember {
        mutableStateListOf(
            "Desayuno",
            "Comida",
            "Merienda",
            "Cena"
        )
    }

    val colors = remember {
        mutableStateListOf(
            OrangeMedium,
            RedMedium,
            BlueMedium,
            GreenMedium
        )
    }


    var dragOffset by remember { mutableStateOf(Offset.Zero) }
    var draggedIndex by remember { mutableIntStateOf(-1) }
    var draggedPosition by remember { mutableStateOf(Offset.Zero) }

    val boxBounds = remember { mutableStateMapOf<Int, Rect>() }
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
                repeat(boxCount) { i ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        BigText(
                            text = "${i + 1}º",
                            color = White,
                            fontWeight = FontWeight.SemiBold
                        )

                        SpacerMedium(horizontal = true)

                        Box(
                            modifier = Modifier
                                .onGloballyPositioned { coordinates -> boxBounds[i] = coordinates.boundsInRoot() }
                                .zIndex(if (draggedIndex == i) 1f else 0f)
                                .background(GrayHard, shape = RoundedCornerShape(12.dp))
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            BigText(
                                text = texts[i],
                                color = White,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .onGloballyPositioned { coordinates ->
                                        if (draggedIndex == i) {
                                            draggedPosition = coordinates.positionInRoot()
                                        }
                                    }
                                    .alpha(if (draggedIndex == -1 || draggedIndex == i) 1f else 0.25f)
                                    .graphicsLayer {
                                        translationX = if (draggedIndex == i) dragOffset.x else 0f
                                        translationY = if (draggedIndex == i) dragOffset.y else 0f
                                    }
                                    .pointerInput(i) {
                                        detectDragGestures(
                                            onDragStart = {
                                                draggedIndex = i
                                                dragOffset = Offset.Zero
                                            },
                                            onDrag = { change, amount ->
                                                change.consume()
                                                dragOffset += amount

                                                val draggedBox = boxBounds[draggedIndex]

                                                val rootPosition = draggedBox?.let { it.center + dragOffset }

                                                targetIndex = boxBounds.entries.firstOrNull { (index, bounds) ->
                                                    index != draggedIndex &&
                                                            rootPosition != null &&
                                                            bounds.contains(rootPosition)
                                                }?.key ?: -1
                                            },
                                            onDragEnd = {
                                                if (targetIndex != -1 && targetIndex != draggedIndex) {
                                                    val temp = texts[draggedIndex]
                                                    texts[draggedIndex] = texts[targetIndex]
                                                    texts[targetIndex] = temp
                                                }
                                                targetIndex = -1
                                                draggedIndex = -1
                                                dragOffset = Offset.Zero
                                            },
                                            onDragCancel = {
                                                draggedIndex = -1
                                                dragOffset = Offset.Zero
                                            }
                                        )
                                    },
                            )
                        }
                    }
                    SpacerMedium()
                }
            }

        }
        if (draggedIndex != -1) {
            BigText(
                text = texts[draggedIndex],
                color = White,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .graphicsLayer {
                        translationX = draggedPosition.x + dragOffset.x
                        translationY = draggedPosition.y + dragOffset.y
                    }.zIndex(100f)
            )
        }
    }
}
