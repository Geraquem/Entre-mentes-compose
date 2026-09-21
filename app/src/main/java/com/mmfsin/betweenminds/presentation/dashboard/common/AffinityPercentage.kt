package com.mmfsin.betweenminds.presentation.dashboard.common

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.domain.models.GameType
import com.mmfsin.betweenminds.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenminds.presentation.core.theme.GrayHard
import com.mmfsin.betweenminds.presentation.core.theme.barlow
import com.mmfsin.betweenminds.utils.getAffinity
import com.mmfsin.betweenminds.utils.getPercentageColor

@Preview
@Composable
fun AffinityPercentagePV() {
    AffinityPercentage(GameType.QUESTIONS, 30)
}

@Composable
fun AffinityPercentage(
    type: GameType,
    points: Int,
    isOnline: Boolean = false
) {

    val affinity = getAffinity(type, points, isOnline)

    val animatedValue = remember { Animatable(0f) }

    LaunchedEffect(affinity.first) {
        animatedValue.animateTo(
            targetValue = affinity.first,
            animationSpec = tween(
                durationMillis = 2000,
                easing = FastOutSlowInEasing
            )
        )
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(180.dp)
    ) {

        CircularProgressIndicator(
            progress = { animatedValue.value },
            modifier = Modifier.fillMaxSize(),
            color = getPercentageColor(affinity.first),
            trackColor = GrayHard,
            strokeWidth = 24.dp,
            strokeCap = StrokeCap.Butt
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "${affinity.second}%",
                color = BackgroundBlack,
                fontSize = 30.sp,
                fontFamily = barlow,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = stringResource(R.string.endgame_affinity).uppercase(),
                color = BackgroundBlack,
            )
        }
    }
}