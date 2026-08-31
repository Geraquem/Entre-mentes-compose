package com.mmfsin.betweenminds.presentation.packs.detail

import com.mmfsin.betweenminds.domain.models.Question
import com.mmfsin.betweenminds.domain.models.Range

data class PackDetailStates(
    val isLoading: Boolean = true,

    val showSwwDialog: Boolean = false,

    val packIcon: String = "",
    val packTitle: String = "",
    val packDescription: String = "",

    val questions: List<Question> = emptyList(),
    val ranges: List<Range> = emptyList()
)
