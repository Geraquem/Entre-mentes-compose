@file:OptIn(ExperimentalMaterial3Api::class)

package com.mmfsin.betweenmindscompose.presentation.core.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmfsin.betweenmindscompose.R
import com.mmfsin.betweenmindscompose.presentation.core.theme.White

@Preview
@Composable
fun ToolbarPV() {
    Toolbar(text = "Toolbar", iconBackVisible = true, onBackClick = {}, rightIcon = R.drawable.ic_error, onRightIconClick = {})
}

@Composable
fun Toolbar(
    text: String = "",
    boldText: Boolean = false,
    iconBackVisible: Boolean = false,
    onBackClick: () -> Unit = {},
    rightIcon: Int? = null,
    onRightIconClick: () -> Unit = {},
) {
    TopAppBar(
        //        modifier = Modifier
        //            .zIndex(1f)
        //            .shadow(
        //                elevation = 4.dp,
        //                clip = false
        //            ),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = White
        ),
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = text,
                    fontWeight = if (boldText) FontWeight.Bold else FontWeight.Normal,
                    textAlign = if (boldText) TextAlign.Center else TextAlign.Start,
                    modifier = Modifier.weight(1f).padding(start = if (iconBackVisible) 2.dp else 16.dp),
                )

                if (rightIcon != null) {
                    IconButton(onClick = onRightIconClick, modifier = Modifier.padding(start = 0.dp)) {
                        Icon(
                            painter = painterResource(rightIcon),
                            contentDescription = stringResource(R.string.empty),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        },

        navigationIcon = {
            if (iconBackVisible) {
                IconButton(onClick = onBackClick, modifier = Modifier.padding(start = 0.dp)) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_back),
                        contentDescription = stringResource(R.string.cd_arrow_back),
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
    )
}