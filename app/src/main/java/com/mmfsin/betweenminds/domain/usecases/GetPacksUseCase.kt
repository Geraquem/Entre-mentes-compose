package com.mmfsin.betweenminds.domain.usecases

import com.mmfsin.betweenminds.domain.interfaces.IDataRepository
import com.mmfsin.betweenminds.domain.interfaces.IPacksRepository
import com.mmfsin.betweenminds.domain.models.Packs
import javax.inject.Inject

class GetPacksUseCase @Inject constructor(
    private val packsRepository: IPacksRepository,
    private val dataRepository: IDataRepository,
) {
    suspend operator fun invoke(): Packs {
        val packs = packsRepository.getAllPacks()
        val questions = dataRepository.getQuestions()
        val ranges = dataRepository.getRanges()

        val questionsPacks = packs.questionsPacks.map { p ->
            p.copy(questions = questions
                .filter { it.pack == p.pack.packNumber }
                .shuffled()
            )
        }

        val rangesPacks = packs.rangesPacks.map { p ->
            p.copy(ranges = ranges
                .filter { it.pack == p.pack.packNumber }
                .shuffled()
            )
        }

        return packs.copy(
            questionsPacks = questionsPacks,
            rangesPacks = rangesPacks
        )
    }
}