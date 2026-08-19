package com.mmfsin.betweenmindscompose.utils

import android.content.Context
import android.content.Intent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.mmfsin.betweenmindscompose.presentation.bedrock.BedRockActivity
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit

fun Context.openBedRockActivity(navGraph: String, strArgs: String? = null) {
    val intent = Intent(this, BedRockActivity::class.java)
    intent.putExtra(BEDROCK_NAV_GRAPH, navGraph)
    strArgs?.let { intent.putExtra(BEDROCK_STR_ARGS, strArgs) }
    startActivity(intent)
}

@Composable
fun AnimateX(
    targetX: Float,
    duration: Int = 1000,
    onEnd: () -> Unit = {},
    content: @Composable () -> Unit
) {
    val animatedX by animateDpAsState(
        targetValue = targetX.dp,
        animationSpec = tween(duration),
        finishedListener = { onEnd() }
    )

    Box(modifier = Modifier.offset(x = animatedX)) { content() }
}

@Composable
fun AnimateY(
    targetY: Float,
    duration: Int = 1000,
    onEnd: () -> Unit = {},
    content: @Composable () -> Unit
) {
    val animatedY by animateDpAsState(
        targetValue = targetY.dp,
        animationSpec = tween(duration),
        finishedListener = { onEnd() }
    )

    Box(modifier = Modifier.offset(y = animatedY)) { content() }
}

@Composable
fun ShowAlpha(
    visibleTrigger: Boolean,
    duration: Int = 1000,
    onEnd: () -> Unit = {},
    content: @Composable () -> Unit
) {
    val alpha by animateFloatAsState(
        targetValue = if (visibleTrigger) 1f else 0f,
        animationSpec = tween(duration),
        finishedListener = { if (visibleTrigger) onEnd() }
    )

    Box(modifier = Modifier.alpha(alpha)) { content() }
}

fun getKonfettiParty() = Party(
    speed = 0f,
    maxSpeed = 30f,
    damping = 0.9f,
    spread = 360,
    colors = listOf(
        0xfce18a,
        0xff726d,
        0xf4306d,
        0xb48def
    ),
    emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100),
    position = Position.Relative(0.5, 0.3)
)