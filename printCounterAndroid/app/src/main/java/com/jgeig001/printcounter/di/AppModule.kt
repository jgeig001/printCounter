package com.jgeig001.printcounter.di

import android.content.Context
import com.jgeig001.printcounter.model.api.DataLoaderService
import com.jgeig001.printcounter.model.repository.PrintJobRepository
import com.jgeig001.printcounter.model.repository.PrinterRepository
import com.jgeig001.printcounter.model.repository.UserRepository
import com.jgeig001.printcounter.utils.UserManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideUserRepository(dataLoaderService: DataLoaderService): UserRepository {
        return UserRepository(dataLoaderService)
    }

    @Singleton
    @Provides
    fun providePrinterRepository(dataLoaderService: DataLoaderService): PrinterRepository {
        return PrinterRepository(dataLoaderService)
    }

    @Singleton
    @Provides
    fun providePrintJobRepository(dataLoaderService: DataLoaderService): PrintJobRepository {
        return PrintJobRepository(dataLoaderService)
    }

    @Singleton
    @Provides
    fun provideDataLoaderService(): DataLoaderService {
        return DataLoaderService()
    }

    @Singleton
    @Provides
    fun provideUserManager(
        @ApplicationContext context: Context,
        userRepository: UserRepository
    ): UserManager {
        return UserManager(context, userRepository)
    }

}
