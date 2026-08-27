package com.mmfsin.betweenminds.presentation.packs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.betweenminds.presentation.core.components.CustomToolbar
import com.mmfsin.betweenminds.presentation.core.components.LoadingFullScreen
import com.mmfsin.betweenminds.presentation.core.theme.BackgroundBlack

@Preview
@Composable
fun PacksScreenPV() {
    PacksContent(
        uiStates = PacksStates(

        ), {}
    )
}

@Composable
fun PacksScreen(
    viewModel: PacksViewModel = hiltViewModel(),
    goBack: () -> Unit
) {
    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()
    PacksContent(
        uiStates = uiStates,
        goBack = { goBack() }
    )
}

@Composable
fun PacksContent(
    uiStates: PacksStates,
    goBack: () -> Unit
) {
    Scaffold(
        topBar = {
            CustomToolbar(
                goBack = { goBack() },
                showInstructions = false
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

        if(uiStates.isLoading) LoadingFullScreen()
    }
}