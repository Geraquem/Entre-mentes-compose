package com.mmfsin.betweenminds.presentation.choose.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.presentation.core.components.MediumText
import com.mmfsin.betweenminds.presentation.core.components.SpacerSmall
import com.mmfsin.betweenminds.presentation.core.theme.White
import com.mmfsin.betweenminds.presentation.core.theme.alphazet

@Preview
@Composable
fun PackChosenPV() {
    PackChosen(
        packIcon = "",
        packTitle = "Para parejas",
        {},
    )
}

@Composable
fun PackChosen(
    packIcon: String,
    packTitle: String,
    change: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = packIcon,
            contentDescription = null,
            colorFilter = ColorFilter.tint(White),
            modifier = Modifier.size(24.dp)
        )

        SpacerSmall(horizontal = true)

        MediumText(
            text = packTitle,
            color = White,
            fontFamily = alphazet,
            modifier = Modifier.weight(1f)
        )

        TextButton(onClick = { change() }) {
            MediumText(
                text = R.string.pack_change,
                color = White,
                fontFamily = alphazet,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}