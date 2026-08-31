package com.mmfsin.betweenminds.presentation.packs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.presentation.core.components.CustomToolbar
import com.mmfsin.betweenminds.presentation.core.components.ErrorDialog
import com.mmfsin.betweenminds.presentation.core.components.LoadingFullScreen
import com.mmfsin.betweenminds.presentation.core.components.MediumText
import com.mmfsin.betweenminds.presentation.core.components.SpacerMedium
import com.mmfsin.betweenminds.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenminds.presentation.core.theme.BlueMedium
import com.mmfsin.betweenminds.presentation.core.theme.White
import com.mmfsin.betweenminds.presentation.packs.components.PacksQuestions
import com.mmfsin.betweenminds.presentation.packs.components.PacksRanges
import kotlinx.coroutines.launch

@Preview
@Composable
fun PacksScreenPV() {
    PacksContent(
        uiStates = PacksStates(
            isLoading = false,
        ),
        {}, {}, {},
        {},
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
        goBack = { goBack() },
        seeMorePack = {},
        updateSelectedQuestionsPack = { viewModel.updateSelectedQuestionsPack(it) },
        updateSelectedRangesPack = { },
    )
}

@Composable
fun PacksContent(
    uiStates: PacksStates,
    goBack: () -> Unit,
    seeMorePack: () -> Unit,
    updateSelectedQuestionsPack: (Int) -> Unit,
    updateSelectedRangesPack: (Int) -> Unit,
) {

    val pagerState = rememberPagerState(
        pageCount = { 2 },
        initialPage = 0
    )

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CustomToolbar(
                goBack = { goBack() },
                showInstructions = false
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .background(BackgroundBlack)
                .padding(innerPadding)
                .padding(12.dp)
        ) {

            PrimaryTabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = BackgroundBlack,
                indicator = {
                    TabRowDefaults.PrimaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(pagerState.currentPage, matchContentSize = true),
                        width = 120.dp,
                        height = 6.dp,
                        shape = RoundedCornerShape(0),
                        color = BlueMedium
                    )
                },
                divider = {}
            ) {
                listOf(
                    stringResource(R.string.pack_questions),
                    stringResource(R.string.pack_ranges)
                ).forEachIndexed { i, txtTab ->
                    Tab(
                        selected = pagerState.currentPage == i,
                        onClick = { scope.launch { pagerState.animateScrollToPage(i) } },
                        text = { MediumText(txtTab.uppercase(), color = White) }
                    )
                }
            }

            SpacerMedium()

            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false,
                modifier = Modifier.fillMaxWidth()
            ) { page ->
                when (page) {
                    0 -> PacksQuestions(
                        packs = uiStates.questionsPacks,
                        selected = uiStates.selectedQuestionsPack,
                        seeMore = {},
                        updateQuestionsPack = { updateSelectedQuestionsPack(it) }
                    )

                    else -> PacksRanges(
                        packs = uiStates.rangesPacks,
                        selected = uiStates.selectedRangesPack,
                        seeMore = {},
                        updateRangesPack = { updateSelectedRangesPack(it) }
                    )
                }
            }
        }

        if (uiStates.showSwwDialog) ErrorDialog(accept = { goBack() })

        if (uiStates.isLoading) LoadingFullScreen()
    }
}