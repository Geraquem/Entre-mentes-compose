@file:OptIn(ExperimentalMaterial3Api::class)

package com.mmfsin.betweenmindscompose.presentation.menu.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmfsin.betweenmindscompose.presentation.core.theme.RedHard

@Preview
@Composable
fun SelectorSheetPV() {
    SelectorSheet({})
}

@Composable
fun SelectorSheet(
    onDismiss: () -> Unit
) {
    ModalBottomSheet(onDismissRequest = { onDismiss() }) {
        Box(Modifier.fillMaxWidth().height(100.dp).background(RedHard))
    }
}