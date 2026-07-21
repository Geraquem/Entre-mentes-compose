package com.mmfsin.betweenmindscompose.di

import com.mmfsin.betweenmindscompose.data.repository.DataRepository
import com.mmfsin.betweenmindscompose.domain.interfaces.IDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DataRepositoryModule {
    @Binds
    fun bind(repository: DataRepository): IDataRepository
}