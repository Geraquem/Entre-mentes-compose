package com.mmfsin.betweenminds.presentation.menu.components.selector

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmfsin.betweenminds.domain.models.GameType.RANKING
import com.mmfsin.betweenminds.utils.NAV_INSTR_RANKING_ONLINE

@Preview(showBackground = true)
@Composable
fun CardRankingsPV() {
    CardRankings({}, {})
}

@Composable
fun CardRankings(
    openInstructions: (String) -> Unit,
    play: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Spacer(Modifier.weight(1f))

        CardButtons(
            openInstructions = { openInstructions(NAV_INSTR_RANKING_ONLINE) },
            play = { play(RANKING.id) }
        )
    }
}