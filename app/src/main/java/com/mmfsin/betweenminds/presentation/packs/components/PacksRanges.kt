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
import com.mmfsin.betweenminds.domain.models.Range
import com.mmfsin.betweenminds.domain.models.RangesPack
import com.mmfsin.betweenminds.presentation.core.components.ButtonCustom
import com.mmfsin.betweenminds.presentation.core.components.MediumText
import com.mmfsin.betweenminds.presentation.core.components.SmallText
import com.mmfsin.betweenminds.presentation.core.components.SpacerMedium
import com.mmfsin.betweenminds.presentation.core.components.SpacerMini
import com.mmfsin.betweenminds.presentation.core.components.SpacerSmall
import com.mmfsin.betweenminds.presentation.core.theme.Black
import com.mmfsin.betweenminds.presentation.core.theme.BlueMedium
import com.mmfsin.betweenminds.presentation.core.theme.White
import com.mmfsin.betweenminds.presentation.core.theme.alphazet

@Composable
fun PacksRanges(
    packs: List<RangesPack>,
    selected: Int,
    seeMore: (Int) -> Unit,
    updateRangesPack: (Int) -> Unit
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
                    RangesPack(
                        pack = pack,
                        selected = selected,
                        seeMore = { seeMore(it) },
                        updateRangesPack = { updateRangesPack(it) })
                }
            }
        }
    }
}

@Preview
@Composable
fun RangesPackPV() {
    RangesPack(
        pack = RangesPack(
            pack = Pack(
                packTitle = "Para parejas",
                packDescription = "Compra este pack blablablabla",
                packPrice = "",
                packIcon = "",
                packNumber = 0
            ),
            ranges = listOf(
                Range(leftRange = "Rango izquierdo 1", rightRange = "Rango derecho 1", pack = 0),
                Range(leftRange = "Rango izquierdo 2", rightRange = "Rango derecho 2", pack = 0),
                Range(leftRange = "Rango izquierdo 3", rightRange = "Rango derecho 3", pack = 0),
            ),
        ),
        selected = 0,
        {}, {}
    )
}

@Composable
fun RangesPack(
    pack: RangesPack,
    selected: Int,
    seeMore: (Int) -> Unit,
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
            pack.ranges.take(4).forEach { range ->
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
                            text = range.leftRange,
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
                            text = range.rightRange,
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
                onClick = { seeMore(pack.pack.packNumber) },
                text = R.string.pack_see_more,
                color = Black,
                textColor = White,
            )

            SpacerSmall(horizontal = true)

            ButtonCustom(
                onClick = { updateRangesPack(pack.pack.packNumber) },
                text = if (selected == pack.pack.packNumber) R.string.pack_selected
                else R.string.pack_selected_btn,
                color = if (selected == pack.pack.packNumber) BlueMedium else Black,
                textColor = White,
                modifier = Modifier.weight(1f)
            )
        }
    }
}