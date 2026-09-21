package com.mmfsin.betweenminds.presentation.dashboard.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.presentation.core.components.MediumText
import com.mmfsin.betweenminds.presentation.core.components.SpacerMedium
import com.mmfsin.betweenminds.presentation.core.components.SpacerSmall

@Composable
fun PointsTable(userPoints: Int, otherPlayerPoints: Int) {
    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            SpacerMedium(horizontal = true)

            MediumText(
                text = R.string.endgame_online_ranges_you,
                modifier = Modifier.weight(1f),
                gravity = TextAlign.End
            )

            SpacerSmall(horizontal = true)

            val myPointsText = if (userPoints == 1) stringResource(R.string.endgame_one_point)
            else stringResource(R.string.endgame_pts, userPoints)

            MediumText(
                text = myPointsText,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f)
            )
        }


        Row(modifier = Modifier.fillMaxWidth()) {
            SpacerMedium(horizontal = true)

            MediumText(
                text = R.string.endgame_online_ranges_your_teammate,
                modifier = Modifier.weight(1f),
                gravity = TextAlign.End
            )

            SpacerSmall(horizontal = true)

            val otherPointsText = if (otherPlayerPoints == 1) stringResource(R.string.endgame_one_point)
            else stringResource(R.string.endgame_pts, otherPlayerPoints)

            MediumText(
                text = otherPointsText,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f)
            )
        }
    }
}