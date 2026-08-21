package com.mmfsin.betweenminds.presentation.choose

import android.content.Context
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
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.domain.models.GameType.QUESTIONS
import com.mmfsin.betweenminds.domain.models.GameType.RANGES
import com.mmfsin.betweenminds.presentation.choose.components.ChooseTitle
import com.mmfsin.betweenminds.presentation.choose.components.OnlineRoomTabs
import com.mmfsin.betweenminds.presentation.core.components.BigText
import com.mmfsin.betweenminds.presentation.core.components.ButtonCustom
import com.mmfsin.betweenminds.presentation.core.components.CustomToolbar
import com.mmfsin.betweenminds.presentation.core.components.MediumText
import com.mmfsin.betweenminds.presentation.core.components.SpacerLarge
import com.mmfsin.betweenminds.presentation.core.components.SpacerMedium
import com.mmfsin.betweenminds.presentation.core.components.SpacerSmall
import com.mmfsin.betweenminds.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenminds.presentation.core.theme.RedHard
import com.mmfsin.betweenminds.presentation.core.theme.White
import com.mmfsin.betweenminds.utils.NAV_INSTR_QUESTIONS_ONLINE
import com.mmfsin.betweenminds.utils.NAV_INSTR_RANGES_ONLINE
import com.mmfsin.betweenminds.utils.NAV_QUESTIONS_OFFLINE
import com.mmfsin.betweenminds.utils.openBedRockActivity

@Preview
@Composable
fun ChoosePV() {
    ChooseContent(
        uiStates = ChooseStates(
            gameType = RANGES
        ),
        {}, {}, {}, {},
        {}, {},
    )
}

@Composable
fun ChooseScreen(
    viewModel: ChooseViewModel = hiltViewModel(),
    goBack: () -> Unit,
    joinRoom: (String) -> Unit,
    roomCreated: (String, String) -> Unit
) {
    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ChooseContent(
        uiStates = uiStates,
        goBack = { goBack() },
        goToInstructions = {
            uiStates.gameType?.let { type ->
                when (type) {
                    QUESTIONS -> context.goToInstructions(NAV_INSTR_QUESTIONS_ONLINE)
                    RANGES -> context.goToInstructions(NAV_INSTR_RANGES_ONLINE)
                }
            }
        },
        onRoomCodeChange = { value -> viewModel.onRoomCodeChanged(value) },
        joinRoom = { joinRoom(it) },
        createRoom = { viewModel.createRoom() },
        playOffline = { viewModel.playOffline() }
    )

    if (uiStates.createRoomQuestionsOnline) {
        roomCreated(uiStates.roomCodeCreated, uiStates.gameTypeId)
        viewModel.createRoomQuestionsOnline(false)
    }

    if (uiStates.startQuestionsOffline) {
        context.openBedRockActivity(NAV_QUESTIONS_OFFLINE)
        viewModel.startQuestionsOffline(false)
    }
}

@Composable
fun ChooseContent(
    uiStates: ChooseStates,
    goBack: () -> Unit,
    goToInstructions: () -> Unit,
    onRoomCodeChange: (String) -> Unit,
    joinRoom: (String) -> Unit,
    createRoom: () -> Unit,
    playOffline: () -> Unit,
) {

    Scaffold(
        topBar = {
            CustomToolbar(
                goBack = { goBack() },
                goToInstructions = { goToInstructions() }
            )
        },
        containerColor = BackgroundBlack
    )
    { innerPadding ->
        Column(Modifier.padding(innerPadding).padding(horizontal = 16.dp)) {
            SpacerLarge()
            uiStates.gameType?.let { type -> ChooseTitle(type) }
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
                roomCode = uiStates.roomCodeToJoin,
                onRoomCodeChange = { onRoomCodeChange(it) },
                joinRoom = { joinRoom(it) },
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
                    text = stringResource(R.string.offline_mode),
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

private fun Context.goToInstructions(navGraph: String) = openBedRockActivity(navGraph)
