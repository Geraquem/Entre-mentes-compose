package com.mmfsin.betweenmindscompose.presentation.dashboard.questions.offline

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.betweenmindscompose.R
import com.mmfsin.betweenmindscompose.presentation.choose.components.ChooseToolbar
import com.mmfsin.betweenmindscompose.presentation.core.components.ButtonCustom
import com.mmfsin.betweenmindscompose.presentation.core.components.MediumText
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerLarge
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerMedium
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerMini
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerSmall
import com.mmfsin.betweenmindscompose.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenmindscompose.presentation.core.theme.RedHard
import com.mmfsin.betweenmindscompose.presentation.core.theme.White
import com.mmfsin.betweenmindscompose.presentation.core.theme.alphazet
import com.mmfsin.betweenmindscompose.presentation.core.theme.courier
import com.mmfsin.betweenmindscompose.presentation.dashboard.common.RoundCount
import com.mmfsin.betweenmindscompose.presentation.dashboard.common.SwipeBox
import com.mmfsin.betweenmindscompose.presentation.dashboard.questions.components.People
import com.mmfsin.betweenmindscompose.utils.ShowAlpha

@Preview
@Composable
fun QuestionsOfflinePV() {
    QuestionsOfflineContent(
        uiState = QuestionsOfflineStates(
            isLoading = false,
            showRoundView = false,
        ),
        {}, {}
    )
}

@Composable
fun QuestionsOfflineScreen(viewModel: QuestionsOfflineViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    QuestionsOfflineContent(
        uiState = uiState,
        onBlueNameChange = { viewModel.onBlueNameChanged(it) },
        onOrangeNameChange = { viewModel.onOrangeNameChanged(it) }
    )
}

@Composable
fun QuestionsOfflineContent(
    uiState: QuestionsOfflineStates,
    onBlueNameChange: (String) -> Unit,
    onOrangeNameChange: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            ChooseToolbar(
                goBack = {},
                goToInstructions = {}
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
                .background(BackgroundBlack)
                .padding(innerPadding)
                .padding(12.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                MediumText(
                    text = stringResource(R.string.scoreboard_rounds),
                    fontFamily = alphazet,
                    color = White
                )
                SpacerMini()
                Box(Modifier.fillMaxWidth().height(40.dp).background(RedHard))

                SpacerLarge()

                Box(
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    MediumText(
                        text = "¿Quién es más amable cuando se trata de ser fiel a su equipo de fútbol a las 15 de la tarde un domingo?",
                        color = White,
                        fontFamily = courier,
                        gravity = TextAlign.Center,
                        fontSize = 18.sp
                    )
                }

                SpacerMedium()

                People(
                    blueName = uiState.blueName,
                    onBlueNameChange = { onBlueNameChange(it) },
                    firstBlueOpinion = 0,
                    secondBlueOpinion = 0,
                    orangeName = uiState.orangeName,
                    onOrangeNameChange = { onOrangeNameChange(it) },
                    firstOrangeOpinion = 0,
                    secondOrangeOpinion = 0,
                    showFirstOpinion = uiState.firstOpinionVisible,
                    showSecondOpinion = uiState.secondOpinionVisible
                )

                Spacer(Modifier.weight(1f))

                SwipeBox()
                SpacerLarge()
                ButtonCustom(
                    onClick = {},
                    text = R.string.btn_hide,
                    modifier = Modifier.fillMaxWidth()
                )
                SpacerSmall()
            }

            ShowAlpha(uiState.showRoundView) { RoundCount(uiState.roundCount) }
        }
    }
}