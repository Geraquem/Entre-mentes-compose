package com.mmfsin.betweenmindscompose.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmfsin.betweenmindscompose.presentation.core.components.SmallText
import com.mmfsin.betweenmindscompose.R
import com.mmfsin.betweenmindscompose.presentation.core.theme.GrayHard
import com.mmfsin.betweenmindscompose.presentation.core.theme.White

@Preview(showBackground = true)
@Composable
fun MyOutlinedTextFieldPV() {
    Column() {
        MyWhiteTextField("Texto de prueba", {}, R.string.app_name)
        SpacerMedium()
        MyOutlinedTextField("Texto de prueba", {}, R.string.app_name)
    }
}

@Composable
fun MyWhiteTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: Int,
    singleLine: Boolean = true,
    minLines: Int = 1,
    maxLines: Int = 1,
    maxLength: Int = 50,
    imeAction: ImeAction = ImeAction.Next
) {
    Column() {
        Column(modifier = Modifier.fillMaxWidth()) {
            SmallText(label)
            SpacerMini()
            BasicTextField(
                modifier = Modifier.fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(White)
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                value = value, onValueChange = { onValueChange(it.take(maxLength)) },
                singleLine = singleLine,
                textStyle = MaterialTheme.typography.bodyLarge,
                minLines = minLines,
                maxLines = maxLines,

                keyboardOptions = KeyboardOptions(
                    imeAction = imeAction,
                    capitalization = KeyboardCapitalization.Sentences
                )
            )
            SpacerMini()
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "${value.length}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    "/",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    "$maxLength",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun MyOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: Int,
    singleLine: Boolean = true,
    minLines: Int = 1,
    maxLines: Int = 1,
    maxLength: Int = 50,
    imeAction: ImeAction = ImeAction.Next
) {
    Box(
        modifier = Modifier
            .border(1.dp, GrayHard, RoundedCornerShape(8.dp))
            .padding(12.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            SmallText(label)
            SpacerMini()
            BasicTextField(
                modifier = Modifier.fillMaxWidth(),
                value = value, onValueChange = { onValueChange(it.take(maxLength)) },
                singleLine = singleLine,
                textStyle = MaterialTheme.typography.bodyLarge,
                minLines = minLines,
                maxLines = maxLines,
                keyboardOptions = KeyboardOptions(
                    imeAction = imeAction,
                    capitalization = KeyboardCapitalization.Sentences
                )
            )
            SpacerMini()
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "${value.length}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    "/",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    "$maxLength",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}