package com.mmfsin.betweenminds.presentation.packs

import android.app.Activity
import androidx.lifecycle.viewModelScope
import com.mmfsin.betweenminds.data.billing.BillingManager
import com.mmfsin.betweenminds.domain.usecases.CheckIfPurchasedPacksUseCase
import com.mmfsin.betweenminds.domain.usecases.GetPacksUseCase
import com.mmfsin.betweenminds.domain.usecases.GetSelectedQuestionsPackUseCase
import com.mmfsin.betweenminds.domain.usecases.GetSelectedRangesPackUseCase
import com.mmfsin.betweenminds.domain.usecases.GetSelectedRankingsPackUseCase
import com.mmfsin.betweenminds.domain.usecases.UpdatePacksPurchasedUseCase
import com.mmfsin.betweenminds.domain.usecases.UpdateSelectedQuestionsPackUseCase
import com.mmfsin.betweenminds.domain.usecases.UpdateSelectedRangesPackUseCase
import com.mmfsin.betweenminds.domain.usecases.UpdateSelectedRankingsPackUseCase
import com.mmfsin.betweenminds.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PacksViewModel @Inject constructor(
    private val billingManager: BillingManager,
    private val getPacksUseCase: GetPacksUseCase,
    private val getSelectedQuestionsPackUseCase: GetSelectedQuestionsPackUseCase,
    private val getSelectedRangesPackUseCase: GetSelectedRangesPackUseCase,
    private val getSelectedRankingsPackUseCase: GetSelectedRankingsPackUseCase,
    private val updateSelectedQuestionsPackUseCase: UpdateSelectedQuestionsPackUseCase,
    private val updateSelectedRangesPackUseCase: UpdateSelectedRangesPackUseCase,
    private val updateSelectedRankingsPackUseCase: UpdateSelectedRankingsPackUseCase,
    private val checkIfPurchasedPacksUseCase: CheckIfPurchasedPacksUseCase,
    private val updatedPacksPurchasedUseCase: UpdatePacksPurchasedUseCase,
) : BaseViewModel<PacksStates>(PacksStates()) {

    init {
        viewModelScope.launch {
            billingManager.purchaseResult.collect { success ->
                if (success) updatedPacksPurchased()
            }
        }

        getPacks()
        getSelectedPacks()
        checkIfPurchasedPacks()
    }

    private fun getPacks() {
        executeUseCase(
            { getPacksUseCase() },
            { packs ->
                _uiState.update {
                    it.copy(
                        questionsPacks = packs.questionsPacks,
                        rangesPacks = packs.rangesPacks,
                        rankingsPacks = packs.rankingsPacks,
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

        viewModelScope.launch {
            getSelectedRankingsPackUseCase().collect { rankingsPackNumber ->
                _uiState.update { it.copy(selectedRankingPack = rankingsPackNumber) }
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

    fun updateSelectedRankingsPack(newPack: Int) {
        executeUseCase(
            { updateSelectedRankingsPackUseCase(newPack) },
            { print("rankings pack updated to pack: $newPack") },
            { sww() }
        )
    }

    private fun checkIfPurchasedPacks() {
        executeUseCase(
            { checkIfPurchasedPacksUseCase.execute() },
            { data ->
                _uiState.update {
                    it.copy(
                        packsPurchased = data.first,
                        packsPrice = data.second
                    )
                }
            },
            { sww() },
        )
    }

    fun purchasePacks(activity: Activity) {
        billingManager.purchaseAllPacks(activity)
    }

    fun updatedPacksPurchased() {
        executeUseCase(
            { updatedPacksPurchasedUseCase() },
            { _uiState.update { it.copy(packsPurchased = true) } },
            {},
        )
    }

    private fun sww() = _uiState.update { it.copy(showSwwDialog = true) }
}