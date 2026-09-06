package com.mmfsin.betweenminds.presentation.dashboard.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.presentation.core.components.MediumText
import com.mmfsin.betweenminds.presentation.core.theme.White
import com.mmfsin.betweenminds.presentation.core.theme.alphazet

@Preview
@Composable
fun SwipeBox(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.alpha(0.75f).padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MediumText(
            text = R.string.controller_text,
            gravity = TextAlign.Center,
            color = White,
            fontFamily = alphazet
        )
        /*
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painterResource(R.drawable.ic_swipe), null,
                modifier = Modifier.size(42.dp).graphicsLayer {
                    scaleX = -1f
                },
                tint = White
            )
            Icon(
                painterResource(R.drawable.ic_swipe), null,
                modifier = Modifier.size(42.dp),
                tint = White
            )
        }*/
    }
}