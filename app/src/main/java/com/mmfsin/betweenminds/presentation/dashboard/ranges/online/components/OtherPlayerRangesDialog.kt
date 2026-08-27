package com.mmfsin.betweenminds.presentation.dashboard.ranges.online.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.presentation.core.components.BigText
import com.mmfsin.betweenminds.presentation.core.theme.Black
import com.mmfsin.betweenminds.presentation.core.theme.White
import com.mmfsin.betweenminds.presentation.core.theme.courier

@Preview(showBackground = true)
@Composable
fun OtherPlayerRangesDialog(
) {
    Dialog(
        onDismissRequest = {},
    ) {
        Box(
            modifier = Modifier
                .background(Black, shape = RoundedCornerShape(16.dp))
                .padding(horizontal = 16.dp, vertical = 200.dp),
            contentAlignment = Alignment.Center
        ) {

            BigText(
                text = R.string.ranges_partner_sent,
                allCaps = true,
                color = White,
                fontFamily = courier,
                gravity = TextAlign.Center
            )
        }
    }
}