package com.mmfsin.betweenminds.presentation.choose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.presentation.core.components.ButtonCustom
import com.mmfsin.betweenminds.presentation.core.components.MediumText
import com.mmfsin.betweenminds.presentation.core.components.SpacerMedium
import com.mmfsin.betweenminds.presentation.core.components.SpacerSmall
import com.mmfsin.betweenminds.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenminds.presentation.core.theme.Black
import com.mmfsin.betweenminds.presentation.core.theme.White

@Preview
@Composable
fun JoinedErrorDialogPV() {
    JoinedErrorDialog { }
}

@Composable
fun JoinedErrorDialog(onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = { onDismiss() },
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
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                MediumText(
                    text = R.string.error_joining_room_title,
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

                Text(
                    text = stringResource(R.string.error_joining_room_description),
                    style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 18.sp),
                )

                SpacerSmall()

                Text(
                    text = stringResource(R.string.error_joining_room_description_2),
                    style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 18.sp),
                )

                SpacerMedium()

                ButtonCustom(
                    onClick = { onDismiss() },
                    modifier = Modifier.fillMaxWidth(),
                    text = R.string.error_btn,
                    color = Black,
                    textColor = White
                )
            }
        }
    }
}