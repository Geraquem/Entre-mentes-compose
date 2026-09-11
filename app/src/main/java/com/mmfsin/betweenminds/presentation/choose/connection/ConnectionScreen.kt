package com.mmfsin.betweenminds.presentation.choose.connection

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.mmfsin.betweenminds.domain.models.GameType.RANGES
import com.mmfsin.betweenminds.presentation.choose.connection.components.ConnectionTitle
import com.mmfsin.betweenminds.presentation.choose.connection.components.JoinedErrorDialog
import com.mmfsin.betweenminds.presentation.choose.connection.components.OnlineRoomTabs
import com.mmfsin.betweenminds.presentation.choose.connection.components.PackChosen
import com.mmfsin.betweenminds.presentation.core.components.BigText
import com.mmfsin.betweenminds.presentation.core.components.ButtonCustom
import com.mmfsin.betweenminds.presentation.core.components.CustomToolbar
import com.mmfsin.betweenminds.presentation.core.components.ErrorDialog
import com.mmfsin.betweenminds.presentation.core.components.LoadingFullScreen
import com.mmfsin.betweenminds.presentation.core.components.MediumText
import com.mmfsin.betweenminds.presentation.core.components.SpacerLarge
import com.mmfsin.betweenminds.presentation.core.components.SpacerMedium
import com.mmfsin.betweenminds.presentation.core.components.SpacerSmall
import com.mmfsin.betweenminds.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenminds.presentation.core.theme.White
import com.mmfsin.betweenminds.utils.NAV_QUESTIONS_OFFLINE
import com.mmfsin.betweenminds.utils.NAV_QUESTIONS_ONLINE_JOINED
import com.mmfsin.betweenminds.utils.NAV_RANGES_OFFLINE
import com.mmfsin.betweenminds.utils.NAV_RANGES_ONLINE
import com.mmfsin.betweenminds.utils.NAV_RANKING_OFFLINE
import com.mmfsin.betweenminds.utils.openBedRockActivity

@Preview
@Composable
fun ConnectionScreenPV() {
    ConnectionContent(
        uiStates = ConnectionStates(
            gameType = RANGES,
            isLoading = false,
        ),
        {}, {}, {}, {},
        {}, {}, {}, {},
        {},
    )
}

@Composable
fun ConnectionScreen(
    viewModel: ConnectionViewModel = hiltViewModel(),
    goBack: () -> Unit,
    roomCreated: (String, String) -> Unit,
    goToPacks: (Int) -> Unit
) {
    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ConnectionContent(
        uiStates = uiStates,
        goBack = { goBack() },
        goToInstructions = { context.goToInstructions(uiStates.instructionsNavGraph) },
        goToPacks = { goToPacks(uiStates.packsTab) },
        onRoomCodeChange = { value -> viewModel.onRoomCodeChanged(value) },
        joinRoom = { viewModel.joinRoom() },
        createRoom = { viewModel.createRoom() },
        playOffline = { viewModel.playOffline() },
        showJoinErrorDialog = { viewModel.joinedError(it) },
        showSwwDialog = { viewModel.sww(it) }
    )

    /**************** ONLINE ****************/

    if (uiStates.createOnlineRoom) {
        roomCreated(uiStates.roomCodeCreated, uiStates.gameTypeId)
        viewModel.createOnlineRoom(false)
    }

    if (uiStates.joinToQuestionsOnline) {
        context.openBedRockActivity(
            NAV_QUESTIONS_ONLINE_JOINED,
            uiStates.roomCodeToJoin
        )
        viewModel.joinToQuestionsOnline(false)
    }

    if (uiStates.joinToRangesOnline) {
        context.openBedRockActivity(
            NAV_RANGES_ONLINE,
            uiStates.roomCodeToJoin,
            boolArgs = false //isCreator = no
        )
        viewModel.joinToRangesOnline(false)
    }

    /**************** OFFLINE ****************/

    if (uiStates.startQuestionsOffline) {
        context.openBedRockActivity(NAV_QUESTIONS_OFFLINE)
        viewModel.startQuestionsOffline(false)
    }

    if (uiStates.startRangesOffline) {
        context.openBedRockActivity(NAV_RANGES_OFFLINE)
        viewModel.startRangesOffline(false)
    }

    if (uiStates.startRankingsOffline) {
        context.openBedRockActivity(NAV_RANKING_OFFLINE)
        viewModel.startRankingsOffline(false)
    }
}

@Composable
fun ConnectionContent(
    uiStates: ConnectionStates,
    goBack: () -> Unit,
    goToInstructions: () -> Unit,
    goToPacks: () -> Unit,
    onRoomCodeChange: (String) -> Unit,
    joinRoom: () -> Unit,
    createRoom: () -> Unit,
    playOffline: () -> Unit,
    showJoinErrorDialog: (Boolean) -> Unit,
    showSwwDialog: (Boolean) -> Unit,
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
        Column(
            Modifier.fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            SpacerSmall()

            uiStates.gameType?.let { type -> ConnectionTitle(type) }

            SpacerMedium()

            PackChosen(
                packIcon = uiStates.packIcon,
                packTitle = uiStates.packTitle,
                change = { goToPacks() },
            )

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

            SpacerLarge()
        }

        if (uiStates.showErrorJoinedDialog) JoinedErrorDialog { showJoinErrorDialog(false) }
        if (uiStates.showSwwDialog) ErrorDialog(accept = { showSwwDialog(false) })

        if (uiStates.isLoading) LoadingFullScreen()

        BackHandler { goBack() }
    }
}

private fun Context.goToInstructions(navGraph: String) = openBedRockActivity(navGraph)
