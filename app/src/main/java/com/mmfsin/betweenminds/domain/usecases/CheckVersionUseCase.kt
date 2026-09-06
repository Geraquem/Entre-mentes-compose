package com.mmfsin.betweenminds.domain.usecases

import com.mmfsin.betweenminds.domain.interfaces.IDataRepository
import javax.inject.Inject

class CheckVersionUseCase @Inject constructor(private val repository: IDataRepository) {
    suspend fun execute() = repository.checkVersion()
}