package com.mmfsin.betweenminds.di

import android.content.Context
import androidx.room.Room
import com.mmfsin.betweenminds.data.ddbb.RoomConfiguration
import com.mmfsin.betweenminds.data.ddbb.daos.PacksDAO
import com.mmfsin.betweenminds.data.ddbb.daos.QuestionsDAO
import com.mmfsin.betweenminds.data.ddbb.daos.RangesDAO
import com.mmfsin.betweenminds.utils.DDBB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): RoomConfiguration =
        Room.databaseBuilder(
            context,
            RoomConfiguration::class.java,
            DDBB_NAME
        )
            .fallbackToDestructiveMigration(true)
            .build()

    @Provides
    fun provideQuestionsDAO(db: RoomConfiguration): QuestionsDAO = db.questionsDAO()

    @Provides
    fun provideRangesDAO(db: RoomConfiguration): RangesDAO = db.rangesDAO()

    @Provides
    fun providePacksDAO(db: RoomConfiguration): PacksDAO = db.packsDAO()
}