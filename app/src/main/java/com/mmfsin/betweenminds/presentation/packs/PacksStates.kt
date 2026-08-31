package com.mmfsin.betweenminds.presentation.packs

import com.mmfsin.betweenminds.domain.models.QuestionsPack
import com.mmfsin.betweenminds.domain.models.RangesPack

data class PacksStates(
    val isLoading: Boolean = true,

    val showSwwDialog: Boolean = false,

    val questionsPacks: List<QuestionsPack> = emptyList(),
    val rangesPacks: List<RangesPack> = emptyList(),

    val selectedQuestionsPack: Int = -1,
    val selectedRangesPack: Int = -1,
)