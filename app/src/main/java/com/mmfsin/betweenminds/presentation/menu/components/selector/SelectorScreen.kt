@file:OptIn(ExperimentalMaterial3Api::class)

package com.mmfsin.betweenminds.presentation.menu.components.selector

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.domain.models.GameType
import com.mmfsin.betweenminds.domain.models.GameType.QUESTIONS
import com.mmfsin.betweenminds.domain.models.GameType.RANGES
import com.mmfsin.betweenminds.domain.models.GameType.RANKING
import com.mmfsin.betweenminds.presentation.core.components.BigText
import com.mmfsin.betweenminds.presentation.core.components.SpacerCustom
import com.mmfsin.betweenminds.presentation.core.components.SpacerSmall
import com.mmfsin.betweenminds.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenminds.presentation.core.theme.GrayHard
import com.mmfsin.betweenminds.presentation.core.theme.GrayLight
import com.mmfsin.betweenminds.presentation.core.theme.GrayMedium
import com.mmfsin.betweenminds.presentation.core.theme.White
import com.mmfsin.betweenminds.presentation.core.theme.alphazet
import kotlin.math.absoluteValue

@Preview
@Composable
fun SelectorScreenPV() {
    SelectorScreen(
        {}, {}, {}, {},
        {}, {}, {},
    )
}

@Composable
fun SelectorScreen(
    onDismiss: () -> Unit,
    questionsInstructions: () -> Unit,
    questions: () -> Unit,
    ranges: () -> Unit,
    rangesInstructions: () -> Unit,
    rankings: () -> Unit,
    rankingsInstructions: () -> Unit,
) {
    val modes = GameType.entries

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { modes.size }
    )

    Box(
        Modifier.fillMaxSize()
            .alpha(0.9f)
            .background(BackgroundBlack)
    )

    Row(Modifier.fillMaxWidth()) {
        Spacer(Modifier.weight(1f))
        Box(
            Modifier
                .padding(32.dp)
                .background(color = GrayMedium, shape = CircleShape)
        ) {
            IconButton(onClick = { onDismiss() }) {
                Icon(
                    painterResource(R.drawable.ic_cross), null,
                    tint = BackgroundBlack,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.weight(1f))

        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 52.dp),
            pageSpacing = 0.dp,
            modifier = Modifier.fillMaxWidth()
        ) { page ->

            val pageOffset = (
                    (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

            val scale = lerp(
                start = 0.85f,
                stop = 1f,
                fraction = 1f - pageOffset.coerceIn(0f, 1f)
            )

            val mode = modes[page]

            GameModeCard(
                modifier = Modifier.graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                },
                mode = mode,
                onClick = {/* onModeSelected(mode) */ },
            )
        }

        SpacerCustom(32.dp)

        StretchPagerIndicator(
            pagerState = pagerState,
            pageCount = modes.size
        )

        Spacer(Modifier.weight(1f))
    }
}

@Composable
fun GameModeCard(
    modifier: Modifier,
    mode: GameType,
    onClick: () -> Unit,
) {
    val title = when (mode) {
        QUESTIONS -> R.string.selector_questions
        RANGES -> R.string.selector_ranges
        RANKING -> R.string.selector_ranking
    }

    val color = when (mode) {
        QUESTIONS, RANKING -> GrayLight
        RANGES -> GrayMedium
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        BigText(
            text = title,
            color = White,
            allCaps = true,
            fontFamily = alphazet,
            fontWeight = FontWeight.SemiBold,
            fontSize = 32.sp
        )

        SpacerSmall()

        Card(
            modifier = modifier.fillMaxWidth()
                .height(450.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = color),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
        ) {
            when (mode) {
                QUESTIONS -> CardQuestions()
                RANGES -> CardRanges()
                RANKING -> CardRankings()
            }
        }
    }
}

@Composable
fun StretchPagerIndicator(
    pagerState: PagerState,
    pageCount: Int
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        repeat(pageCount) { index ->

            val pageOffset = ((pagerState.currentPage - index) + pagerState.currentPageOffsetFraction).absoluteValue

            val progress = 1f - pageOffset.coerceIn(0f, 1f)

            val width = lerp(
                start = 8.dp,
                stop = 16.dp,
                fraction = progress
            )

            Box(
                modifier = Modifier
                    .height(8.dp)
                    .width(width)
                    .clip(CircleShape)
                    .background(
                        if (progress > 0.5f) White
                        else GrayHard
                    )
            )
        }
    }
}