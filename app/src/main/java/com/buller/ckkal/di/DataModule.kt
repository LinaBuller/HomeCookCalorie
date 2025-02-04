package com.buller.ckkal.di

import android.content.Context
import com.buller.ckkal.data.repositories.LocalDatabaseRepositoryImpl
import com.buller.ckkal.data.room.DishDao
import com.buller.ckkal.data.room.LocalDatabase
import com.buller.ckkal.data.sources.LocalDatabaseSourceImpl
import com.buller.ckkal.domain.interfaces.LocalDatabaseRepository
import com.buller.ckkal.domain.interfaces.LocalDatabaseSource
import com.buller.ckkal.domain.usecases.DeleteDishUseCase
import com.buller.ckkal.domain.usecases.GetDishesUseCase
import com.buller.ckkal.domain.usecases.SetDishUseCase
import com.buller.ckkal.domain.usecases.UpdateDishUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideDao(database: LocalDatabase): DishDao = database.getDao()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): LocalDatabase =
        LocalDatabase.newInstance(context = context)

    @Provides
    @Singleton
    fun provideLocalSource(dao: DishDao): LocalDatabaseSource =
        LocalDatabaseSourceImpl(dao = dao)

    @Provides
    @Singleton
    fun provideLocalRepository(source: LocalDatabaseSource): LocalDatabaseRepository =
        LocalDatabaseRepositoryImpl(source = source)

    @Provides
    @Singleton
    fun provideDeleteDishUseCase(repository: LocalDatabaseRepository): DeleteDishUseCase =
        DeleteDishUseCase(repository = repository)

    @Provides
    @Singleton
    fun provideSetDishUseCase(repository: LocalDatabaseRepository): SetDishUseCase =
        SetDishUseCase(repository = repository)

    @Provides
    @Singleton
    fun provideGetDishesUseCase(repository: LocalDatabaseRepository): GetDishesUseCase =
        GetDishesUseCase(repository = repository)

    @Provides
    @Singleton
    fun provideUpdateDishUseCase(repository: LocalDatabaseRepository): UpdateDishUseCase =
        UpdateDishUseCase(repository = repository)
}