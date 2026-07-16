package com.mmfsin.betweenmindscompose.presentation.choose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.betweenmindscompose.R
import com.mmfsin.betweenmindscompose.domain.models.GameType
import com.mmfsin.betweenmindscompose.presentation.choose.components.ChooseTitle
import com.mmfsin.betweenmindscompose.presentation.choose.components.ChooseToolbar
import com.mmfsin.betweenmindscompose.presentation.choose.components.OnlineRoomTabs
import com.mmfsin.betweenmindscompose.presentation.core.components.BigText
import com.mmfsin.betweenmindscompose.presentation.core.components.ButtonCustom
import com.mmfsin.betweenmindscompose.presentation.core.components.MediumText
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerLarge
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerMedium
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerMini
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerSmall
import com.mmfsin.betweenmindscompose.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenmindscompose.presentation.core.theme.RedHard
import com.mmfsin.betweenmindscompose.presentation.core.theme.White
import com.mmfsin.betweenmindscompose.utils.NAV_QUESTIONS_OFFLINE
import com.mmfsin.betweenmindscompose.utils.openBedRockActivity

@Preview
@Composable
fun ChoosePV() {
    ChooseContent(
        uiState = ChooseStates(
            gameType = GameType.RANGES
        ),
        {}, {}, {}, {},
    )
}

@Composable
fun ChooseScreen(viewModel: ChooseViewModel = hiltViewModel()) {
    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()
    ChooseContent(
        uiState = uiStates,
        onRoomCodeChange = { value -> viewModel.onRoomCodeChanged(value) },
        joinRoom = {},
        createRoom = {},
        playOffline = { viewModel.playOffline() }
    )

    val context = LocalContext.current
    if (uiStates.startQuestionsOffline) {
        context.openBedRockActivity(NAV_QUESTIONS_OFFLINE)
        viewModel.startQuestionsOffline(false)
    }

}

@Composable
fun ChooseContent(
    uiState: ChooseStates,
    onRoomCodeChange: (String) -> Unit,
    joinRoom: () -> Unit,
    createRoom: () -> Unit,
    playOffline: () -> Unit,
) {

    Scaffold(
        topBar = {
            ChooseToolbar(
                goBack = {},
                goToInstructions = {}
            )
        },
        containerColor = BackgroundBlack
    )
    { innerPadding ->
        Column(Modifier.padding(innerPadding).padding(horizontal = 16.dp)) {
            SpacerLarge()
            uiState.gameType?.let { type -> ChooseTitle(type) }
            SpacerMedium()
            Box(Modifier.fillMaxWidth().height(25.dp).background(RedHard))
            SpacerMedium()

            /*****************************************************************************************/
            /************************************* ONLINE ********************************************/
            /*****************************************************************************************/

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painterResource(R.drawable.ic_dot), null,
                    tint = White,
                    modifier = Modifier.size(16.dp)
                )
                SpacerSmall(horizontal = true)
                BigText(
                    text = stringResource(R.string.online_mode),
                    allCaps = true,
                    color = White
                )
                SpacerMedium(horizontal = true)
                Icon(painterResource(R.drawable.ic_mobile), null, tint = White)
                Icon(painterResource(R.drawable.ic_mobile), null, tint = White)
            }

            SpacerSmall()
            MediumText(text = stringResource(R.string.online_mode_desc), color = White)
            SpacerSmall()

            OnlineRoomTabs(
                roomCode = uiState.roomCode,
                onRoomCodeChange = { onRoomCodeChange(it) },
                joinRoom = { joinRoom() },
                createRoom = { createRoom() }
            )

            SpacerLarge()

            Box(Modifier.fillMaxWidth().padding(horizontal = 24.dp).height(1.dp).background(White))

            SpacerLarge()

            /*****************************************************************************************/
            /************************************ OFFLINE ********************************************/
            /*****************************************************************************************/

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painterResource(R.drawable.ic_dot), null,
                    tint = White,
                    modifier = Modifier.size(16.dp)
                )
                SpacerSmall(horizontal = true)
                BigText(
                    text = stringResource(R.string.online_offline),
                    allCaps = true,
                    color = White
                )
                SpacerMedium(horizontal = true)
                Icon(painterResource(R.drawable.ic_mobile), null, tint = White)
            }
            SpacerSmall()
            MediumText(text = stringResource(R.string.online_offline_desc), color = White)
            SpacerLarge()
            ButtonCustom(
                onClick = { playOffline() },
                text = R.string.online_btn_start,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}