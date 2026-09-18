package com.mmfsin.betweenminds.presentation.menu.components.selector

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmfsin.betweenminds.R
import com.mmfsin.betweenminds.domain.models.GameType.RANGES
import com.mmfsin.betweenminds.presentation.core.components.MediumText
import com.mmfsin.betweenminds.presentation.core.components.SmallText
import com.mmfsin.betweenminds.presentation.core.components.SpacerMini
import com.mmfsin.betweenminds.presentation.core.components.SpacerSmall
import com.mmfsin.betweenminds.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenminds.presentation.instructions.ranges.components.RangesSliderInstr
import com.mmfsin.betweenminds.utils.NAV_INSTR_RANGES_ONLINE

@Preview(showBackground = true)
@Composable
fun CardRangesPV() {
    CardRanges({}, {})
}

@Composable
fun CardRanges(
    openInstructions: (String) -> Unit,
    play: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Spacer(Modifier.weight(1f))

        RangesSliderInstr(
            showBullseye = true,
            showSlider = false,
            bullseyePosition = 20f,
            sliderPosition = 0f,
            showRangeLimits = false
        )
        ExampleRangeLimits(
            R.string.selector_ranges_example_1_left,
            R.string.selector_ranges_example_1_right
        )
        SpacerMini()
        ExampleRangeLimits(
            R.string.selector_ranges_example_2_left,
            R.string.selector_ranges_example_2_right,
            showArrows = false
        )

        Spacer(Modifier.weight(1f))

        SmallText(
            text = R.string.selector_ranges_description_1,
            color = BackgroundBlack
        )

        Spacer(Modifier.weight(1f))

        CardButtons(
            openInstructions = { openInstructions(NAV_INSTR_RANGES_ONLINE) },
            play = { play(RANGES.id) }
        )
    }
}


@Composable
fun ExampleRangeLimits(leftRange: Int, rightRange: Int, showArrows: Boolean = true) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            if (showArrows) {
                Icon(
                    painterResource(R.drawable.ic_long_arrow), null,
                    tint = BackgroundBlack
                )
            }
            MediumText(
                text = leftRange,
                color = BackgroundBlack,
                modifier = Modifier.align(Alignment.Start).padding(start = 8.dp)
            )
        }

        SpacerSmall(horizontal = true)

        Column(
            modifier = Modifier.weight(1f)
        ) {
            if (showArrows) {
                Icon(
                    painterResource(R.drawable.ic_long_arrow), null,
                    tint = BackgroundBlack,
                    modifier = Modifier.graphicsLayer { scaleX = -1f }
                )
            }
            MediumText(
                text = rightRange,
                color = BackgroundBlack,
                modifier = Modifier.align(Alignment.End).padding(end = 8.dp),
                gravity = TextAlign.End
            )
        }
    }
}