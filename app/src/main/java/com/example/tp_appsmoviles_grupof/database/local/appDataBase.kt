package com.example.tp_appsmoviles_grupof.database.local

import androidx.room.Database
import com.example.tp_appsmoviles_grupof.database.local.dao.userDao
import com.example.tp_appsmoviles_grupof.database.local.entities.userEntity


import androidx.room.RoomDatabase

@Database(entities = [userEntity::class], version = 1)
abstract class appDataBase: RoomDatabase() {
abstract fun userDao(): userDao





}