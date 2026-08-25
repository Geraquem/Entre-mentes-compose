@file:OptIn(ExperimentalMaterial3Api::class)

package com.mmfsin.betweenminds.presentation.instructions.ranges

import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.presentation.core.components.MediumText
import com.mmfsin.betweenminds.presentation.core.components.SpacerCustom
import com.mmfsin.betweenminds.presentation.core.components.SpacerLarge
import com.mmfsin.betweenminds.presentation.core.components.SpacerMedium
import com.mmfsin.betweenminds.presentation.core.components.SpacerSmall
import com.mmfsin.betweenminds.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenminds.presentation.core.theme.White
import com.mmfsin.betweenminds.presentation.instructions.common.InstructionsText
import com.mmfsin.betweenminds.presentation.instructions.common.PhaseSeparator
import com.mmfsin.betweenminds.presentation.instructions.ranges.component.EditTextInst
import com.mmfsin.betweenminds.presentation.instructions.ranges.component.RangesSliderInstr

@Preview
@Composable
fun InstrOfflineRanges() {

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

            InstructionsText(R.string.instr_offline_first)

            SpacerLarge()

            PhaseSeparator(text = R.string.instr_ranges_1)

            SpacerMedium()

            InstructionsText(R.string.instr_ranges_2)

            SpacerLarge()

            RangesSliderInstr(
                showBullseye = true,
                showSlider = false,
                bullseyePosition = 20f,
                sliderPosition = 0f,
            )

            SpacerMedium()

            InstructionsText(R.string.instr_ranges_3)

            SpacerLarge()

            EditTextInst()

            SpacerMedium()

            InstructionsText(R.string.instr_ranges_4)

            SpacerLarge()

            PhaseSeparator(text = R.string.instr_ranges_5)

            SpacerMedium()

            InstructionsText(R.string.instr_ranges_6)

            SpacerLarge()

            MediumText(
                text = R.string.instr_ranges_example_clue,
                color = White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            SpacerMedium()

            RangesSliderInstr(
                showBullseye = false,
                showSlider = true,
                bullseyePosition = 0f,
                sliderPosition = 28f,
            )

            SpacerLarge()

            InstructionsText(R.string.instr_ranges_7)

            SpacerLarge()

            RangesSliderInstr(
                showBullseye = true,
                showSlider = true,
                bullseyePosition = 20f,
                sliderPosition = 28f,
            )

            SpacerLarge()

            PhaseSeparator(text = R.string.instr_ranges_8)

            SpacerMedium()

            InstructionsText(R.string.instr_ranges_9)

            SpacerSmall()

            InstructionsText(R.string.instr_ranges_10)

            SpacerCustom(space = 100.dp)
        }
    }
}