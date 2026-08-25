package com.mmfsin.betweenminds.presentation.dashboard.questions.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.presentation.core.components.ButtonCustom
import com.mmfsin.betweenminds.presentation.core.components.MediumText
import com.mmfsin.betweenminds.presentation.core.components.SpacerMedium
import com.mmfsin.betweenminds.presentation.core.components.SpacerSmall
import com.mmfsin.betweenminds.presentation.core.theme.Black
import com.mmfsin.betweenminds.presentation.core.theme.BlueMedium
import com.mmfsin.betweenminds.presentation.core.theme.OrangeHard
import com.mmfsin.betweenminds.presentation.core.theme.White
import com.mmfsin.betweenminds.presentation.dashboard.questions.offline.components.SetPlayerName

@Preview
@Composable
fun ChangeNamesQuestionsDialogPV() {
    ChangeNamesQuestionsDialog(
        blueName = "Pedro",
        {},
        orangeName = "",
        {}, {}, {}
    )
}

@Composable
fun ChangeNamesQuestionsDialog(
    blueName: String,
    onBlueNameChanged: (String) -> Unit,
    orangeName: String,
    onOrangeNameChanged: (String) -> Unit,
    startGame: () -> Unit,
    exit: () -> Unit,
) {

    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.9f)
                .clip(RoundedCornerShape(8.dp))
                .background(White)
                .padding(12.dp),
        ) {

            MediumText(
                text = R.string.questions_welcome_four,
            )

            SpacerSmall()

            SetPlayerName(
                name = blueName,
                onNameChange = { onBlueNameChanged(it) },
                hint = R.string.name_blue,
                color = BlueMedium
            )

            SpacerSmall()

            SetPlayerName(
                name = orangeName,
                onNameChange = { onOrangeNameChanged(it) },
                hint = R.string.name_orange,
                color = OrangeHard
            )

            SpacerMedium()

            Row {
                Spacer(Modifier.weight(1f))

                TextButton(onClick = { exit() }) {
                    MediumText(text = R.string.exit, allCaps = true)
                }

                SpacerSmall(horizontal = true)

                ButtonCustom(
                    onClick = { startGame() },
                    textModifier = Modifier.padding(horizontal = 12.dp),
                    text = R.string.online_btn_start,
                    color = Black,
                    textColor = White
                )
            }
        }
    }
}