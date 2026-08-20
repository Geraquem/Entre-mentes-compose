package com.mmfsin.betweenminds.presentation.dashboard.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit

@Composable
fun Confetti(
    modifier: Modifier = Modifier
) {
//    AndroidView(
//        modifier = modifier.fillMaxSize(),
//        factory = { context -> KonfettiView(context) },
//        update = { confettiView -> confettiView.start(getConfettiParty()) }
//    )
}

fun getConfettiParty() = Party(
    speed = 0f,
    maxSpeed = 30f,
    damping = 0.9f,
    spread = 360,
    colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
    emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100),
    position = Position.Relative(0.5, 0.3)
)