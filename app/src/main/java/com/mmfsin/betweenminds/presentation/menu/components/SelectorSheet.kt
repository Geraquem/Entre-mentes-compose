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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.presentation.core.components.BigText
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
import kotlinx.coroutines.launch

@Preview
@Composable
fun SelectorSheetPV() {
    SelectorSheet(
        {}, {}, {}, {},
        {}, {}, {},
    )
}

@Composable
fun SelectorSheet(
    onDismiss: () -> Unit,
    questionsInstructions: () -> Unit,
    questions: () -> Unit,
    ranges: () -> Unit,
    rangesInstructions: () -> Unit,
    rankings: () -> Unit,
    rankingsInstructions: () -> Unit,
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
                .padding(horizontal = 24.dp),
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
                description = R.string.selector_questions_description_1
            )

            SpacerMedium()

            ModeBox(
                icon1 = R.drawable.ic_arrow,
                icon2 = R.drawable.ic_arrow,
                title = R.string.selector_ranges,
                description = R.string.ranges_sheet_description
            )

            SpacerMedium()

            ModeBox(
                icon1 = R.drawable.ic_ranking,
                title = R.string.selector_ranking,
                description = R.string.raking_sheet_description
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
    icon1Color: Color = BackgroundBlack,
    icon2: Int? = null,
    icon2Color: Color = BackgroundBlack,
    title: Int,
    description: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = GrayLight),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(Modifier.padding(8.dp)) {
            Row() {
                Icon(
                    painterResource(icon1), null,
                    tint = icon1Color
                )
                icon2?.let {
                    Icon(
                        painterResource(icon2), null,
                        tint = icon2Color
                    )
                }

                SpacerSmall(horizontal = true)

                BigText(text = title)
            }

            SpacerSmall()

            Text(
                text = stringResource(description),
                style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 18.sp),
                color = BackgroundBlack
            )
        }
    }
}