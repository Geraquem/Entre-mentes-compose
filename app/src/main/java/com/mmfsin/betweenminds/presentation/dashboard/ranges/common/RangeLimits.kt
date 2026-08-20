package com.mmfsin.betweenminds.presentation.dashboard.ranges.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.presentation.core.components.MediumText
import com.mmfsin.betweenminds.presentation.core.components.SpacerSmall
import com.mmfsin.betweenminds.presentation.core.theme.White

@Preview
@Composable
fun RangeLimitsPV() {
    RangeLimits("Algo que sea de niños", "Algo que sea de adultos")
}

@Composable
fun RangeLimits(leftRange: String, rightRange: String) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                painterResource(R.drawable.ic_long_arrow), null,
                tint = White
            )
            MediumText(
                text = leftRange,
                color = White,
                modifier = Modifier.align(Alignment.Start).padding(start = 8.dp)
            )
        }

        SpacerSmall(horizontal = true)

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                painterResource(R.drawable.ic_long_arrow), null,
                tint = White,
                modifier = Modifier.graphicsLayer { scaleX = -1f }
            )
            MediumText(
                text = rightRange,
                color = White,
                modifier = Modifier.align(Alignment.End).padding(end = 8.dp),
                gravity = TextAlign.End
            )
        }
    }
}
