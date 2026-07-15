@file:OptIn(ExperimentalMaterial3Api::class)

package com.mmfsin.betweenmindscompose.presentation.menu.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmfsin.betweenmindscompose.R
import com.mmfsin.betweenmindscompose.presentation.core.components.BigText
import com.mmfsin.betweenmindscompose.presentation.core.components.MediumText
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerCustom
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerLarge
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerMedium
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerMini
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerSmall
import com.mmfsin.betweenmindscompose.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenmindscompose.presentation.core.theme.BlueMedium
import com.mmfsin.betweenmindscompose.presentation.core.theme.OrangeHard
import com.mmfsin.betweenmindscompose.presentation.core.theme.White

@Preview
@Composable
fun SelectorSheetPV() {
    SelectorSheet({})
}

@Composable
fun SelectorSheet(
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
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
                    onClick = {}
                )
                SelectorButton(
                    icon = null,
                    text = R.string.menu_play,
                    modifier = Modifier.weight(1f),
                    onClick = {}
                )
            }

            SpacerLarge()

            Box(Modifier.fillMaxWidth().height(1.dp).background(White).padding(horizontal = 64.dp))

            SpacerLarge()

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow), null,
                    tint = White
                )
                Icon(
                    painter = painterResource(R.drawable.ic_arrow), null,
                    tint = White
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
                    onClick = {}
                )
                SelectorButton(
                    icon = null,
                    text = R.string.menu_play,
                    modifier = Modifier.weight(1f),
                    onClick = {}
                )
            }

            SpacerCustom(space = 40.dp)
        }
    }
}

@Preview
@Composable
fun SelectorButtonPV() {
    SelectorButton(
        icon = R.drawable.ic_book,
        text = R.string.selector_how_to_play,
        modifier = Modifier,
        {})
}

@Composable
fun SelectorButton(icon: Int?, text: Int, modifier: Modifier, onClick: () -> Unit) {
    Row(
        modifier = modifier
            .height(40.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(White)
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