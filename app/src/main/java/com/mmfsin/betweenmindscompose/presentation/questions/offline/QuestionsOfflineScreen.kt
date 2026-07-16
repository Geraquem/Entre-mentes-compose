package com.mmfsin.betweenmindscompose.presentation.questions.offline

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.betweenmindscompose.presentation.core.theme.RedHard

@Preview
@Composable
fun QuestionsOfflinePV() {
    QuestionsOfflineContent(
        uiState = QuestionsOfflineStates(
            isLoading = false
        ),

        )
}

@Composable
fun QuestionsOfflineScreen(viewModel: QuestionsOfflineViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    QuestionsOfflineContent(
        uiState = uiState
    )
}

@Composable
fun QuestionsOfflineContent(
    uiState: QuestionsOfflineStates,

    ) {
    Box(Modifier.fillMaxSize().background(RedHard)) { }
}