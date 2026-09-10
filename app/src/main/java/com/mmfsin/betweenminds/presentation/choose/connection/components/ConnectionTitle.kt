package com.mmfsin.betweenminds.presentation.choose.connection.components

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
import androidx.compose.ui.unit.sp
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.domain.models.GameType
import com.mmfsin.betweenminds.domain.models.GameType.QUESTIONS
import com.mmfsin.betweenminds.domain.models.GameType.RANGES
import com.mmfsin.betweenminds.domain.models.GameType.RANKING
import com.mmfsin.betweenminds.presentation.core.components.BigText
import com.mmfsin.betweenminds.presentation.core.components.SpacerMini
import com.mmfsin.betweenminds.presentation.core.theme.BlueMedium
import com.mmfsin.betweenminds.presentation.core.theme.OrangeMedium
import com.mmfsin.betweenminds.presentation.core.theme.White

@Preview
@Composable
fun ConnectionTitlePV() {
    ConnectionTitle(GameType.RANKING)
}

@Composable
fun ConnectionTitle(type: GameType) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        when (type) {
            QUESTIONS -> {
                BigText(
                    text = stringResource(R.string.selector_questions),
                    allCaps = true,
                    color = White,
                    fontSize = 28.sp,
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

            RANGES -> {
                BigText(
                    text = stringResource(R.string.selector_ranges),
                    allCaps = true,
                    color = White,
                    fontSize = 28.sp,
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

            RANKING -> {
                BigText(
                    text = stringResource(R.string.selector_ranking),
                    allCaps = true,
                    color = White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.weight(1f))

                Icon(
                    painterResource(R.drawable.ic_ranking), null,
                    tint = White,
                    modifier = Modifier.size(36.dp)
                )

            }
        }
    }
}