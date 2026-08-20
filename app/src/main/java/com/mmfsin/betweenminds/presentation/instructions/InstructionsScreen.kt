@file:OptIn(ExperimentalMaterial3Api::class)

package com.mmfsin.betweenminds.presentation.instructions

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.domain.models.GameType
import com.mmfsin.betweenminds.domain.models.GameType.QUESTIONS
import com.mmfsin.betweenminds.presentation.core.components.BigText
import com.mmfsin.betweenminds.presentation.core.components.CustomToolbar
import com.mmfsin.betweenminds.presentation.core.components.SmallText
import com.mmfsin.betweenminds.presentation.core.components.SpacerSmall
import com.mmfsin.betweenminds.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenminds.presentation.core.theme.BlueMedium
import com.mmfsin.betweenminds.presentation.core.theme.White
import com.mmfsin.betweenminds.presentation.instructions.questions.InstrOfflineQuestions
import com.mmfsin.betweenminds.presentation.instructions.questions.InstrOnlineQuestions
import kotlinx.coroutines.launch

@Preview
@Composable
fun InstructionsScreenPV() {
    InstructionsScreen(QUESTIONS, false)
}

@Composable
fun InstructionsScreen(gameType: GameType, onlineMode: Boolean) {

    val activity = LocalActivity.current

    val pagerState = rememberPagerState(
        pageCount = { 2 },
        initialPage = if (onlineMode) 0 else 1
    )

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CustomToolbar(
                goBack = { activity?.finish() },
                showInstructions = false
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .background(BackgroundBlack)
                .padding(innerPadding)
        ) {

            BigText(
                text = if (gameType == QUESTIONS) R.string.instr_mode_questions else R.string.instr_mode_ranges,
                allCaps = true,
                color = White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            SpacerSmall()

            PrimaryTabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = BackgroundBlack,
                indicator = {
                    TabRowDefaults.PrimaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(
                            pagerState.currentPage,
                            matchContentSize = true
                        ),
                        width = 100.dp,
                        height = 6.dp,
                        shape = RoundedCornerShape(0),
                        color = BlueMedium
                    )
                },
                divider = {}
            ) {
                listOf(
                    stringResource(R.string.online_mode),
                    stringResource(R.string.offline_mode)
                ).forEachIndexed { i, tab ->
                    Tab(
                        selected = pagerState.currentPage == i,
                        onClick = { scope.launch { pagerState.animateScrollToPage(i) } },
                        text = { SmallText(tab.uppercase(), color = White) }
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                userScrollEnabled = true,
                modifier = Modifier.fillMaxWidth()
            ) { page ->
                when (page) {
                    0 -> InstrOnlineQuestions()
                    1 -> InstrOfflineQuestions()
                }
            }
        }
    }
}