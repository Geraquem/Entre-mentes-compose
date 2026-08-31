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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.domain.models.Pack
import com.mmfsin.betweenminds.domain.models.Question
import com.mmfsin.betweenminds.domain.models.QuestionsPack
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
fun PacksQuestions(
    packs: List<QuestionsPack>,
    selected: Int,
    seeMore: (Int) -> Unit,
    updateQuestionsPack: (Int) -> Unit
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
                    QuestionsPack(
                        pack = pack,
                        selected = selected,
                        seeMore = { seeMore(it) },
                        updateQuestionsPack = { updateQuestionsPack(it) }
                    )
                }
            }
            item { SpacerCustom(64.dp) }
        }
    }
}

@Preview
@Composable
fun QuestionsPackPV() {
    QuestionsPack(
        pack = QuestionsPack(
            pack = Pack(
                packTitle = "Para parejas",
                packDescription = "Compra este pack blablablabla",
                packPrice = "",
                packIcon = "",
                packNumber = 0
            ),
            questions = listOf(
                Question("¿Question 1?", 0),
                Question("¿Question 2?", 0),
                Question("¿Question 3?", 0),
            )
        ),
        selected = 0,
        {}, {}
    )
}

@Composable
fun QuestionsPack(
    pack: QuestionsPack,
    selected: Int,
    seeMore: (Int) -> Unit,
    updateQuestionsPack: (Int) -> Unit
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
            text = R.string.pack_include_questions_as,
            fontWeight = FontWeight.SemiBold
        )

        SpacerSmall()

        Column {
            pack.questions.take(4).forEach { question ->
                Row() {
                    Column {
                        SpacerSmall()
                        Box(
                            modifier = Modifier.size(4.dp)
                                .clip(RoundedCornerShape(50))
                                .background(Black)
                                .padding(top = 8.dp)
                        )
                    }

                    SpacerSmall(horizontal = true)

                    MediumText(
                        text = question.question,
                        fontFamily = alphazet
                    )
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
                onClick = { updateQuestionsPack(pack.pack.packNumber) },
                text = if (selected == pack.pack.packNumber) R.string.pack_selected
                else R.string.pack_selected_btn,
                color = if (selected == pack.pack.packNumber) BlueMedium else Black,
                textColor = White,
                modifier = Modifier.weight(1f)
            )
        }
    }
}