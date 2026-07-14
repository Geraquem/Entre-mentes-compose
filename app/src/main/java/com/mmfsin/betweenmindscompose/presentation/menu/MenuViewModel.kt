package com.mmfsin.betweenmindscompose.presentation.menu

import androidx.lifecycle.viewModelScope
import com.mmfsin.betweenmindscompose.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(

) : BaseViewModel<MenuStates>(MenuStates()) {

    init {
        checkVersion()
    }

    private fun checkVersion() {
        viewModelScope.launch {
            delay(2000)
            _uiState.update { it.copy(isLoading = false) }

            delay(1000)
            _uiState.update { it.copy(positonButtons = 0f) }
        }
    }

    fun showSelectorSheet(value: Boolean) = _uiState.update { it.copy(showSelectorSheet = value) }
}