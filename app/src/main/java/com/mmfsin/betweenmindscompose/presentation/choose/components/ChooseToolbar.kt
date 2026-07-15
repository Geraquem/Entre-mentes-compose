@file:OptIn(ExperimentalMaterial3Api::class)

package com.mmfsin.betweenmindscompose.presentation.choose.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmfsin.betweenmindscompose.R
import com.mmfsin.betweenmindscompose.presentation.core.components.MediumText
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerMini
import com.mmfsin.betweenmindscompose.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenmindscompose.presentation.core.theme.White
import com.mmfsin.betweenmindscompose.presentation.core.theme.kineks

@Preview
@Composable
fun ChooseToolbarPV() {
    ChooseToolbar({}, {})
}

@Composable
fun ChooseToolbar(goBack: () -> Unit, goToInstructions: () -> Unit) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Row(
                    modifier = Modifier.clickable(onClick = { goBack() }),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painterResource(R.drawable.ic_arrow_back), null,
                        tint = White
                    )
                    SpacerMini(horizontal = true)
                    MediumText(
                        text = R.string.back,
                        color = White,
                        fontFamily = kineks
                    )
                }
                Spacer(Modifier.weight(1f))

                MediumText(
                    text = R.string.how_to_play,
                    color = White,
                    fontFamily = kineks,
                    modifier = Modifier.clickable(onClick = { goToInstructions() }).padding(end = 16.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundBlack),
    )
}