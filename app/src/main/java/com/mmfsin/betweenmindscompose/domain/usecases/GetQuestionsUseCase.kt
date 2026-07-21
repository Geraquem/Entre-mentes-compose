package com.mmfsin.betweenmindscompose.domain.usecases

import com.mmfsin.betweenmindscompose.domain.interfaces.IDataRepository
import com.mmfsin.betweenmindscompose.domain.models.Question
import java.util.Random
import javax.inject.Inject

class GetQuestionsUseCase @Inject constructor(
    //    private val packRepository: IPacksRepository,
    private val dataRepository: IDataRepository
) {
    suspend operator fun invoke(): List<Question> {
        //        val selectedPack = packRepository.getSelectedQPackId()
        val selectedPack = 0
        val questions = dataRepository.getQuestions()
        return questions.filter { it.pack == selectedPack }.shuffled(Random(System.nanoTime()))
    }
}