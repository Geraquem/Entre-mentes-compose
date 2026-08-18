@file:OptIn(ExperimentalMaterial3Api::class)

package com.mmfsin.betweenmindscompose.presentation.instructions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmfsin.betweenmindscompose.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenmindscompose.presentation.core.theme.GrayHard
import com.mmfsin.betweenmindscompose.presentation.core.theme.RedHard
import com.mmfsin.betweenmindscompose.presentation.core.theme.RedLight
import com.mmfsin.betweenmindscompose.presentation.core.theme.Transparent
import com.mmfsin.betweenmindscompose.presentation.core.theme.White

@Preview
@Composable
fun InstructionsScreenPV() {
    InstructionsScreen()
}

@Composable
fun InstructionsScreen() {
    var value by remember { mutableFloatStateOf(50f) }
    var range by remember { mutableStateOf(1f..100f) }

    Box(
        modifier = Modifier.fillMaxSize().background(BackgroundBlack).padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column() {

            Slider(
                modifier = Modifier.fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(GrayHard),
                value = value,
                onValueChange = { value = it },
                valueRange = 0f..100f,
                thumb = {
                    Box(
                        modifier = Modifier
                            .width(10.dp)
                            .fillMaxHeight()
                            .background(White)
                    )
                },
                colors = SliderDefaults.colors(
                    thumbColor = White,
                    activeTrackColor = Transparent,
                    inactiveTrackColor = Transparent
                ),
            )

            Slider(
                modifier = Modifier.height(300.dp),
                value = value,
                onValueChange = { value = it },
                valueRange = 0f..100f,
                track = { sliderState ->
                    SliderDefaults.Track(
                        sliderState = sliderState,
                        modifier = Modifier.fillMaxHeight(),
                        colors = SliderDefaults.colors(
                            activeTrackColor = RedHard,
                            inactiveTrackColor = RedLight
                        )
                    )
                },
                colors = SliderDefaults.colors(thumbColor = White),
            )
        }
    }
}