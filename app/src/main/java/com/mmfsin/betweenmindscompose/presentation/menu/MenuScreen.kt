package com.mmfsin.betweenmindscompose.presentation.menu

import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.betweenmindscompose.R
import com.mmfsin.betweenmindscompose.presentation.core.components.BigText
import com.mmfsin.betweenmindscompose.presentation.core.components.LoadingLottie
import com.mmfsin.betweenmindscompose.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenmindscompose.presentation.core.theme.White

@Preview
@Composable
fun MenuScreenPV() {
    MenuContent(
        uiState = MenuStates()
    )
}

@Composable
fun MenuScreen(viewModel: MenuViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    MenuContent(
        uiState = uiState
    )
}

@Composable
fun MenuContent(
    uiState: MenuStates,
) {

    Box(Modifier.fillMaxSize().background(BackgroundBlack))

    ParticlesBackground()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        BigText(
            text = R.string.app_name,
            color = White,
            fontSize = 40.sp
        )
    }

    if (uiState.isLoading) LoadingLottie()
    else {

    }
}