package com.mmfsin.betweenminds.presentation.instructions.ranking.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.presentation.core.components.MediumText
import com.mmfsin.betweenminds.presentation.core.components.SpacerLarge
import com.mmfsin.betweenminds.presentation.core.components.SpacerMedium
import com.mmfsin.betweenminds.presentation.core.components.SpacerSmall
import com.mmfsin.betweenminds.presentation.core.theme.GrayHard
import com.mmfsin.betweenminds.presentation.core.theme.GreenMedium
import com.mmfsin.betweenminds.presentation.core.theme.RedMedium
import com.mmfsin.betweenminds.presentation.core.theme.White
import com.mmfsin.betweenminds.presentation.core.theme.alphazet
import com.mmfsin.betweenminds.presentation.core.theme.courier

@Preview
@Composable
fun RankingInstrComponentsPV() {
    Column {
        RankingInstr()
        SpacerSmall()
        RankingOptions(R.string.instr_ranking_example_1, R.string.instr_ranking_example_2, R.string.instr_ranking_example_3, R.string.instr_ranking_example_4)
        SpacerLarge()
        RankingInstrSolution(R.string.instr_ranking_example_1, R.string.instr_ranking_example_2, R.string.instr_ranking_example_3, R.string.instr_ranking_example_4)
    }
}

@Composable
fun RankingInstr(
    text1: Int? = null,
    text2: Int? = null,
    text3: Int? = null,
    text4: Int? = null,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        MediumText(
            text = R.string.instr_ranking_example_top_text,
            color = White,
            fontFamily = courier,
            gravity = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        SpacerSmall()

        repeat(4) { i ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                MediumText(
                    text = "${i + 1}º",
                    modifier = Modifier.width(26.dp),
                    gravity = TextAlign.End,
                    color = White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = alphazet
                )

                SpacerSmall(horizontal = true)

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            GrayHard,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(vertical = 8.dp, horizontal = 12.dp)
                ) {
                    val text = when (i) {
                        0 -> text1 ?: R.string.empty
                        1 -> text2 ?: R.string.empty
                        2 -> text3 ?: R.string.empty
                        3 -> text4 ?: R.string.empty
                        else -> R.string.empty
                    }

                    MediumText(
                        text = text,
                        color = White,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
            if (i != 3) SpacerSmall()
        }
    }
}

@Composable
fun RankingInstrSolution(
    text1: Int? = null,
    text2: Int? = null,
    text3: Int? = null,
    text4: Int? = null,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        MediumText(
            text = R.string.instr_ranking_example_top_text,
            color = White,
            fontFamily = courier,
            gravity = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        SpacerSmall()

        repeat(4) { i ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                MediumText(
                    text = "${i + 1}º",
                    modifier = Modifier.width(26.dp),
                    gravity = TextAlign.End,
                    color = White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = alphazet
                )

                SpacerSmall(horizontal = true)

                val background = when (i) {
                    0 -> GreenMedium
                    else -> RedMedium
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            background,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(vertical = 8.dp, horizontal = 12.dp)
                ) {
                    val text = when (i) {
                        0 -> text1 ?: R.string.empty
                        1 -> text2 ?: R.string.empty
                        2 -> text3 ?: R.string.empty
                        3 -> text4 ?: R.string.empty
                        else -> R.string.empty
                    }

                    val correctPosition = when (i) {
                        0 -> "1º"
                        1 -> "3º"
                        2 -> "4º"
                        3 -> "2º"
                        else -> "?"
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        MediumText(
                            text = text,
                            color = White,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.weight(1f)
                        )

                        MediumText(
                            text = correctPosition,
                            color = White,
                            fontFamily = alphazet,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
            if (i != 3) SpacerSmall()
        }
    }
}

@Composable
fun RankingOptions(
    text1: Int? = null,
    text2: Int? = null,
    text3: Int? = null,
    text4: Int? = null,
) {
    Column(Modifier.fillMaxWidth()) {
        Row {
            MediumText(
                text = text1 ?: R.string.empty,
                color = White,
                gravity = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
            MediumText(
                text = text2 ?: R.string.empty,
                color = White,
                gravity = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
        }
        SpacerMedium()
        Row {
            MediumText(
                text = text3 ?: R.string.empty,
                color = White,
                gravity = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
            MediumText(
                text = text4 ?: R.string.empty,
                color = White,
                gravity = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
