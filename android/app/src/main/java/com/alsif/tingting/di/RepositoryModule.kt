package com.alsif.tingting.di

import com.alsif.tingting.data.repository.HomeRepository
import com.alsif.tingting.data.repository.HomeRepositoryImpl
import com.alsif.tingting.data.repository.LikeRepository
import com.alsif.tingting.data.repository.LikeRepositoryImpl
import com.alsif.tingting.data.repository.ReserveRepository
import com.alsif.tingting.data.repository.ReserveRepositoryImpl
import com.alsif.tingting.data.repository.SearchRepository
import com.alsif.tingting.data.repository.SearchRepositoryImpl
import com.alsif.tingting.data.service.HomeService
import com.alsif.tingting.data.service.LikeService
import com.alsif.tingting.data.service.SearchService
import com.alsif.tingtinqg.data.service.ReserveService
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
    fun provideHomeRepository(homeService: HomeService): HomeRepository {
        return HomeRepositoryImpl(homeService)
    }

    @Provides
    @Singleton
    fun provideLikeRepository(likeService: LikeService): LikeRepository {
        return LikeRepositoryImpl(likeService)
    }

    @Provides
    @Singleton
    fun provideSearchRepository(searchService: SearchService): SearchRepository {
        return SearchRepositoryImpl(searchService)
    }

    @Provides
    @Singleton
    fun provideReserveRepository(reserveService: ReserveService): ReserveRepository {
        return ReserveRepositoryImpl(reserveService)
    }
}