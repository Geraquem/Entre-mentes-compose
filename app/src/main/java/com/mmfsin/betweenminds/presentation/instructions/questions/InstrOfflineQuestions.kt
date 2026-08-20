@file:OptIn(ExperimentalMaterial3Api::class)

package com.mmfsin.betweenminds.presentation.instructions.questions

import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.presentation.core.components.MediumText
import com.mmfsin.betweenminds.presentation.core.components.SpacerLarge
import com.mmfsin.betweenminds.presentation.core.components.SpacerMedium
import com.mmfsin.betweenminds.presentation.core.components.SpacerSmall
import com.mmfsin.betweenminds.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenminds.presentation.core.theme.RedMedium
import com.mmfsin.betweenminds.presentation.core.theme.White
import com.mmfsin.betweenminds.presentation.core.theme.alphazet
import com.mmfsin.betweenminds.presentation.dashboard.questions.components.People
import com.mmfsin.betweenminds.presentation.instructions.common.InstrSlider
import com.mmfsin.betweenminds.presentation.instructions.common.InstructionsText
import com.mmfsin.betweenminds.presentation.instructions.common.PhaseSeparator

@Preview
@Composable
fun InstrOfflineQuestions() {

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

            SpacerSmall()

            InstructionsText(R.string.instr_questions_1)

            SpacerSmall()

            InstructionsText(R.string.instr_questions_2)

            SpacerLarge()

            People(
                blueName = stringResource(R.string.instr_questions_maria),
                onBlueNameChange = { },
                firstBlueOpinion = 50,
                secondBlueOpinion = 0,
                orangeName = stringResource(R.string.instr_questions_juan),
                onOrangeNameChange = { },
                firstOrangeOpinion = 50,
                secondOrangeOpinion = 0,
                showFirstOpinion = true,
                showSecondOpinion = false,
                blueHandsUp = false,
                orangeHandsUp = false
            )

            SpacerLarge()

            PhaseSeparator(text = R.string.instr_questions_3)

            SpacerMedium()

            InstructionsText(R.string.instr_questions_4)

            SpacerMedium()

            MediumText(
                text = R.string.instr_questions_question,
                color = White,
                fontFamily = alphazet,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 18.sp
            )

            SpacerMedium()

            InstructionsText(R.string.instr_questions_5)

            SpacerLarge()

            People(
                blueName = stringResource(R.string.instr_questions_maria),
                onBlueNameChange = { },
                firstBlueOpinion = 80,
                secondBlueOpinion = 0,
                orangeName = stringResource(R.string.instr_questions_juan),
                onOrangeNameChange = { },
                firstOrangeOpinion = 20,
                secondOrangeOpinion = 0,
                showFirstOpinion = true,
                showSecondOpinion = false,
                blueHandsUp = true,
                orangeHandsUp = false
            )

            SpacerMedium()

            InstrSlider(20f, White)

            SpacerMedium()

            InstructionsText(R.string.instr_questions_6)

            SpacerMedium()

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(painterResource(R.drawable.ic_player_one), null)
                SpacerSmall(horizontal = true)
                InstructionsText(R.string.instr_questions_7)
            }

            SpacerMedium()

            InstructionsText(R.string.instr_questions_8)

            SpacerLarge()

            PhaseSeparator(R.string.instr_questions_9)

            SpacerMedium()

            InstructionsText(R.string.instr_questions_10)

            SpacerLarge()

            People(
                blueName = stringResource(R.string.instr_questions_maria),
                onBlueNameChange = { },
                firstBlueOpinion = 0,
                secondBlueOpinion = 62,
                orangeName = stringResource(R.string.instr_questions_juan),
                onOrangeNameChange = { },
                firstOrangeOpinion = 0,
                secondOrangeOpinion = 38,
                showFirstOpinion = false,
                showSecondOpinion = true,
                blueHandsUp = true,
                orangeHandsUp = false
            )

            SpacerMedium()

            InstrSlider(38f, RedMedium)

            SpacerLarge()

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(painterResource(R.drawable.ic_player_two), null)
                SpacerSmall(horizontal = true)
                InstructionsText(R.string.instr_questions_10_1)
            }

            SpacerMedium()

            InstructionsText(R.string.instr_questions_11)

            SpacerMedium()

            InstructionsText(R.string.instr_questions_12)

            SpacerLarge()

            PhaseSeparator(R.string.instr_questions_13)

            SpacerMedium()

            InstructionsText(R.string.instr_questions_14)

            SpacerMedium()

            InstructionsText(R.string.instr_questions_15)

            SpacerLarge()
        }
    }
}