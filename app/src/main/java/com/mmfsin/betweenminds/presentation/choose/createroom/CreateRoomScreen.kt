package com.mmfsin.betweenminds.presentation.choose.createroom

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.presentation.core.components.CustomToolbar
import com.mmfsin.betweenminds.presentation.core.components.MediumText
import com.mmfsin.betweenminds.presentation.core.components.SpacerCustom
import com.mmfsin.betweenminds.presentation.core.components.SpacerSmall
import com.mmfsin.betweenminds.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenminds.presentation.core.theme.White
import com.mmfsin.betweenminds.presentation.core.theme.alphazet
import com.mmfsin.betweenminds.presentation.core.theme.courier

@Preview
@Composable
fun RoomCodeCodePV() {
    RoomCodeContent(
        uiStates = CreateRoomStates(
            roomCode = "afwd0o"
        ),
        {}
    )
}

@Composable
fun RoomCodeScreen(
    viewModel: CreateRoomViewModel = hiltViewModel(),
    goBack: () -> Unit
) {
    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()
    RoomCodeContent(
        uiStates = uiStates,
        goBack = { goBack() }
    )
}

@Composable
fun RoomCodeContent(
    uiStates: CreateRoomStates,
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
                modifier = Modifier.size(100.dp)
            )

            MediumText(
                text = R.string.online_room_code_waiting,
                color = White,
                fontFamily = alphazet,
                gravity = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }
    }
}