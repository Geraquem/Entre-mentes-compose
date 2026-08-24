package com.mmfsin.betweenminds.presentation.dashboard.questions.online.join

import android.content.Context
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.betweenminds.utils.NAV_INSTR_QUESTIONS_ONLINE
import com.mmfsin.betweenminds.utils.openBedRockActivity

@Preview
@Composable
fun QuestionsOnlineJoinPV() {
    QuestionsOnlineJoinContent(
        uiStates = QuestionsOnlineJoinStates(

        ), {}, {}
    )
}

@Composable
fun QuestionsOnlineJoinScreen(
    viewModel: QuestionsOnlineJoinViewModel = hiltViewModel(),
    roomCode: String?
) {
    val context = LocalContext.current
    val activity = LocalActivity.current

    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(roomCode) { viewModel.updateRoomCode(roomCode) }

    QuestionsOnlineJoinContent(
        uiStates = uiStates,
        goBack = { activity?.finish() },
        goToInstructions = { context.goToInstructions() }
    )
}

@Composable
fun QuestionsOnlineJoinContent(
    uiStates: QuestionsOnlineJoinStates,
    goBack: () -> Unit,
    goToInstructions: () -> Unit,
) {

}

private fun Context.goToInstructions() = openBedRockActivity(NAV_INSTR_QUESTIONS_ONLINE)