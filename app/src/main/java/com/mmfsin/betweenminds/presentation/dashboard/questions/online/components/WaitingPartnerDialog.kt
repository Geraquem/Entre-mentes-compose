package com.mmfsin.betweenminds.presentation.dashboard.questions.online.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.presentation.core.components.MediumText
import com.mmfsin.betweenminds.presentation.core.components.SpacerSmall
import com.mmfsin.betweenminds.presentation.core.theme.GrayHard
import com.mmfsin.betweenminds.presentation.core.theme.White
import com.mmfsin.betweenminds.presentation.core.theme.alphazet

@Preview
@Composable
fun WaitingPartnerDialogPV() {
    WaitingPartnerDialog({})
}

@Composable
fun WaitingPartnerDialog(goBack: () -> Unit) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.waiting)
    )

    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )

    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.9f)
                .clip(RoundedCornerShape(8.dp))
                .background(White)
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp, top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            IconButton(
                onClick = { goBack() },
                modifier = Modifier.align(Alignment.Start)
            ) {
                Icon(
                    painterResource(R.drawable.ic_exit), null,
                    tint = GrayHard
                )
            }

            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier.size(140.dp)
            )

            SpacerSmall()

            MediumText(
                text = R.string.online_wainting_other_player,
                fontFamily = alphazet
            )
        }
    }
}
