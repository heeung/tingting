package com.alsif.tingting.di

import android.content.Context
import com.alsif.tingting.data.local.AuthDataSource
import com.alsif.tingting.data.local.AuthDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Singleton
    @Provides
    fun provideAuthDatasource(
        @ApplicationContext context: Context
    ) : AuthDataSource = AuthDataSourceImpl(context)
}
