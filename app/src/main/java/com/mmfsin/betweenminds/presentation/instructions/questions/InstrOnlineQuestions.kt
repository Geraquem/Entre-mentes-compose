package com.mmfsin.betweenminds.presentation.instructions.questions

import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.mmfsin.betweenminds.presentation.core.theme.BlueMedium
import com.mmfsin.betweenminds.presentation.core.theme.OrangeHard
import com.mmfsin.betweenminds.presentation.core.theme.RedMedium
import com.mmfsin.betweenminds.presentation.core.theme.White
import com.mmfsin.betweenminds.presentation.core.theme.alphazet
import com.mmfsin.betweenminds.presentation.dashboard.questions.components.People
import com.mmfsin.betweenminds.presentation.dashboard.questions.components.SetPlayerName
import com.mmfsin.betweenminds.presentation.instructions.common.InstrSlider
import com.mmfsin.betweenminds.presentation.instructions.common.InstrSlider2
import com.mmfsin.betweenminds.presentation.instructions.common.InstructionsText

@Preview
@Composable
fun InstrOnlineQuestions() {

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

            InstructionsText(R.string.instr_online_questions_1)

            SpacerSmall()

            InstructionsText(R.string.instr_online_questions_2)

            SpacerLarge()

            SetPlayerName(
                name = stringResource(R.string.instr_questions_maria),
                onNameChange = {},
                hint = R.string.empty,
                color = BlueMedium,
                enabled = false
            )

            SpacerSmall()

            SetPlayerName(
                name = stringResource(R.string.instr_questions_juan),
                onNameChange = {},
                hint = R.string.empty,
                color = OrangeHard,
                enabled = false
            )

            SpacerLarge()

            InstructionsText(R.string.instr_online_questions_3)

            SpacerMedium()

            MediumText(
                text = R.string.instr_questions_question,
                color = White,
                fontFamily = alphazet,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 18.sp
            )

            SpacerMedium()

            InstructionsText(R.string.instr_online_questions_4)

            SpacerMedium()

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painterResource(R.drawable.ic_player_one), null,
                    modifier = Modifier.size(32.dp)
                )
                SpacerMedium(horizontal = true)
                Box(Modifier.width(10.dp).height(38.dp).background(White))
            }

            SpacerMedium()

            InstructionsText(R.string.instr_online_questions_5)

            SpacerMedium()

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painterResource(R.drawable.ic_player_two), null,
                    modifier = Modifier.size(32.dp)
                )
                SpacerMedium(horizontal = true)
                Box(Modifier.width(10.dp).height(38.dp).background(RedMedium))
            }

            SpacerMedium()

            InstructionsText(R.string.instr_online_questions_6)

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

            SpacerLarge()

            InstructionsText(R.string.instr_questions_6)

            SpacerMedium()

            InstructionsText(R.string.instr_online_questions_7)

            SpacerLarge()

            People(
                blueName = stringResource(R.string.instr_questions_maria),
                onBlueNameChange = { },
                firstBlueOpinion = 80,
                secondBlueOpinion = 62,
                orangeName = stringResource(R.string.instr_questions_juan),
                onOrangeNameChange = { },
                firstOrangeOpinion = 20,
                secondOrangeOpinion = 38,
                showFirstOpinion = true,
                showSecondOpinion = true,
                blueHandsUp = true,
                orangeHandsUp = false
            )

            SpacerMedium()

            InstrSlider2(20f, 38f)

            SpacerLarge()

            InstructionsText(R.string.instr_online_questions_8)

            SpacerSmall()

            InstructionsText(R.string.instr_online_questions_9)

            SpacerLarge()
        }
    }
}