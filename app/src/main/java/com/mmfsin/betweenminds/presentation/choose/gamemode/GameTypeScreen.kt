package com.mmfsin.betweenminds.presentation.choose.gamemode

import android.content.Context
import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.domain.models.GameType
import com.mmfsin.betweenminds.presentation.core.components.BigText
import com.mmfsin.betweenminds.presentation.core.components.CustomToolbar
import com.mmfsin.betweenminds.presentation.core.components.MediumText
import com.mmfsin.betweenminds.presentation.core.components.SpacerCustom
import com.mmfsin.betweenminds.presentation.core.components.SpacerLarge
import com.mmfsin.betweenminds.presentation.core.components.SpacerMedium
import com.mmfsin.betweenminds.presentation.core.components.SpacerMini
import com.mmfsin.betweenminds.presentation.core.components.SpacerSmall
import com.mmfsin.betweenminds.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenminds.presentation.core.theme.BlueMedium
import com.mmfsin.betweenminds.presentation.core.theme.OrangeHard
import com.mmfsin.betweenminds.presentation.core.theme.White
import com.mmfsin.betweenminds.presentation.menu.components.SelectorButton
import com.mmfsin.betweenminds.utils.NAV_INSTR_QUESTIONS_ONLINE
import com.mmfsin.betweenminds.utils.NAV_INSTR_RANGES_ONLINE
import com.mmfsin.betweenminds.utils.openBedRockActivity

@Preview
@Composable
fun GameTypeScreenPV() {
    GameTypeScreen({}, {})
}

@Composable
fun GameTypeScreen(
    goBack: () -> Unit,
    goToConnectionScreen: (String) -> Unit
) {

    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            CustomToolbar(
                goBack = { goBack() },
                showInstructions = false,
            )
        },
        containerColor = BackgroundBlack
    )
    { innerPadding ->
        CompositionLocalProvider(
            LocalOverscrollFactory provides null
        ) {
            Column(
                Modifier.fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                SpacerMedium()
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.ic_human_down), null,
                        tint = BlueMedium
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_human_down), null,
                        tint = OrangeHard
                    )
                    SpacerSmall(horizontal = true)
                    BigText(text = stringResource(R.string.selector_questions), allCaps = true, color = White)
                }

                SpacerSmall()

                MediumText(text = stringResource(R.string.questions_welcome), color = White)
                SpacerMini()
                MediumText(text = stringResource(R.string.questions_welcome_three), color = White)

                SpacerMedium()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    SelectorButton(
                        icon = R.drawable.ic_book,
                        text = R.string.selector_how_to_play,
                        modifier = Modifier.weight(1f),
                        onClick = { context.goToInstructions(NAV_INSTR_QUESTIONS_ONLINE) }
                    )
                    SelectorButton(
                        icon = null,
                        text = R.string.menu_play,
                        modifier = Modifier.weight(1f),
                        onClick = { goToConnectionScreen(GameType.QUESTIONS.id) }
                    )
                }

                SpacerLarge()

                Box(Modifier.fillMaxWidth().padding(horizontal = 24.dp).height(1.dp).background(White))

                SpacerLarge()

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow), null,
                        tint = White
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow), null,
                        tint = White,
                        modifier = Modifier.graphicsLayer { scaleX = -1f }
                    )
                    SpacerSmall(horizontal = true)
                    BigText(text = stringResource(R.string.selector_ranges), allCaps = true, color = White)
                }

                SpacerSmall()

                MediumText(text = stringResource(R.string.ranges_start_resume_other), color = White)
                SpacerMini()
                MediumText(text = stringResource(R.string.ranges_start_resume_two), color = White)

                SpacerMedium()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    SelectorButton(
                        icon = R.drawable.ic_book,
                        text = R.string.selector_how_to_play,
                        modifier = Modifier.weight(1f),
                        onClick = { context.openBedRockActivity(NAV_INSTR_RANGES_ONLINE) }
                    )
                    SelectorButton(
                        icon = null,
                        text = R.string.menu_play,
                        modifier = Modifier.weight(1f),
                        onClick = { goToConnectionScreen(GameType.RANGES.id) }
                    )
                }

                SpacerLarge()

                Box(Modifier.fillMaxWidth().padding(horizontal = 24.dp).height(1.dp).background(White))

                SpacerLarge()

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.ic_ranking), null,
                        tint = White
                    )
                    SpacerSmall(horizontal = true)
                    BigText(text = stringResource(R.string.selector_ranking), allCaps = true, color = White)
                }

                SpacerSmall()

                MediumText(text = stringResource(R.string.raking_initial_dialog_1), color = White)
                SpacerMini()
                MediumText(text = stringResource(R.string.raking_initial_dialog_2), color = White)

                SpacerMedium()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    SelectorButton(
                        icon = R.drawable.ic_book,
                        text = R.string.selector_how_to_play,
                        modifier = Modifier.weight(1f),
                        onClick = { }
                    )
                    SelectorButton(
                        icon = null,
                        text = R.string.menu_play,
                        modifier = Modifier.weight(1f),
                        onClick = { goToConnectionScreen(GameType.RANKING.id) }
                    )
                }

                SpacerCustom(space = 40.dp)
            }
        }
    }
}

private fun Context.goToInstructions(navGraph: String) = openBedRockActivity(navGraph)