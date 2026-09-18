package com.mmfsin.betweenminds.presentation.dashboard.ranking.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.presentation.core.components.BigText
import com.mmfsin.betweenminds.presentation.core.components.SpacerLarge
import com.mmfsin.betweenminds.presentation.core.theme.Black
import com.mmfsin.betweenminds.presentation.core.theme.White
import com.mmfsin.betweenminds.presentation.core.theme.alphazet
import kotlinx.coroutines.delay

@Preview(showBackground = true)
@Composable
fun BetweenPhasesDialogPV() {
    BetweenPhasesDialog({})
}

@Composable
fun BetweenPhasesDialog(onEnd: () -> Unit) {

    val bookOffset = remember { Animatable(-1f) }
    var boxWidth by remember { mutableIntStateOf(0) }

    LaunchedEffect(boxWidth) {
        if (boxWidth > 0) {
            bookOffset.snapTo(-1f)
            delay(250)
            bookOffset.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 750,
                    easing = LinearEasing
                )
            )

            delay(500)
            onEnd()
        }
    }

    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.9f)
                .height(400.dp)
                .background(Black, shape = RoundedCornerShape(16.dp))
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            BigText(
                "Cambio de mano",
                color = White,
                allCaps = true,
                fontFamily = alphazet
            )

            SpacerLarge()

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painterResource(R.drawable.ic_human_down), null,
                    tint = White,
                    modifier = Modifier.size(64.dp)
                )

                Box(
                    modifier = Modifier.weight(1f)
                        .onSizeChanged {
                            boxWidth = it.width
                        }) {
                    Icon(
                        painterResource(R.drawable.ic_smartphone), null,
                        tint = White,
                        modifier = Modifier
                            .size(70.dp)
                            .graphicsLayer {
                                val distance = boxWidth - 56.dp.toPx()
                                translationX = (bookOffset.value + 1f) / 2f * distance
                            }
                    )
                }

                Icon(
                    painterResource(R.drawable.ic_human_down), null,
                    tint = White,
                    modifier = Modifier.size(64.dp)
                )
            }
        }
    }
}