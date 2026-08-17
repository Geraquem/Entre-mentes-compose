package com.mmfsin.betweenmindscompose.presentation.dashboard.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mmfsin.betweenmindscompose.R
import com.mmfsin.betweenmindscompose.presentation.core.components.ButtonCustom
import com.mmfsin.betweenmindscompose.presentation.core.components.MediumText
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerMedium
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerSmall
import com.mmfsin.betweenmindscompose.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenmindscompose.presentation.core.theme.Black
import com.mmfsin.betweenmindscompose.presentation.core.theme.White

@Preview
@Composable
fun ExitGameDialogPV() {
    ExitGameDialog()
}

@Composable
fun ExitGameDialog() {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.9f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                    .background(BackgroundBlack)
                    .padding(vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                MediumText(
                    text = R.string.exit,
                    allCaps = true,
                    color = White,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth()
                    .clip(RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp))
                    .background(White)
                    .padding(12.dp)
            ) {
                MediumText(text = R.string.exit_text_1)
                SpacerSmall()
                MediumText(text = R.string.exit_text_2)
                SpacerMedium()
                Row() {
                    Spacer(Modifier.weight(1f))
                    TextButton(onClick = {}) { MediumText(text = R.string.stay, allCaps = true) }
                    SpacerSmall(horizontal = true)
                    ButtonCustom(
                        onClick = {},
                        textModifier = Modifier.padding(horizontal = 12.dp),
                        text = R.string.exit,
                        color = Black,
                        textColor = White
                    )
                }
            }
        }
    }
}