package com.mmfsin.betweenminds.presentation.packs.components

import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.domain.models.Pack
import com.mmfsin.betweenminds.domain.models.Ranking
import com.mmfsin.betweenminds.domain.models.RankingsPack
import com.mmfsin.betweenminds.presentation.core.components.ButtonCustom
import com.mmfsin.betweenminds.presentation.core.components.MediumText
import com.mmfsin.betweenminds.presentation.core.components.SmallText
import com.mmfsin.betweenminds.presentation.core.components.SpacerCustom
import com.mmfsin.betweenminds.presentation.core.components.SpacerMedium
import com.mmfsin.betweenminds.presentation.core.components.SpacerMini
import com.mmfsin.betweenminds.presentation.core.components.SpacerSmall
import com.mmfsin.betweenminds.presentation.core.theme.Black
import com.mmfsin.betweenminds.presentation.core.theme.BlueMedium
import com.mmfsin.betweenminds.presentation.core.theme.White
import com.mmfsin.betweenminds.presentation.core.theme.alphazet

@Composable
fun PacksRankings(
    packs: List<RankingsPack>,
    selected: Int,
    purchased: Boolean,
    seeMore: (String) -> Unit,
    updateRangesPack: (Int) -> Unit,
) {
    CompositionLocalProvider(
        LocalOverscrollFactory provides null
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            packs.forEach { pack ->
                item {
                    RankingsPack(
                        pack = pack,
                        selected = selected,
                        purchased = purchased,
                        seeMore = { seeMore(it) },
                        updateRangesPack = { updateRangesPack(it) })
                }
            }
            item { SpacerCustom(64.dp) }
        }
    }
}

@Preview
@Composable
fun RankingsPackPV() {
    RankingsPack(
        pack = RankingsPack(
            pack = Pack(
                packTitle = "Para parejas",
                packDescription = "Compra este pack blablablabla",
                packPrice = "",
                packIcon = "",
                packNumber = 0
            ),
            rankings = listOf(
                Ranking(id = "", text = "Rankin 1", rankings = listOf("A1", "B1", "C1", "D1"), pack = 0),
                Ranking(id = "", text = "Rankin 2", rankings = listOf("A2", "B2", "C2", "D2"), pack = 0),
                Ranking(id = "", text = "Rankin 3", rankings = listOf("A3", "B3", "C3", "D3"), pack = 0),
            ),
        ),
        selected = 0, purchased = true,
        {}, {}
    )
}

@Composable
fun RankingsPack(
    pack: RankingsPack,
    selected: Int,
    purchased: Boolean,
    seeMore: (String) -> Unit,
    updateRangesPack: (Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(White)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = pack.pack.packIcon,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )

            SpacerSmall(horizontal = true)

            MediumText(
                text = pack.pack.packTitle,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )
        }

        SpacerMedium()

        SmallText(text = pack.pack.packDescription)

        SpacerMedium()

        MediumText(
            text = R.string.pack_include_ranges_as,
            fontWeight = FontWeight.SemiBold
        )

        SpacerSmall()

        Column {
            pack.rankings.take(4).forEach { ranking ->
                Row {
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Box(modifier = Modifier.padding(top = 8.dp)) {
                            Box(
                                modifier = Modifier.size(4.dp)
                                    .clip(RoundedCornerShape(50))
                                    .background(Black)
                            )
                        }
                        SpacerSmall(horizontal = true)

                        SmallText(
                            text = "ranking.leftRange",
                            fontFamily = alphazet,
                            gravity = TextAlign.Start,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    SpacerSmall(horizontal = true)

                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.End
                    ) {
                        SmallText(
                            text = "ranking.rightRange",
                            fontFamily = alphazet,
                            gravity = TextAlign.End,
                            modifier = Modifier.weight(1f)
                        )

                        SpacerSmall(horizontal = true)

                        Box(modifier = Modifier.padding(top = 8.dp)) {
                            Box(
                                modifier = Modifier.size(4.dp)
                                    .clip(RoundedCornerShape(50))
                                    .background(Black)
                            )
                        }
                    }
                }
                SpacerMini()
            }
        }

        SpacerMedium()

        Row {
            ButtonCustom(
                onClick = { seeMore(pack.pack.packId) },
                text = R.string.pack_see_more,
                color = Black,
                textColor = White,
            )

            SpacerSmall(horizontal = true)

            val packSelected = (selected == pack.pack.packNumber)
            ButtonCustom(
                onClick = { if (!packSelected) updateRangesPack(pack.pack.packNumber) },
                text = if (packSelected) R.string.pack_selected else R.string.pack_selected_btn,
                color = if (selected == pack.pack.packNumber) BlueMedium else Black,
                textColor = White,
                modifier = Modifier.weight(1f),
                enabled = if (packSelected) true else purchased
            )
        }
    }
}