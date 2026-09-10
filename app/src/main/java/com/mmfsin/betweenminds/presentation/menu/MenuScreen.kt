package com.mmfsin.betweenminds.presentation.menu

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.domain.models.GameType
import com.mmfsin.betweenminds.presentation.core.components.BigText
import com.mmfsin.betweenminds.presentation.core.components.ButtonCustom
import com.mmfsin.betweenminds.presentation.core.components.LoadingLottie
import com.mmfsin.betweenminds.presentation.core.components.SpacerCustom
import com.mmfsin.betweenminds.presentation.core.components.SpacerMedium
import com.mmfsin.betweenminds.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenminds.presentation.core.theme.White
import com.mmfsin.betweenminds.presentation.core.theme.courier
import com.mmfsin.betweenminds.presentation.menu.components.ParticlesBackground
import com.mmfsin.betweenminds.presentation.menu.components.SelectorSheet
import com.mmfsin.betweenminds.utils.AnimateY
import com.mmfsin.betweenminds.utils.NAV_INSTR_QUESTIONS_ONLINE
import com.mmfsin.betweenminds.utils.NAV_INSTR_RANGES_ONLINE
import com.mmfsin.betweenminds.utils.NAV_INSTR_RANKING_ONLINE
import com.mmfsin.betweenminds.utils.ShowAlpha
import com.mmfsin.betweenminds.utils.openBedRockActivity

@Preview
@Composable
fun MenuScreenPV() {
    MenuContent(
        uiStates = MenuStates(
            positonButtons = 0f,
            showSelectorSheet = true
        ),
        {}, {}, {}, {},
        {}, {}
    )
}

@Composable
fun MenuScreen(
    viewModel: MenuViewModel = hiltViewModel(),
    goToGameTypeScreen: () -> Unit,
    goToConnectionScreen: (String) -> Unit,
    goToPacksScreen: () -> Unit,
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MenuContent(
        uiStates = uiState,
        showSelectorSheet = { value -> viewModel.showSelectorSheet(value) },
        openInstructions = { context.goToInstructions(it) },
        goToGameTypeScreen = { goToGameTypeScreen() },
        goToConnectionScreen = { goToConnectionScreen(it) },
        goToPacksScreen = { goToPacksScreen() },
        setFreePacks = { viewModel.setFreePacks() }
    )
}

@Composable
fun MenuContent(
    uiStates: MenuStates,
    showSelectorSheet: (value: Boolean) -> Unit,
    openInstructions: (String) -> Unit,
    goToGameTypeScreen: () -> Unit,
    goToConnectionScreen: (String) -> Unit,
    goToPacksScreen: () -> Unit,
    setFreePacks: () -> Unit,
) {

    var freeCount by remember { mutableIntStateOf(0) }

    if (freeCount > 29) {
        freeCount = 0
        setFreePacks()
    }

    Box(Modifier.fillMaxSize().background(BackgroundBlack))

    ParticlesBackground()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ShowAlpha(
            visibleTrigger = !uiStates.isLoading,
            duration = 2000
        ) {
            BigText(
                text = R.string.app_name,
                color = White,
                allCaps = true,
                fontFamily = courier,
                fontSize = 42.sp,
                modifier = Modifier.padding(bottom = 42.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = { freeCount++ }
                    )
            )
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
    ) {
        Spacer(Modifier.weight(1f))

        AnimateY(
            targetY = uiStates.positonButtons,
            duration = 1500
        ) {
            Column {
                ButtonCustom(
                    onClick = { showSelectorSheet(true) },
                    //                    onClick = { goToGameTypeScreen() },
                    text = R.string.menu_play,
                    modifier = Modifier.fillMaxWidth(),
                )

                SpacerMedium()

                ButtonCustom(
                    onClick = { goToPacksScreen() },
                    text = R.string.menu_packs,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        SpacerCustom(space = 30.dp)
    }

    if (uiStates.showSelectorSheet) {
        SelectorSheet(
            onDismiss = { showSelectorSheet(false) },
            questionsInstructions = { openInstructions(NAV_INSTR_QUESTIONS_ONLINE) },
            questions = { goToConnectionScreen(GameType.QUESTIONS.id) },
            rangesInstructions = { openInstructions(NAV_INSTR_RANGES_ONLINE) },
            ranges = { goToConnectionScreen(GameType.RANGES.id) },
            rankingsInstructions = { openInstructions(NAV_INSTR_RANKING_ONLINE) },
            rankings = { goToConnectionScreen(GameType.RANKING.id) }
        )
    }

    ShowAlpha(
        visibleTrigger = uiStates.isLoading,
        duration = 200
    ) {
        LoadingLottie()
    }
}

private fun Context.goToInstructions(navGraph: String) = openBedRockActivity(navGraph)