package com.mmfsin.betweenmindscompose.presentation.choose.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmfsin.betweenmindscompose.R
import com.mmfsin.betweenmindscompose.domain.models.GameType
import com.mmfsin.betweenmindscompose.presentation.core.components.BigText
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerMini
import com.mmfsin.betweenmindscompose.presentation.core.theme.BlueMedium
import com.mmfsin.betweenmindscompose.presentation.core.theme.OrangeMedium
import com.mmfsin.betweenmindscompose.presentation.core.theme.White

@Preview
@Composable
fun ChooseTitlePV() {
    ChooseTitle(GameType.RANGES)
}

@Composable
fun ChooseTitle(type: GameType) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        when (type) {
            GameType.QUESTIONS -> {
                BigText(
                    text = stringResource(R.string.selector_questions),
                    allCaps = true,
                    color = White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.weight(1f))
                Icon(
                    painterResource(R.drawable.ic_human_down), null,
                    tint = BlueMedium,
                    modifier = Modifier.size(36.dp)
                )
                SpacerMini(horizontal = true)
                Icon(
                    painterResource(R.drawable.ic_human_down), null,
                    tint = OrangeMedium,
                    modifier = Modifier.size(36.dp)
                )
            }

            else -> {
                BigText(
                    text = stringResource(R.string.selector_ranges),
                    allCaps = true,
                    color = White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.weight(1f))
                Icon(
                    painterResource(R.drawable.ic_arrow), null,
                    tint = White,
                    modifier = Modifier.size(36.dp)
                )
                SpacerMini(horizontal = true)
                Icon(
                    painterResource(R.drawable.ic_arrow), null,
                    tint = White,
                    modifier = Modifier.size(36.dp).graphicsLayer { scaleX = -1f }
                )
            }
        }
    }
}