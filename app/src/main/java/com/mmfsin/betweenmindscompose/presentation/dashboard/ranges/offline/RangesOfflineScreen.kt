package com.mmfsin.betweenmindscompose.presentation.dashboard.ranges.offline

import android.content.Context
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
import com.mmfsin.betweenmindscompose.presentation.core.components.CustomToolbar
import com.mmfsin.betweenmindscompose.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenmindscompose.utils.NAV_INSTR_RANGES_OFFLINE
import com.mmfsin.betweenmindscompose.utils.openBedRockActivity

@Preview
@Composable
fun RangesOfflineScreenPV() {
    RangesOfflineContent(
        uiStates = RangesOfflineStates(

        ),
        {},
    )
}

@Composable
fun RangesOfflineScreen(viewModel: RangesOfflineViewModel = hiltViewModel()) {
    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()

    RangesOfflineContent(
        uiStates = uiStates,
        goToInstructions = {},
    )
}

@Composable
fun RangesOfflineContent(
    uiStates: RangesOfflineStates,
    goToInstructions: () -> Unit
) {
    Scaffold(
        topBar = {
            CustomToolbar(
                goBack = {},
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

private fun Context.goToInstructions() = openBedRockActivity(NAV_INSTR_RANGES_OFFLINE)
