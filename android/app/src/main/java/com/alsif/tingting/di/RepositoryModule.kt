package com.alsif.tingting.di

import com.alsif.tingting.data.repository.HomeRepository
import com.alsif.tingting.data.repository.HomeRepositoryImpl
import com.alsif.tingting.data.service.HomeService
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
    fun provideHomeRepository(homeServie: HomeService): HomeRepository {
        return HomeRepositoryImpl(homeServie)
    }
}