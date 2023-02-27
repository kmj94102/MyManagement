package com.example.mymanagement.database.di

import android.app.Application
import androidx.room.Room
import com.example.mymanagement.database.FavoriteDao
import com.example.mymanagement.database.MyManagementDatabase
import com.example.mymanagement.database.SubwayDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        application: Application
    ): MyManagementDatabase =
        Room
            .databaseBuilder(application, MyManagementDatabase::class.java, "myManagement.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideSubwayDao(
        database: MyManagementDatabase
    ): SubwayDao = database.subwayDao()

    @Provides
    @Singleton
    fun provideFavoriteDao(
        database: MyManagementDatabase
    ): FavoriteDao = database.favoriteDao()

}