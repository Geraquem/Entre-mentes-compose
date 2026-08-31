package com.mmfsin.betweenminds.presentation.packs.detail

import androidx.lifecycle.SavedStateHandle
import com.mmfsin.betweenminds.domain.usecases.GetPackByIdUseCase
import com.mmfsin.betweenminds.domain.usecases.GetPackQuestionsUseCase
import com.mmfsin.betweenminds.domain.usecases.GetPackRangesUseCase
import com.mmfsin.betweenminds.presentation.core.base.BaseViewModel
import com.mmfsin.betweenminds.utils.QUESTIONS
import com.mmfsin.betweenminds.utils.RANGES
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PackDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPackByIdUseCase: GetPackByIdUseCase,
    private val getPackQuestionsUseCase: GetPackQuestionsUseCase,
    private val getPackRangesUseCase: GetPackRangesUseCase
) : BaseViewModel<PackDetailStates>(PackDetailStates()) {

    private val packId: String? = savedStateHandle["packId"]

    init {
        getPack()
    }

    private fun getPack() {
        if (packId == null) sww()
        else {
            executeUseCase(
                { getPackByIdUseCase(packId) },
                { pack ->
                    if (pack == null) sww()
                    else {
                        _uiState.update {
                            it.copy(
                                packIcon = pack.packIcon,
                                packTitle = pack.packTitle,
                                packDescription = pack.packDescription,
                            )
                        }
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
                    },
                    { sww() }
                )
            }

            RANGES -> {
                executeUseCase(
                    { getPackRangesUseCase(packNumber) },
                    { data ->
                        _uiState.update { it.copy(ranges = data) }
                    },
                    { sww() }
                )
            }

            else -> sww()
        }
        _uiState.update { it.copy(isLoading = false) }
    }

    private fun sww() = _uiState.update { it.copy(showSwwDialog = true) }
}