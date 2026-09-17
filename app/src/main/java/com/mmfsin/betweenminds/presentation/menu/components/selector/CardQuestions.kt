@file:OptIn(ExperimentalMaterial3Api::class)

package com.mmfsin.betweenminds.presentation.menu.components.selector

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.presentation.core.components.ButtonCustom
import com.mmfsin.betweenminds.presentation.core.components.ButtonCustomIcon
import com.mmfsin.betweenminds.presentation.core.components.MediumText
import com.mmfsin.betweenminds.presentation.core.components.SmallText
import com.mmfsin.betweenminds.presentation.core.components.SpacerLarge
import com.mmfsin.betweenminds.presentation.core.components.SpacerMini
import com.mmfsin.betweenminds.presentation.core.components.SpacerSmall
import com.mmfsin.betweenminds.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenminds.presentation.core.theme.BlueMedium
import com.mmfsin.betweenminds.presentation.core.theme.OrangeMedium
import com.mmfsin.betweenminds.presentation.core.theme.courier
import com.mmfsin.betweenminds.presentation.core.theme.kineks

@Preview(showBackground = true)
@Composable
fun CardQuestions() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Spacer(Modifier.weight(1f))

        val questions = listOf(
            R.string.selector_questions_example1,
            R.string.selector_questions_example2,
            R.string.selector_questions_example3,
            //            R.string.selector_questions_example4,
        )

        questions.forEach { question ->
            SmallText(
                text = question,
                color = BackgroundBlack,
                fontFamily = courier,
                fontWeight = FontWeight.SemiBold,
                gravity = TextAlign.Center,
            )
            SpacerSmall()
        }

        SpacerLarge()

        Column(
            Modifier.padding(horizontal = 24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Icon(
                    painterResource(R.drawable.ic_human_up), null,
                    modifier = Modifier.size(80.dp),
                    tint = BlueMedium
                )
                Icon(
                    painterResource(R.drawable.ic_human_down), null,
                    modifier = Modifier.size(80.dp),
                    tint = OrangeMedium
                )
            }

            SpacerMini()

            //            Row(modifier = Modifier.fillMaxWidth()) {
            //
            //                SmallText(
            //                    text = R.string.selector_questions_you,
            //                    color = BlueMedium,
            //                    gravity = TextAlign.Center,
            //                    fontWeight = FontWeight.SemiBold,
            //                    modifier = Modifier.weight(1f)
            //                )
            //                SmallText(
            //                    text = R.string.selector_questions_your_partner,
            //                    color = OrangeMedium,
            //                    gravity = TextAlign.Center,
            //                    fontWeight = FontWeight.SemiBold,
            //                    modifier = Modifier.weight(1f)
            //                )
            //            }
            //
            //            SpacerMini()

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                MediumText(
                    text = "65%",
                    color = BlueMedium,
                    fontSize = 30.sp,
                    fontFamily = kineks
                )
                MediumText(
                    text = "35%",
                    color = OrangeMedium,
                    fontSize = 30.sp,
                    fontFamily = kineks
                )
            }
        }

        Spacer(Modifier.weight(1f))

        SmallText(
            text = R.string.selector_questions_description_1,
            color = BackgroundBlack,
        )

        SpacerSmall()

        SmallText(
            text = R.string.selector_questions_description_2,
            color = BackgroundBlack,
        )

        Spacer(Modifier.weight(1f))

        ButtonCustomIcon(
            onClick = {},
            text = R.string.selector_how_to_play,
            icon = R.drawable.ic_book,
            color = BackgroundBlack
        )
    }
}