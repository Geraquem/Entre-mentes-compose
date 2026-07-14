package com.mmfsin.betweenmindscompose.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mmfsin.betweenmindscompose.R
import com.mmfsin.betweenmindscompose.presentation.core.theme.BlueMedium
import com.mmfsin.betweenmindscompose.presentation.core.theme.White

//@Preview
@Composable
fun LoadingFullScreen() {
    Box(Modifier.fillMaxSize().background(Color.White), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            Modifier.size(64.dp),
            strokeWidth = 6.dp,
            color = Color.Blue,
            strokeCap = StrokeCap.Round
        )
    }
}

@Preview
@Composable
fun LoadingMini() {
    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            Modifier.size(40.dp),
            strokeWidth = 6.dp,
            color = Color.Blue,
            strokeCap = StrokeCap.Round
        )
    }
}

@Preview
@Composable
fun LoadingDialogPV() {
    LoadingDialog(R.string.app_name)
}

@Composable
fun LoadingDialog(text: Int? = null) {
    Dialog(onDismissRequest = {}) {
        Box(
            modifier = Modifier.size(200.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(White),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(
                    Modifier.size(64.dp),
                    strokeWidth = 6.dp,
                    color = BlueMedium,
                    strokeCap = StrokeCap.Round
                )

                text?.let {
                    SpacerLarge()
                    SmallText(text = it)
                }
            }
        }
    }
}