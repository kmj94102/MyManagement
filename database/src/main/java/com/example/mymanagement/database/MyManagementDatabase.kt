package com.example.mymanagement.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mymanagement.database.entity.FavoriteEntity
import com.example.mymanagement.database.entity.StationEntity
import com.example.mymanagement.database.entity.TokenEntity

@Database(
    entities = [
        StationEntity::class,
        FavoriteEntity::class,
        TokenEntity::class
    ],
    version = 3,
    exportSchema = true
)
abstract class MyManagementDatabase : RoomDatabase() {

    abstract fun subwayDao(): SubwayDao

    abstract fun favoriteDao(): FavoriteDao

    abstract fun tokenDao(): TokenDao

}