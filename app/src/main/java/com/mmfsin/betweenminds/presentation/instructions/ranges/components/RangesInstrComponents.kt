@file:OptIn(ExperimentalMaterial3Api::class)

package com.mmfsin.betweenminds.presentation.instructions.ranges.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.presentation.core.components.MediumText
import com.mmfsin.betweenminds.presentation.core.components.SpacerLarge
import com.mmfsin.betweenminds.presentation.core.components.SpacerSmall
import com.mmfsin.betweenminds.presentation.core.theme.GrayHard
import com.mmfsin.betweenminds.presentation.core.theme.Transparent
import com.mmfsin.betweenminds.presentation.core.theme.White
import com.mmfsin.betweenminds.presentation.core.theme.alphazet
import com.mmfsin.betweenminds.presentation.dashboard.ranges.components.Bullseye
import com.mmfsin.betweenminds.presentation.dashboard.ranges.components.RangeLimits

@Preview
@Composable
fun RangesSliderInstrPV() {
    Column() {
        EditTextInst()
        SpacerLarge()
        RangesSliderInstr(
            true,
            true,
            50f,
            10f,
        )
    }
}

@Composable
fun EditTextInst(modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        MediumText(
            text = R.string.ranges_write_a_clue,
            color = White,
            fontFamily = alphazet
        )

        SpacerSmall()

        BasicTextField(
            enabled = false,
            modifier = Modifier.fillMaxWidth()
                .border(
                    width = 2.dp,
                    color = GrayHard,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 12.dp, vertical = 18.dp),
            value = stringResource(R.string.instr_ranges_example_clue),
            onValueChange = { },
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = White),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                capitalization = KeyboardCapitalization.Sentences
            ),
            cursorBrush = SolidColor(GrayHard),
        )
    }
}

@Composable
fun RangesSliderInstr(
    showBullseye: Boolean,
    showSlider: Boolean,
    bullseyePosition: Float,
    sliderPosition: Float,
) {
    Column {
        Box(
            modifier = Modifier.fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(GrayHard)
        ) {

            if (showBullseye) {
                Bullseye(bullseyePosition)
            }

            if (showSlider)
                Slider(
                    modifier = Modifier.fillMaxWidth(),
                    enabled = false,
                    value = sliderPosition,
                    onValueChange = { },
                    valueRange = 0f..100f,
                    thumb = {
                        Box(
                            modifier = Modifier
                                .width(4.dp)
                                .fillMaxHeight()
                                .background(White)
                        )
                    },
                    colors = SliderDefaults.colors(
                        thumbColor = White,
                        activeTrackColor = Transparent,
                        disabledActiveTrackColor = Transparent,
                        inactiveTrackColor = Transparent,
                        disabledInactiveTrackColor = Transparent,
                    ),
                )

        }

        SpacerSmall()

        RangeLimits(
            leftRange = stringResource(R.string.instr_ranges_example_left),
            rightRange = stringResource(R.string.instr_ranges_example_right)
        )
    }
}