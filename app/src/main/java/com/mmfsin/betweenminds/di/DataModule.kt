package com.mmfsin.betweenminds.di

import com.mmfsin.betweenminds.data.repository.DataRepository
import com.mmfsin.betweenminds.data.repository.QuestionsOnlineRepository
import com.mmfsin.betweenminds.data.repository.RoomRepository
import com.mmfsin.betweenminds.domain.interfaces.IDataRepository
import com.mmfsin.betweenminds.domain.interfaces.IQuestionsOnlineRepository
import com.mmfsin.betweenminds.domain.interfaces.IRoomRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindDataRepository(repository: DataRepository): IDataRepository

    @Binds
    fun bindRoomRepository(repository: RoomRepository): IRoomRepository

    @Binds
    fun bindIQuestionsOnlineRepository(repository: QuestionsOnlineRepository): IQuestionsOnlineRepository
}