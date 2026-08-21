package com.mmfsin.betweenminds.presentation.dashboard.questions.online

import android.content.Context
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.betweenminds.presentation.core.components.CustomToolbar
import com.mmfsin.betweenminds.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenminds.utils.NAV_INSTR_QUESTIONS_ONLINE
import com.mmfsin.betweenminds.utils.openBedRockActivity

@Preview
@Composable
fun QuestionsOnlinePV() {
    QuestionsOnlineContent(
        uiStates = QuestionsOnlineStates(

        ),
        {}, {},
    )
}

@Composable
fun QuestionsOnlineScreen(viewModel: QuestionsOnlineViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val activity = LocalActivity.current

    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()
    QuestionsOnlineContent(
        uiStates = uiStates,
        goBack = { activity?.finish() },
        goToInstructions = { context.goToInstructions() },
    )
}

@Composable
fun QuestionsOnlineContent(
    uiStates: QuestionsOnlineStates,
    goBack: () -> Unit,
    goToInstructions: () -> Unit,

    ) {
    var parentWidth by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            CustomToolbar(
                goBack = { goBack() },
                goToInstructions = { goToInstructions() }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
                .background(BackgroundBlack)
                .padding(innerPadding)
                .padding(12.dp)
        ) {
        }
    }
}

private fun Context.goToInstructions() = openBedRockActivity(NAV_INSTR_QUESTIONS_ONLINE)