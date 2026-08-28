package com.mmfsin.betweenminds.presentation.packs

import com.mmfsin.betweenminds.domain.usecases.GetPacksUseCase
import com.mmfsin.betweenminds.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PacksViewModel @Inject constructor(
    private val getPacksUseCase: GetPacksUseCase,
) : BaseViewModel<PacksStates>(PacksStates()) {

    init {
        getPacks()
    }

    private fun getPacks() {
        executeUseCase(
            { getPacksUseCase() },
            { packs ->
                _uiState.update {
                    it.copy(
                        questionsPacks = packs.questionsPacks,
                        rangesPacks = packs.rangesPacks,
                        isLoading = false
                    )
                }
            },
            { sww() }
        )
    }

    private fun sww() = _uiState.update { it.copy(showSwwDialog = true) }
}