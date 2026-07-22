package com.mmfsin.betweenmindscompose.presentation.dashboard.questions.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import com.mmfsin.betweenmindscompose.R
import kotlin.math.roundToInt

@Preview
@Composable
fun DraggablePV() {
    Draggable(
        1,
        {_,_->}
    )
}

@Composable
fun Draggable(
    round: Int,
    updatePercents: (phase: Int, percent: Int) -> Unit
) {
    val focusManager = LocalFocusManager.current

    var offsetX by remember { mutableFloatStateOf(0f) }
    var parentWidth by remember { mutableIntStateOf(0) }
    var opinionWidth by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .onSizeChanged {
                parentWidth = it.width
            }
    ) {
        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), 0) }
                .onSizeChanged {
                    opinionWidth = it.width
                }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = {
                            focusManager.clearFocus()
                        },
                        onDrag = { change, dragAmount ->

                            change.consume()

                            val maxOffset =
                                (parentWidth - opinionWidth).toFloat()

                            offsetX = (offsetX + dragAmount.x)
                                .coerceIn(0f, maxOffset)

                            val percent =
                                ((offsetX / maxOffset) * 100).toInt()

                            updatePercents(round, percent)
                        }
                    )
                }
        ) {

            // Flecha
            Image(
                painter = painterResource(R.drawable.ic_arrow),
                contentDescription = null,
                modifier = Modifier.align(Alignment.TopCenter)
            )

            // Tu OpinionView
        }
    }
}