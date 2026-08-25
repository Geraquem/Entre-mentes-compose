package com.mmfsin.betweenminds.presentation.instructions.ranges

import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.presentation.core.components.SpacerCustom
import com.mmfsin.betweenminds.presentation.core.components.SpacerLarge
import com.mmfsin.betweenminds.presentation.core.components.SpacerMedium
import com.mmfsin.betweenminds.presentation.core.components.SpacerSmall
import com.mmfsin.betweenminds.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenminds.presentation.instructions.common.InstructionsText
import com.mmfsin.betweenminds.presentation.instructions.common.PhaseSeparator
import com.mmfsin.betweenminds.presentation.instructions.ranges.component.EditTextInst
import com.mmfsin.betweenminds.presentation.instructions.ranges.component.RangesSliderInstr

@Preview
@Composable
fun InstrOnlineRanges() {

    val scrollState = rememberScrollState()

    CompositionLocalProvider(
        LocalOverscrollFactory provides null
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .background(BackgroundBlack)
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {

            InstructionsText(R.string.instr_online_first)

            SpacerSmall()

            InstructionsText(R.string.instr_online_second)

            SpacerLarge()

            InstructionsText(R.string.instr_online_ranges_1)

            SpacerLarge()

            PhaseSeparator(text = R.string.instr_online_ranges_2)

            SpacerMedium()

            InstructionsText(R.string.instr_online_ranges_3)

            SpacerLarge()

            RangesSliderInstr(
                showBullseye = true,
                showSlider = false,
                bullseyePosition = 20f,
                sliderPosition = 0f,
            )

            SpacerMedium()

            InstructionsText(R.string.instr_online_ranges_4)

            SpacerLarge()

            EditTextInst()

            SpacerMedium()

            InstructionsText(R.string.instr_online_ranges_5)

            SpacerSmall()

            InstructionsText(R.string.instr_online_ranges_6)

            SpacerLarge()

            PhaseSeparator(text = R.string.instr_online_ranges_7)

            SpacerMedium()

            InstructionsText(R.string.instr_online_ranges_8)

            SpacerSmall()

            InstructionsText(R.string.instr_online_ranges_9)

            SpacerSmall()

            InstructionsText(R.string.instr_online_ranges_10)

            SpacerLarge()

            RangesSliderInstr(
                showBullseye = false,
                showSlider = true,
                bullseyePosition = 0f,
                sliderPosition = 28f,
            )

            SpacerLarge()

            InstructionsText(R.string.instr_online_ranges_11)

            SpacerLarge()

            RangesSliderInstr(
                showBullseye = true,
                showSlider = true,
                bullseyePosition = 20f,
                sliderPosition = 28f,
            )

            SpacerLarge()

            InstructionsText(R.string.instr_online_ranges_12)

            SpacerCustom(space = 100.dp)
        }
    }
}