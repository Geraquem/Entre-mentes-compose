package com.mmfsin.betweenmindscompose.presentation.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.betweenmindscompose.R
import com.mmfsin.betweenmindscompose.domain.models.GameType
import com.mmfsin.betweenmindscompose.presentation.core.components.BigText
import com.mmfsin.betweenmindscompose.presentation.core.components.ButtonCustom
import com.mmfsin.betweenmindscompose.presentation.core.components.LoadingLottie
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerCustom
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerMedium
import com.mmfsin.betweenmindscompose.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenmindscompose.presentation.core.theme.White
import com.mmfsin.betweenmindscompose.presentation.menu.components.ParticlesBackground
import com.mmfsin.betweenmindscompose.presentation.menu.components.SelectorSheet
import com.mmfsin.betweenmindscompose.utils.AnimateY
import com.mmfsin.betweenmindscompose.utils.ShowAlpha

@Preview
@Composable
fun MenuScreenPV() {
    MenuContent(
        uiState = MenuStates(
            positonButtons = 0f
        ),
        {}, {},
    )
}

@Composable
fun MenuScreen(
    viewModel: MenuViewModel = hiltViewModel(),
    goToChooseFragment: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    MenuContent(
        uiState = uiState,
        showSelectorSheet = { value -> viewModel.showSelectorSheet(value) },
        goToChooseFragment = { id ->
            viewModel.showSelectorSheet(false)
            goToChooseFragment(id)
        }
    )
}

@Composable
fun MenuContent(
    uiState: MenuStates,
    showSelectorSheet: (value: Boolean) -> Unit,
    goToChooseFragment: (String) -> Unit
) {

    Box(Modifier.fillMaxSize().background(BackgroundBlack))

    ParticlesBackground()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ShowAlpha(
            visibleTrigger = !uiState.isLoading,
            duration = 2000
        ) {
            BigText(
                text = R.string.app_name,
                color = White, fontSize = 40.sp,
                modifier = Modifier.padding(bottom = 42.dp)
            )
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
    ) {
        Spacer(Modifier.weight(1f))

        AnimateY(
            targetY = uiState.positonButtons,
            duration = 1500
        ) {
            Column {
                ButtonCustom(
                    onClick = { showSelectorSheet(true) },
                    text = R.string.menu_play,
                    modifier = Modifier.fillMaxWidth(),
                )

                SpacerMedium()

                ButtonCustom(
                    onClick = {},
                    text = R.string.menu_packs,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        SpacerCustom(space = 30.dp)
    }

    if (uiState.showSelectorSheet) {
        SelectorSheet(
            onDismiss = { showSelectorSheet(false) },
            questionsInstructions = {},
            questions = { goToChooseFragment(GameType.QUESTIONS.id) },
            rangesInstructions = {},
            ranges = { goToChooseFragment(GameType.RANGES.id) },
        )
    }

    ShowAlpha(
        visibleTrigger = uiState.isLoading,
        duration = 200
    ) {
        LoadingLottie()
    }
}