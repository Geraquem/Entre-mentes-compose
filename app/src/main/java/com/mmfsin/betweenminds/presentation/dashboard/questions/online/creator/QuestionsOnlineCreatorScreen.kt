package com.mmfsin.betweenminds.presentation.dashboard.questions.online.creator

import android.content.Context
import androidx.activity.compose.BackHandler
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
import com.mmfsin.betweenminds.presentation.core.components.ErrorDialog
import com.mmfsin.betweenminds.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenminds.presentation.dashboard.questions.online.components.InitialQOCreatorDialog
import com.mmfsin.betweenminds.utils.NAV_INSTR_QUESTIONS_ONLINE
import com.mmfsin.betweenminds.utils.openBedRockActivity

@Preview
@Composable
fun QuestionsOnlineCreatorCreatorPV() {
    QuestionsOnlineCreatorContent(
        uiStates = QuestionsOnlineCreatorStates(

        ),
        {}, {}, {}, {},
        {}, {},
    )
}

@Composable
fun QuestionsOnlineCreatorScreen(viewModel: QuestionsOnlineCreatorViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val activity = LocalActivity.current

    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()
    QuestionsOnlineCreatorContent(
        uiStates = uiStates,
        goBack = { activity?.finish() },
        goToInstructions = { context.goToInstructions() },
        hideInitialDialog = { viewModel.hideInitialDialog() },
        onBlueNameChange = { viewModel.onBlueNameChanged(it) },
        onOrangeNameChange = { viewModel.onOrangeNameChanged(it) },
        //        updateFirstOpinionPercents = { viewModel.updateFirstOpinionPercents(it) },
        //        updateSecondOpinionPercents = { viewModel.updateSecondOpinionPercents(it) },
        //        readyOpinionOne = { viewModel.readyOpinionOne() },
        //        readyOpinionTwo = { viewModel.readyOpinionTwo() },
        //        handleNextRound = { viewModel.handleNextRound() },
        //        showResultDialog = { viewModel.showResultDialog(it) },
        //        replay = { viewModel.replay() },
        showExitDialog = { viewModel.showExitDialog(it) }
    )
}

@Composable
fun QuestionsOnlineCreatorContent(
    uiStates: QuestionsOnlineCreatorStates,
    goBack: () -> Unit,
    goToInstructions: () -> Unit,
    hideInitialDialog: () -> Unit,
    onBlueNameChange: (String) -> Unit,
    onOrangeNameChange: (String) -> Unit,
    //    updateFirstOpinionPercents: (Int) -> Unit,
    //    updateSecondOpinionPercents: (Int) -> Unit,
    //    readyOpinionOne: () -> Unit,
    //    readyOpinionTwo: () -> Unit,
    //    handleNextRound: () -> Unit,
    //    showResultDialog: (Boolean) -> Unit,
    //    replay: () -> Unit,
    showExitDialog: (Boolean) -> Unit
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

        if (uiStates.showInitialDialog) {
            InitialQOCreatorDialog(
                blueName = uiStates.blueName,
                onBlueNameChanged = { onBlueNameChange(it) },
                orangeName = uiStates.orangeName,
                onOrangeNameChanged = { onOrangeNameChange(it) },
                startGame = { hideInitialDialog() },
                howToPlay = { goToInstructions() },
                isLoading = uiStates.isLoading
            )
        }

        if (uiStates.showSwwDialog) ErrorDialog(accept = { goBack() })

        BackHandler { showExitDialog(true) }
    }
}

private fun Context.goToInstructions() = openBedRockActivity(NAV_INSTR_QUESTIONS_ONLINE)