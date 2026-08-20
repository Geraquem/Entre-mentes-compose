package com.mmfsin.betweenminds.presentation.instructions.questions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenminds.presentation.core.theme.White

@Preview
@Composable
fun InstrOnlineQuestions() {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(BackgroundBlack)
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.instr_online_first),
            style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 18.sp),
            color = White
        )
    }
}