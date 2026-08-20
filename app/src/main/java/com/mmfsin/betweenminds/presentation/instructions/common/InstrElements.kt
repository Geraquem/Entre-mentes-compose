@file:OptIn(ExperimentalMaterial3Api::class)

package com.mmfsin.betweenminds.presentation.instructions.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.presentation.core.theme.GrayHard
import com.mmfsin.betweenminds.presentation.core.theme.Transparent
import com.mmfsin.betweenminds.presentation.core.theme.White
import com.mmfsin.betweenminds.presentation.core.theme.kineks

@Preview
@Composable
fun PhaseSeparatorPV() {
    Column {
        PhaseSeparator(R.string.instr_questions_3)
        InstructionsText(R.string.instr_questions_4)
        InstrSlider(20f, White)
    }
}

@Composable
fun PhaseSeparator(text: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(2.dp)
                .background(White)
        )

        Text(
            modifier = Modifier.padding(horizontal = 12.dp),
            text = stringResource(text),
            color = White,
            fontSize = 18.sp,
            fontFamily = kineks,
            fontWeight = FontWeight.Bold
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .background(Transparent)
        )
    }
}

@Composable
fun InstructionsText(text: Int) {
    Text(
        text = stringResource(text),
        style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 18.sp),
        color = White
    )
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