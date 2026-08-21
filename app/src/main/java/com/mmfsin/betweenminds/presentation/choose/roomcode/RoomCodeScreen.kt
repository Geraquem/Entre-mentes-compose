package com.mmfsin.betweenminds.presentation.choose.roomcode

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.presentation.core.components.CustomToolbar
import com.mmfsin.betweenminds.presentation.core.components.ErrorDialog
import com.mmfsin.betweenminds.presentation.core.components.MediumText
import com.mmfsin.betweenminds.presentation.core.components.SpacerCustom
import com.mmfsin.betweenminds.presentation.core.components.SpacerSmall
import com.mmfsin.betweenminds.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenminds.presentation.core.theme.White
import com.mmfsin.betweenminds.presentation.core.theme.alphazet
import com.mmfsin.betweenminds.presentation.core.theme.courier
import com.mmfsin.betweenminds.utils.NAV_QUESTIONS_OFFLINE
import com.mmfsin.betweenminds.utils.NAV_QUESTIONS_ONLINE_CREATOR
import com.mmfsin.betweenminds.utils.openBedRockActivity

@Preview
@Composable
fun RoomCodeCodePV() {
    RoomCodeContent(
        uiStates = RoomCodeStates(
            roomCode = "afwd0o"
        ),
        {}
    )
}

@Composable
fun RoomCodeScreen(
    viewModel: RoomCodeViewModel = hiltViewModel(),
    goBack: () -> Unit
) {
    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    RoomCodeContent(
        uiStates = uiStates,
        goBack = { goBack() }
    )

    if (uiStates.goToQuestionsCreator) {
        context.openBedRockActivity(NAV_QUESTIONS_ONLINE_CREATOR)
        goBack()
    }

    if (uiStates.goToRangesOnline) {
        goBack()
    }
}

@Composable
fun RoomCodeContent(
    uiStates: RoomCodeStates,
    goBack: () -> Unit,
) {

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.lottie_waiting)
    )

    Scaffold(
        topBar = {
            CustomToolbar(
                goBack = { goBack() },
                showInstructions = false,
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .background(BackgroundBlack)
                .padding(innerPadding)
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            MediumText(
                text = R.string.online_room_code,
                color = White
            )

            SpacerSmall()

            Text(
                text = uiStates.roomCode.uppercase(),
                style = MaterialTheme.typography.titleLarge.copy(
                    color = White,
                    fontSize = 36.sp,
                    letterSpacing = 12.sp,
                    fontFamily = courier
                )
            )

            SpacerCustom(space = 38.dp)

            LottieAnimation(
                composition = composition,
                modifier = Modifier.size(100.dp),
                iterations = LottieConstants.IterateForever
            )

            MediumText(
                text = R.string.online_room_code_waiting,
                color = White,
                fontFamily = alphazet,
                gravity = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
        }

        if (uiStates.showSwwDialog) ErrorDialog(accept = { goBack() })
    }
}