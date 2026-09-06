package com.mmfsin.betweenminds.presentation.packs.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mmfsin.betweenminds.domain.usecases.CheckIfPurchasedPacksUseCase
import com.mmfsin.betweenminds.domain.usecases.GetPackByIdUseCase
import com.mmfsin.betweenminds.domain.usecases.GetPackQuestionsUseCase
import com.mmfsin.betweenminds.domain.usecases.GetPackRangesUseCase
import com.mmfsin.betweenminds.domain.usecases.GetSelectedQuestionsPackUseCase
import com.mmfsin.betweenminds.domain.usecases.GetSelectedRangesPackUseCase
import com.mmfsin.betweenminds.domain.usecases.UpdateSelectedQuestionsPackUseCase
import com.mmfsin.betweenminds.domain.usecases.UpdateSelectedRangesPackUseCase
import com.mmfsin.betweenminds.presentation.core.base.BaseViewModel
import com.mmfsin.betweenminds.utils.QUESTIONS
import com.mmfsin.betweenminds.utils.RANGES
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PackDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPackByIdUseCase: GetPackByIdUseCase,
    private val getPackQuestionsUseCase: GetPackQuestionsUseCase,
    private val getPackRangesUseCase: GetPackRangesUseCase,
    private val getSelectedQuestionsPackUseCase: GetSelectedQuestionsPackUseCase,
    private val getSelectedRangesPackUseCase: GetSelectedRangesPackUseCase,
    private val updateSelectedQuestionsPackUseCase: UpdateSelectedQuestionsPackUseCase,
    private val updateSelectedRangesPackUseCase: UpdateSelectedRangesPackUseCase,
    private val checkIfPurchasedPacksUseCase: CheckIfPurchasedPacksUseCase,
) : BaseViewModel<PackDetailStates>(PackDetailStates()) {

    private val packId: String? = savedStateHandle["packId"]

    init {
        getPack()
        checkIfPurchasedPacks()
    }

    private fun getPack() {
        if (packId == null) sww()
        else {
            executeUseCase(
                { getPackByIdUseCase(packId) },
                { pack ->
                    if (pack == null) sww()
                    else {
                        _uiState.update { it.copy(pack = pack) }
                        getData(pack.packType, pack.packNumber)
                    }
                },
                { sww() }
            )
        }
    }

    private fun getData(type: String, packNumber: Int) {
        when (type) {
            QUESTIONS -> {
                executeUseCase(
                    { getPackQuestionsUseCase(packNumber) },
                    { data ->
                        _uiState.update { it.copy(questions = data) }
                        getSelectedQuestionsPack()
                    },
                    { sww() }
                )
            }

            RANGES -> {
                executeUseCase(
                    { getPackRangesUseCase(packNumber) },
                    { data ->
                        _uiState.update { it.copy(ranges = data) }
                        getSelectedRangesPack()
                    },
                    { sww() }
                )
            }

            else -> sww()
        }
    }


    private fun getSelectedQuestionsPack() {
        val states = uiState.value
        viewModelScope.launch {
            getSelectedQuestionsPackUseCase().collect { selectedNumber ->
                _uiState.update { it.copy(selected = states.pack?.packNumber == selectedNumber) }
            }
        }
        _uiState.update { it.copy(isLoading = false) }
    }

    private fun getSelectedRangesPack() {
        val states = uiState.value
        viewModelScope.launch {
            getSelectedRangesPackUseCase().collect { selectedNumber ->
                _uiState.update { it.copy(selected = states.pack?.packNumber == selectedNumber) }
            }
        }
        _uiState.update { it.copy(isLoading = false) }
    }

    fun selectPack() {
        val pack = uiState.value.pack
        if (pack == null) sww()
        else {
            when (pack.packType) {
                QUESTIONS -> {
                    executeUseCase(
                        { updateSelectedQuestionsPackUseCase(pack.packNumber) },
                        { },
                        { sww() }
                    )
                }

                RANGES -> {
                    executeUseCase(
                        { updateSelectedRangesPackUseCase(pack.packNumber) },
                        {},
                        { sww() }
                    )
                }

                else -> sww()
            }
        }
    }

    private fun checkIfPurchasedPacks() {
        executeUseCase(
            { checkIfPurchasedPacksUseCase.execute() },
            { data -> _uiState.update { it.copy(purchased = data.first) } },
            { sww() },
        )
    }

    private fun sww() = _uiState.update { it.copy(showSwwDialog = true) }
}