package com.mmfsin.betweenminds.domain.usecases

import com.mmfsin.betweenminds.domain.interfaces.IDataRepository
import com.mmfsin.betweenminds.domain.interfaces.IPacksRepository
import com.mmfsin.betweenminds.domain.models.Question
import javax.inject.Inject

class GetPackQuestionsUseCase @Inject constructor(
    private val packRepository: IPacksRepository,
    private val dataRepository: IDataRepository
) {
    suspend operator fun invoke(packNumber: Int): List<Question> {
        val questions = dataRepository.getQuestions()
        return questions.filter { it.pack == packNumber }
    }
}