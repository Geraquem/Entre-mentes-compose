package com.mmfsin.betweenmindscompose.presentation.core.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mmfsin.betweenmindscompose.R

val barlowRegular = FontFamily(
    Font(R.font.alphazet, weight = FontWeight.Normal),
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodySmall = TextStyle(
        fontFamily = barlowRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),

    bodyLarge = TextStyle(
        fontFamily = barlowRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),

    titleLarge = TextStyle(
        fontFamily = barlowRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
    ),
)