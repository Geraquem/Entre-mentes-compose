@file:OptIn(ExperimentalMaterial3Api::class)

package com.mmfsin.betweenminds.presentation.instructions.questions.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmfsin.betweenminds.presentation.core.components.SpacerSmall
import com.mmfsin.betweenminds.presentation.core.theme.GrayHard
import com.mmfsin.betweenminds.presentation.core.theme.RedMedium
import com.mmfsin.betweenminds.presentation.core.theme.Transparent
import com.mmfsin.betweenminds.presentation.core.theme.White

@Preview
@Composable
fun PhaseSeparatorPV() {
    Column {
        InstrSlider(20f, White)
        SpacerSmall()
        InstrSlider2(30f, 25f)
    }
}

@Composable
fun InstrSlider(value: Float, color: Color) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(GrayHard)
    ) {

        Slider(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = { },
            valueRange = 0f..100f,
            enabled = false,
            thumb = {
                Box(
                    modifier = Modifier
                        .width(10.dp)
                        .fillMaxHeight()
                        .background(color)
                )
            },
            colors = SliderDefaults.colors(
                thumbColor = color,
                disabledActiveTrackColor = Transparent,
                disabledInactiveTrackColor = Transparent,
            ),
        )
    }
}

@Composable
fun InstrSlider2(value1: Float, value2: Float) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(GrayHard)
    ) {

        Slider(
            modifier = Modifier.fillMaxWidth(),
            value = value1,
            onValueChange = { },
            valueRange = 0f..100f,
            enabled = false,
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
                disabledActiveTrackColor = Transparent,
                disabledInactiveTrackColor = Transparent,
            ),
        )

        Slider(
            modifier = Modifier.fillMaxWidth(),
            value = value2,
            onValueChange = { },
            valueRange = 0f..100f,
            enabled = false,
            thumb = {
                Box(
                    modifier = Modifier
                        .width(10.dp)
                        .fillMaxHeight()
                        .background(RedMedium)
                )
            },
            colors = SliderDefaults.colors(
                thumbColor = RedMedium,
                disabledActiveTrackColor = Transparent,
                disabledInactiveTrackColor = Transparent,
            ),
        )
    }
}