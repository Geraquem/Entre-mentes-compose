@file:OptIn(ExperimentalMaterial3Api::class)

package com.mmfsin.betweenminds.presentation.instructions.ranking

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
import com.mmfsin.betweenminds.presentation.instructions.ranking.components.RankingInstr
import com.mmfsin.betweenminds.presentation.instructions.ranking.components.RankingInstrSolution
import com.mmfsin.betweenminds.presentation.instructions.ranking.components.RankingOptions

@Preview
@Composable
fun InstrOfflineRanking() {

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

            PhaseSeparator(text = R.string.instr_ranking_1)

            SpacerMedium()

            InstructionsText(R.string.instr_ranking_2)

            SpacerLarge()

            RankingInstr(
                text2 = R.string.instr_ranking_example_2,
                text3 = R.string.instr_ranking_example_3,
            )

            SpacerMedium()

            RankingOptions(
                text1 = R.string.instr_ranking_example_1,
                text4 = R.string.instr_ranking_example_4,
            )

            SpacerLarge()

            InstructionsText(R.string.instr_ranking_3)

            SpacerSmall()

            InstructionsText(R.string.instr_ranking_4)

            SpacerLarge()

            PhaseSeparator(R.string.instr_ranking_5)

            SpacerMedium()

            InstructionsText(R.string.instr_ranking_6)

            SpacerSmall()

            InstructionsText(R.string.instr_ranking_7)

            SpacerLarge()

            RankingInstr(
                text1 = R.string.instr_ranking_example_1,
                text2 = R.string.instr_ranking_example_3,
                text3 = R.string.instr_ranking_example_4,
                text4 = R.string.instr_ranking_example_2,
            )

            SpacerLarge()

            InstructionsText(R.string.instr_ranking_8)

            SpacerSmall()

            InstructionsText(R.string.instr_ranking_9)

            SpacerLarge()

            RankingInstrSolution(
                text1 = R.string.instr_ranking_example_1,
                text2 = R.string.instr_ranking_example_3,
                text3 = R.string.instr_ranking_example_4,
                text4 = R.string.instr_ranking_example_2,
            )

            SpacerLarge()

            InstructionsText(R.string.instr_ranking_10)

            SpacerSmall()

            InstructionsText(R.string.instr_ranking_11)

            SpacerCustom(space = 100.dp)
        }
    }
}