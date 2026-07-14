package com.mmfsin.betweenmindscompose.presentation.menu

import com.mmfsin.betweenmindscompose.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(

) : BaseViewModel<MenuStates>(MenuStates()) {

    private fun checkVersion() {
        executeUseCase(
            {},
            {},
            {}
        )
    }
}