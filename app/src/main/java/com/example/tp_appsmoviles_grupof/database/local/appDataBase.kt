package com.example.tp_appsmoviles_grupof.database.local

import androidx.room.Database

@Database(entities = [userEntity::class], version = 1)
abstract class appDataBase: RoomDatabase() {
abstract fun userDao(): userDao

}