package com.mmfsin.betweenmindscompose.presentation.core.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import com.mmfsin.betweenmindscompose.R
import com.mmfsin.betweenmindscompose.presentation.core.theme.Black

@Preview(showBackground = true)
@Composable
fun TextsPV() {
    Column {
        SmallText(text = R.string.app_name)
        MediumText(text = R.string.app_name)
        BigText(text = R.string.app_name)
    }
}

@Composable
fun SmallText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Black,
    fontWeight: FontWeight = FontWeight.Normal,
    gravity: TextAlign = TextAlign.Start,
    fontSize: TextUnit = MaterialTheme.typography.titleLarge.fontSize
) {
    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.bodySmall,
        color = color,
        fontWeight = fontWeight,
        textAlign = gravity,
        fontSize = fontSize
    )
}

@Composable
fun SmallText(
    text: Int,
    modifier: Modifier = Modifier,
    color: Color = Black,
    fontWeight: FontWeight = FontWeight.Normal,
    gravity: TextAlign = TextAlign.Start,
    fontSize: TextUnit = MaterialTheme.typography.titleLarge.fontSize
) {
    Text(
        text = stringResource(text),
        modifier = modifier,
        style = MaterialTheme.typography.bodySmall,
        color = color,
        fontWeight = fontWeight,
        textAlign = gravity,
        fontSize = fontSize
    )
}

@Composable
fun MediumText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Black,
    fontWeight: FontWeight = FontWeight.Normal,
    gravity: TextAlign = TextAlign.Start,
    fontSize: TextUnit = MaterialTheme.typography.titleLarge.fontSize
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        color = color,
        fontWeight = fontWeight,
        textAlign = gravity,
        fontSize = fontSize
    )
}

@Composable
fun MediumText(
    text: Int,
    modifier: Modifier = Modifier,
    color: Color = Black,
    fontWeight: FontWeight = FontWeight.Normal,
    gravity: TextAlign = TextAlign.Start,
    fontSize: TextUnit = MaterialTheme.typography.titleLarge.fontSize
) {
    Text(
        modifier = modifier,
        text = stringResource(text),
        style = MaterialTheme.typography.bodyLarge,
        color = color,
        fontWeight = fontWeight,
        textAlign = gravity,
        fontSize = fontSize
    )
}

@Composable
fun BigText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Black,
    fontWeight: FontWeight = FontWeight.Normal,
    gravity: TextAlign = TextAlign.Start,
    fontSize: TextUnit = MaterialTheme.typography.titleLarge.fontSize
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.titleLarge,
        color = color,
        fontWeight = fontWeight,
        textAlign = gravity,
        fontSize = fontSize
    )
}

@Composable
fun BigText(
    text: Int,
    modifier: Modifier = Modifier,
    color: Color = Black,
    fontWeight: FontWeight = FontWeight.Normal,
    gravity: TextAlign = TextAlign.Start,
    fontSize: TextUnit = MaterialTheme.typography.titleLarge.fontSize
) {
    Text(
        modifier = modifier,
        text = stringResource(text),
        style = MaterialTheme.typography.titleLarge,
        color = color,
        fontWeight = fontWeight,
        textAlign = gravity,
        fontSize = fontSize
    )
}