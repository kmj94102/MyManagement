package com.example.mymanagement.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mymanagement.database.entity.FavoriteEntity
import com.example.mymanagement.database.entity.StationEntity

@Database(
    entities = [
        StationEntity::class,
        FavoriteEntity::class
    ],
    version = 2,
    exportSchema = true
)
abstract class MyManagementDatabase : RoomDatabase() {

    abstract fun subwayDao(): SubwayDao

    abstract fun favoriteDao(): FavoriteDao

}