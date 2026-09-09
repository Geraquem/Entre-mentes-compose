package com.mmfsin.betweenminds.utils

import android.content.Context
import android.content.Intent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.mmfsin.betweenminds.domain.models.GameType
import com.mmfsin.betweenminds.domain.models.GameType.QUESTIONS
import com.mmfsin.betweenminds.domain.models.GameType.RANGES
import com.mmfsin.betweenminds.domain.models.RankingBox
import com.mmfsin.betweenminds.presentation.bedrock.BedRockActivity
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

fun Context.openBedRockActivity(navGraph: String, strArgs: String? = null, boolArgs: Boolean? = null) {
    val intent = Intent(this, BedRockActivity::class.java)
    intent.putExtra(BEDROCK_NAV_GRAPH, navGraph)
    strArgs?.let { intent.putExtra(BEDROCK_STR_ARGS, strArgs) }
    boolArgs?.let { intent.putExtra(BEDROCK_BOOL_ARGS, boolArgs) }
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

fun getKonfettiParty(gameType: GameType, points: Int): Party {
    val size = when (gameType) {
        QUESTIONS -> 100
        RANGES -> {
            when (points) {
                1 -> 10
                2 -> 20
                5 -> 100
                else -> 0
            }
        }
    }

    return Party(
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
        emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(size),
        position = Position.Relative(0.5, 0.3)
    )
}

@Composable
fun Modifier.shakeItem(shake: Boolean): Modifier {
    val offsetX = remember { Animatable(0f) }

    LaunchedEffect(shake) {
        if (shake) {
            offsetX.animateTo(
                targetValue = 0f,
                animationSpec = keyframes {
                    durationMillis = 600
                    -50f at 50
                    50f at 100
                    -45f at 150
                    40f at 200
                    -30f at 250
                    30f at 300
                    -20f at 400
                    20f at 450
                    -10f at 500
                    10f at 550
                    0f at 600
                }
            )
        }
    }

    return this.offset {
        IntOffset(offsetX.value.roundToInt(), 0)
    }
}