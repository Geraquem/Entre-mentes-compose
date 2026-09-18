@file:OptIn(ExperimentalMaterial3Api::class)

package com.mmfsin.betweenminds.presentation.menu.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.domain.models.GameType.QUESTIONS
import com.mmfsin.betweenminds.domain.models.GameType.RANGES
import com.mmfsin.betweenminds.domain.models.GameType.RANKING
import com.mmfsin.betweenminds.presentation.core.components.BigText
import com.mmfsin.betweenminds.presentation.core.components.ButtonCustom
import com.mmfsin.betweenminds.presentation.core.components.MediumText
import com.mmfsin.betweenminds.presentation.core.components.SpacerCustom
import com.mmfsin.betweenminds.presentation.core.components.SpacerLarge
import com.mmfsin.betweenminds.presentation.core.components.SpacerMedium
import com.mmfsin.betweenminds.presentation.core.components.SpacerMini
import com.mmfsin.betweenminds.presentation.core.components.SpacerSmall
import com.mmfsin.betweenminds.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenminds.presentation.core.theme.BlueMedium
import com.mmfsin.betweenminds.presentation.core.theme.GrayLight
import com.mmfsin.betweenminds.presentation.core.theme.OrangeHard
import com.mmfsin.betweenminds.presentation.core.theme.White
import com.mmfsin.betweenminds.utils.NAV_INSTR_QUESTIONS_ONLINE
import com.mmfsin.betweenminds.utils.NAV_INSTR_RANGES_ONLINE
import com.mmfsin.betweenminds.utils.NAV_INSTR_RANKING_ONLINE
import kotlinx.coroutines.launch

@Preview
@Composable
fun SelectorSheetPV() {
    SelectorSheet({}, {}, {})
}

@Composable
fun SelectorSheet(
    onDismiss: () -> Unit,
    openInstructions: (String) -> Unit,
    play: (String) -> Unit,
) {
    val scope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    fun closeDialog(action: () -> Unit = {}) {
        scope.launch {
            sheetState.hide()
            onDismiss()
            action()
        }
    }

    ModalBottomSheet(
        onDismissRequest = { closeDialog() },
        sheetState = sheetState,
        dragHandle = { }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .background(BackgroundBlack)
                .padding(horizontal = 16.dp),
        ) {
            SpacerMedium()
            Box(
                Modifier.width(64.dp).height(4.dp)
                    .clip(RoundedCornerShape(50))
                    .background(White)
                    .align(Alignment.CenterHorizontally)
            )

            SpacerCustom(32.dp)

            ModeBox(
                icon1 = R.drawable.ic_human_down,
                icon1Color = BlueMedium,
                icon2 = R.drawable.ic_human_down,
                icon2Color = OrangeHard,
                title = R.string.selector_questions,
                description = R.string.selector_questions_description_1,
                openInstructions = { openInstructions(NAV_INSTR_QUESTIONS_ONLINE) },
                play = { closeDialog { play(QUESTIONS.id) } }
            )

            SpacerMedium()

            ModeBox(
                icon1 = R.drawable.ic_arrow,
                icon2 = R.drawable.ic_arrow,
                title = R.string.selector_ranges,
                description = R.string.ranges_sheet_description,
                openInstructions = { openInstructions(NAV_INSTR_RANGES_ONLINE) },
                play = { closeDialog { play(RANGES.id) } })

            SpacerMedium()

            ModeBox(
                icon1 = R.drawable.ic_ranking,
                title = R.string.selector_ranking,
                description = R.string.raking_sheet_description,
                openInstructions = { openInstructions(NAV_INSTR_RANKING_ONLINE) },
                play = { closeDialog { play(RANKING.id) } }
            )

            SpacerLarge()
        }
    }
}

@Composable
fun SelectorButton(
    icon: Int?,
    text: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .height(40.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(White)
            .clickable(onClick = { onClick() })
            .padding(vertical = 8.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        icon?.let {
            Icon(painter = painterResource(icon), null, tint = BackgroundBlack)
            SpacerMini(horizontal = true)
        }
        MediumText(
            text = text,
            allCaps = true,
            color = BackgroundBlack,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun ModeBox(
    icon1: Int,
    icon1Color: Color = GrayLight,
    icon2: Int? = null,
    icon2Color: Color = GrayLight,
    title: Int,
    description: Int,
    openInstructions: () -> Unit,
    play: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = BackgroundBlack),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Column(Modifier.padding(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                BigText(
                    text = title,
                    allCaps = true,
                    color = GrayLight,
                    fontSize = 28.sp
                )

                SpacerSmall(horizontal = true)

                Icon(
                    painterResource(icon1), null,
                    tint = icon1Color,
                    modifier = Modifier.size(30.dp)
                )
                icon2?.let {
                    Icon(
                        painterResource(icon2), null,
                        tint = icon2Color,
                        modifier = Modifier.size(30.dp).graphicsLayer { scaleX = -1f }
                    )
                }
            }

            SpacerSmall()

            Text(
                text = stringResource(description),
                style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 18.sp),
                color = GrayLight
            )

            SpacerMedium()

            ActionButtons(
                openInstructions = { openInstructions() },
                play = { play() }
            )
        }
    }
}

@Composable
fun ActionButtons(
    openInstructions: () -> Unit,
    play: () -> Unit
) {
    Row(Modifier.fillMaxWidth()) {
        IconButton(
            onClick = { openInstructions() },
            modifier = Modifier.background(color = GrayLight, shape = CircleShape)
        ) {
            Icon(
                painterResource(R.drawable.ic_book), null,
                tint = BackgroundBlack
            )
        }

        SpacerSmall(horizontal = true)

        ButtonCustom(
            onClick = { play() },
            text = R.string.menu_play,
            color = GrayLight,
            textColor = BackgroundBlack,
            modifier = Modifier.weight(1f)
        )
    }
}