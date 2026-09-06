package com.mmfsin.betweenminds.presentation.menu

import androidx.lifecycle.viewModelScope
import com.mmfsin.betweenminds.domain.usecases.SetFreePacksUseCase
import com.mmfsin.betweenminds.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val setFreePacksUseCase: SetFreePacksUseCase,
) : BaseViewModel<MenuStates>(MenuStates()) {

    init {
        checkVersion()
    }

    private fun checkVersion() {
        viewModelScope.launch {
            delay(0)
            _uiState.update { it.copy(isLoading = false) }

            delay(0)
            _uiState.update { it.copy(positonButtons = 0f) }
        }
    }

    fun showSelectorSheet(value: Boolean) = _uiState.update { it.copy(showSelectorSheet = value) }

    fun setFreePacks() {
        executeUseCase(
            { setFreePacksUseCase.execute() },
            { println("Free packs set") },
            {},
        )
    }
}