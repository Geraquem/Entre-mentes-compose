package com.mmfsin.betweenminds.presentation.packs

import androidx.lifecycle.viewModelScope
import com.mmfsin.betweenminds.domain.usecases.GetPacksUseCase
import com.mmfsin.betweenminds.domain.usecases.GetSelectedQuestionsPackUseCase
import com.mmfsin.betweenminds.domain.usecases.GetSelectedRangesPackUseCase
import com.mmfsin.betweenminds.domain.usecases.UpdateSelectedQuestionsPackUseCase
import com.mmfsin.betweenminds.domain.usecases.UpdateSelectedRangesPackUseCase
import com.mmfsin.betweenminds.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PacksViewModel @Inject constructor(
    private val getPacksUseCase: GetPacksUseCase,
    private val getSelectedQuestionsPackUseCase: GetSelectedQuestionsPackUseCase,
    private val getSelectedRangesPackUseCase: GetSelectedRangesPackUseCase,
    private val updateSelectedQuestionsPackUseCase: UpdateSelectedQuestionsPackUseCase,
    private val updateSelectedRangesPackUseCase: UpdateSelectedRangesPackUseCase,
) : BaseViewModel<PacksStates>(PacksStates()) {

    init {
        getPacks()
        getSelectedPacks()
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

    private fun getSelectedPacks() {
        viewModelScope.launch {
            getSelectedQuestionsPackUseCase().collect { questionsPackNumber ->
                _uiState.update { it.copy(selectedQuestionsPack = questionsPackNumber) }
            }
        }

        viewModelScope.launch {
            getSelectedRangesPackUseCase().collect { rangesPackNumber ->
                _uiState.update { it.copy(selectedRangesPack = rangesPackNumber) }
            }
        }
    }

    fun updateSelectedQuestionsPack(newPack: Int) {
        executeUseCase(
            { updateSelectedQuestionsPackUseCase(newPack) },
            { print("questions pack updated to pack: $newPack") },
            { sww() }
        )
    }

    fun updateSelectedRangesPack(newPack: Int) {
        executeUseCase(
            { updateSelectedRangesPackUseCase(newPack) },
            { print("ranges pack updated to pack: $newPack") },
            { sww() }
        )
    }

    private fun sww() = _uiState.update { it.copy(showSwwDialog = true) }
}