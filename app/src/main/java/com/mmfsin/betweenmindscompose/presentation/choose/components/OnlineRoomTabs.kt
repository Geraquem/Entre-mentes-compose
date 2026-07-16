package com.mmfsin.betweenmindscompose.presentation.choose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mmfsin.betweenmindscompose.R
import com.mmfsin.betweenmindscompose.presentation.core.components.ButtonCustom
import com.mmfsin.betweenmindscompose.presentation.core.components.MediumText
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerMedium
import com.mmfsin.betweenmindscompose.presentation.core.components.SpacerSmall
import com.mmfsin.betweenmindscompose.presentation.core.theme.BackgroundBlack
import com.mmfsin.betweenmindscompose.presentation.core.theme.BackgroundBlackSoft
import com.mmfsin.betweenmindscompose.presentation.core.theme.BlueMedium
import com.mmfsin.betweenmindscompose.presentation.core.theme.White
import kotlinx.coroutines.launch

@Preview
@Composable
fun OnlineRoomTabsPV() {
    OnlineRoomTabs("", {}, {}, {})
}

@Composable
fun OnlineRoomTabs(
    roomCode: String,
    onRoomCodeChange: (String) -> Unit,
    joinRoom: () -> Unit,
    createRoom: () -> Unit
) {
    val pagerState = rememberPagerState(
        pageCount = { 2 },
        initialPage = 0
    )

    val scope = rememberCoroutineScope()

    Column {
        PrimaryTabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = BackgroundBlack,
            indicator = {
                TabRowDefaults.PrimaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(pagerState.currentPage, matchContentSize = true),
                    width = 100.dp,
                    height = 6.dp,
                    shape = RoundedCornerShape(0),
                    color = BlueMedium
                )
            },
            divider = {}
        ) {
            listOf(
                stringResource(R.string.online_join_room),
                stringResource(R.string.online_create_room)
            ).forEachIndexed { i, txtTab ->
                Tab(
                    selected = pagerState.currentPage == i,
                    onClick = { scope.launch { pagerState.animateScrollToPage(i) } },
                    text = { MediumText(txtTab.uppercase(), color = White) }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            when (page) {
                0 -> JoinRoom(roomCode, onRoomCodeChange) { joinRoom() }
                else -> CreateRoom(createRoom = { createRoom() })
            }
        }
    }
}

@Composable
fun JoinRoom(
    value: String,
    onValueChange: (String) -> Unit,
    joinRoom: () -> Unit
) {
    Column(Modifier.fillMaxWidth().height(172.dp).background(BackgroundBlack)) {
        SpacerMedium()
        MediumText(
            text = stringResource(R.string.online_room_code),
            color = White
        )
        SpacerSmall()
        BasicTextField(
            modifier = Modifier.fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(BackgroundBlackSoft)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            value = value.uppercase(),
            onValueChange = { onValueChange(it.take(5)) },
            singleLine = true,
            textStyle = MaterialTheme.typography.titleLarge.copy(
                color = White,
                letterSpacing = 12.sp,
                fontSize = 28.sp
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                capitalization = KeyboardCapitalization.Words
            ),
            cursorBrush = SolidColor(BlueMedium),
            decorationBox = { innerTextField ->
                Box(contentAlignment = Alignment.CenterStart) {
                    if (value.isEmpty()) {
                        Text(
                            text = "----",
                            color = White,
                            style = MaterialTheme.typography.titleLarge.copy(
                                letterSpacing = 12.sp,
                                fontSize = 28.sp
                            ),
                        )
                    }
                    innerTextField()
                }
            }
        )
        SpacerMedium()

        ButtonCustom(
            onClick = { joinRoom() },
            text = R.string.online_btn_join,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun CreateRoom(createRoom: () -> Unit) {
    Column(Modifier.fillMaxWidth().height(172.dp).background(BackgroundBlack)) {
        SpacerMedium()
        ButtonCustom(
            onClick = { createRoom() },
            text = R.string.online_btn_create,
            modifier = Modifier.fillMaxWidth()
        )
    }
}