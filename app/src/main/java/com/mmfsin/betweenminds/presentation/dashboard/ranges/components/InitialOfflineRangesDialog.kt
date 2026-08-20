package com.mmfsin.betweenminds.presentation.dashboard.ranges.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.presentation.core.components.BigText
import com.mmfsin.betweenminds.presentation.core.components.ButtonCustom
import com.mmfsin.betweenminds.presentation.core.components.SpacerMedium
import com.mmfsin.betweenminds.presentation.core.components.SpacerSmall
import com.mmfsin.betweenminds.presentation.core.theme.Black
import com.mmfsin.betweenminds.presentation.core.theme.White
import com.mmfsin.betweenminds.presentation.core.theme.alphazet

@Preview
@Composable
fun InitialOfflineRangesDialogPV() {
    InitialOfflineRangesDialog({}, {}, true)
}

@Composable
fun InitialOfflineRangesDialog(
    startGame: () -> Unit,
    howToPlay: () -> Unit,
    isLoading: Boolean
) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.9f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BigText(
                text = R.string.selector_ranges,
                allCaps = true,
                color = White,
                fontFamily = alphazet,
                fontSize = 30.sp
            )

            SpacerSmall()

            Column(
                modifier = Modifier.fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(White)
                    .padding(12.dp)
            ) {
                Text(
                    text = stringResource(R.string.ranges_start_resume),
                    style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 18.sp)
                )

                SpacerSmall()

                Text(
                    text = stringResource(R.string.ranges_start_resume_two),
                    style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 18.sp)
                )

                SpacerSmall()

                Text(
                    text = stringResource(R.string.ranges_start_resume_three),
                    style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 18.sp)
                )
                SpacerSmall()

                SpacerMedium()

                ButtonCustom(
                    onClick = { startGame() },
                    text = if (isLoading) R.string.online_btn_wait else R.string.online_btn_start,
                    modifier = Modifier.fillMaxWidth(),
                    color = Black,
                    textColor = White,
                    enabled = !isLoading
                )

                SpacerSmall()

                ButtonCustom(
                    onClick = { howToPlay() },
                    text = R.string.ranges_start_instructions,
                    modifier = Modifier.fillMaxWidth(),
                    color = Black,
                    textColor = White
                )
            }
        }
    }
}