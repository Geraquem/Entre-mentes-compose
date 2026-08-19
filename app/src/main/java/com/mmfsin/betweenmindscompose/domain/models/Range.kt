package com.mmfsin.betweenmindscompose.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Range(
    val leftRange: String,
    val rightRange: String,
    val pack: Int,
) : Parcelable

enum class RangePhaseType {
    SHOW_BULLSEYE,
    MOVE_ARROW,
    NEXT_ROUND,
    RESULTS
}