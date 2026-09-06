package com.mmfsin.betweenminds.presentation.packs.detail

import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.domain.models.Pack
import com.mmfsin.betweenminds.presentation.core.components.ButtonCustom
import com.mmfsin.betweenminds.presentation.core.components.CustomToolbar
import com.mmfsin.betweenminds.presentation.core.components.ErrorDialog
import com.mmfsin.betweenminds.presentation.core.components.LoadingFullScreen
import com.mmfsin.betweenminds.presentation.core.components.MediumText
import com.mmfsin.betweenminds.presentation.core.components.SmallText
import com.mmfsin.betweenminds.presentation.core.components.SpacerMedium
import com.mmfsin.betweenminds.presentation.core.components.SpacerSmall
import com.mmfsin.betweenminds.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenminds.presentation.core.theme.Black
import com.mmfsin.betweenminds.presentation.core.theme.BlueMedium
import com.mmfsin.betweenminds.presentation.core.theme.White
import com.mmfsin.betweenminds.presentation.core.theme.alphazet

@Preview
@Composable
fun PackDetailPV() {
    PackDetailComponent(
        uiStates = PackDetailStates(
            isLoading = false,
            selected = true,
            pack = Pack(
                packTitle = "Para parejas",
                packDescription = "Si pensabas que ya os habíais exprimido al máximo, aquí hay otras 50 preguntas diferentes para que sigáis dándole al coco y descubriendo cómo de diferente pensáis sobre vosotros mismos."
            )
        ),
        {}, {}
    )
}

@Composable
fun PackDetailScreen(
    viewModel: PackDetailViewModel = hiltViewModel(),
    goBack: () -> Unit
) {
    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()
    PackDetailComponent(
        uiStates = uiStates,
        goBack = { goBack() },
        selectPack = { viewModel.selectPack() }
    )
}

@Composable
fun PackDetailComponent(
    uiStates: PackDetailStates,
    goBack: () -> Unit,
    selectPack: () -> Unit
) {
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
                .padding(horizontal = 16.dp)
                .padding(bottom = 12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                uiStates.pack?.let { p ->

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = p.packIcon,
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(White),
                            modifier = Modifier.size(28.dp)
                        )

                        SpacerMedium(horizontal = true)

                        MediumText(
                            text = p.packTitle,
                            color = White,
                            fontFamily = alphazet,
                            fontSize = 20.sp,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    SpacerMedium()

                    SmallText(
                        text = p.packDescription,
                        color = White,
                        fontFamily = alphazet
                    )
                }

                SpacerMedium()

                Box(modifier = Modifier.fillMaxWidth()) {
                    CompositionLocalProvider(
                        LocalOverscrollFactory provides null
                    ) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            uiStates.questions.forEach { q ->
                                item { QuestionItem(q.question) }
                            }
                            uiStates.ranges.forEach { r ->
                                item { RangeItem(r.leftRange, r.rightRange) }
                            }
                        }
                    }
                }
            }

            SpacerMedium()

            val showButton = if (uiStates.selected) true else uiStates.purchased
            if (showButton) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    ButtonCustom(
                        onClick = { if (!uiStates.selected) selectPack() },
                        text = if (uiStates.selected) R.string.pack_selected
                        else R.string.pack_selected_btn,
                        color = if (uiStates.selected) BlueMedium else White,
                        textColor = if (uiStates.selected) White else Black,
                        modifier = Modifier.fillMaxWidth()
                    )

                    SpacerSmall()
                }
            }
        }

        if (uiStates.showSwwDialog) ErrorDialog(accept = { goBack() })

        if (uiStates.isLoading) LoadingFullScreen()
    }
}

@Composable
fun QuestionItem(text: String) {
    Row {
        Column {
            SpacerSmall()
            Box(
                modifier = Modifier.size(4.dp)
                    .clip(RoundedCornerShape(50))
                    .background(White)
                    .padding(top = 8.dp)
            )
        }

        SpacerSmall(horizontal = true)

        MediumText(
            text = text,
            fontFamily = alphazet,
            color = White
        )
    }
}

@Composable
fun RangeItem(leftText: String, rightText: String) {
    Row {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Start
        ) {
            Box(modifier = Modifier.padding(top = 8.dp)) {
                Box(
                    modifier = Modifier.size(4.dp)
                        .clip(RoundedCornerShape(50))
                        .background(White)
                )
            }
            SpacerSmall(horizontal = true)

            SmallText(
                text = leftText,
                fontFamily = alphazet,
                gravity = TextAlign.Start,
                color = White,
                modifier = Modifier.weight(1f)
            )
        }

        SpacerSmall(horizontal = true)

        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.End
        ) {
            SmallText(
                text = rightText,
                fontFamily = alphazet,
                gravity = TextAlign.End,
                color = White,
                modifier = Modifier.weight(1f)
            )

            SpacerSmall(horizontal = true)

            Box(modifier = Modifier.padding(top = 8.dp)) {
                Box(
                    modifier = Modifier.size(4.dp)
                        .clip(RoundedCornerShape(50))
                        .background(White)
                )
            }
        }
    }
}