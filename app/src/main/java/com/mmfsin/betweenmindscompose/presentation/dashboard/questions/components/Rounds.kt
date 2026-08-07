package com.mmfsin.betweenmindscompose.presentation.dashboard.questions.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mmfsin.betweenmindscompose.presentation.core.theme.Background
import com.mmfsin.betweenmindscompose.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenmindscompose.presentation.core.theme.GrayHard

@Preview
@Composable
fun RoundsPV() {
    Rounds(listOf(12, 5, 2, 18))
}

@Composable
fun Rounds(points: List<Int>) {
    Row(
        modifier = Modifier.fillMaxWidth().background(Background),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        points.forEach { p ->
            Text(p.toString())
        }
    }
}