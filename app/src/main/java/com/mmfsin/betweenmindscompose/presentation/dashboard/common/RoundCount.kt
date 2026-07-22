package com.mmfsin.betweenmindscompose.presentation.dashboard.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mmfsin.betweenmindscompose.R
import com.mmfsin.betweenmindscompose.presentation.core.components.BigText
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerSmall
import com.mmfsin.betweenmindscompose.presentation.core.theme.Black
import com.mmfsin.betweenmindscompose.presentation.core.theme.BlueTransparent
import com.mmfsin.betweenmindscompose.presentation.core.theme.White
import com.mmfsin.betweenmindscompose.presentation.core.theme.manaspace

@Preview
@Composable
fun RoundCountPV() {
    RoundCount(2)
}

@Composable
fun RoundCount(round: Int) {
    Column(
        modifier = Modifier.fillMaxSize()
            .clip(RoundedCornerShape(16.dp))
            .background(BlueTransparent),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        BigText(
            text = stringResource(R.string.round),
            allCaps = true,
            fontSize = 42.sp,
            fontFamily = manaspace,
            color = White
        )

        SpacerSmall()

        BigText(
            text = round.toString(),
            fontSize = 42.sp,
            fontFamily = manaspace,
            color = White,
        )
    }
}