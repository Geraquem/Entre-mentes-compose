package com.mmfsin.betweenmindscompose.presentation.dashboard.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmfsin.betweenmindscompose.R
import com.mmfsin.betweenmindscompose.presentation.core.components.MediumText
import com.mmfsin.betweenmindscompose.presentation.core.theme.White
import com.mmfsin.betweenmindscompose.presentation.core.theme.alphazet

@Preview
@Composable
fun SwipeBox() {
    Column() {
        MediumText(
            text = R.string.controller_text,
            gravity = TextAlign.Center,
            color = White,
            fontFamily = alphazet
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
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
        }
    }
}