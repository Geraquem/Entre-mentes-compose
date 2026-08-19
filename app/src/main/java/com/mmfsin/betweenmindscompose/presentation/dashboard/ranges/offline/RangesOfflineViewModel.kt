package com.mmfsin.betweenmindscompose.presentation.dashboard.ranges.offline

import com.mmfsin.betweenmindscompose.domain.usecases.GetRangesUseCase
import com.mmfsin.betweenmindscompose.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RangesOfflineViewModel @Inject constructor(
    private val getRangesUseCase: GetRangesUseCase,
) : BaseViewModel<RangesOfflineStates>(RangesOfflineStates()) {

    init {
        getRanges()
    }

    private fun getRanges() {
        executeUseCase(
            { getRangesUseCase() },
            {},
            {}
        )
    }
}