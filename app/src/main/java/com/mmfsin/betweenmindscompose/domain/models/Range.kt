package com.mmfsin.betweenmindscompose.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Range(
    val leftRange: String,
    val rightRange: String,
    val pack: Int,
) : Parcelable
