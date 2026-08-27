package com.mmfsin.betweenminds.presentation.packs

import com.mmfsin.betweenminds.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PacksViewModel @Inject constructor(

) : BaseViewModel<PacksStates>(PacksStates()) {

    init {
        getPacks()
    }

    private fun getPacks() {
    }

    private fun sww() = _uiState.update { it.copy(showSwwDialog = true) }
}