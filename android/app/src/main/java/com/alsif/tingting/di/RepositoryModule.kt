package com.alsif.tingting.di

import com.alsif.tingting.data.repository.BaseRepository
import com.alsif.tingting.data.repository.BaseRepositoryImpl
import com.alsif.tingting.data.service.BaseService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMainRepository(baseService: BaseService): BaseRepository {
        return BaseRepositoryImpl(baseService)
    }
}