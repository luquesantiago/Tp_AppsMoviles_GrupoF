package com.example.tp_appsmoviles_grupof.database.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tp_appsmoviles_grupof.database.local.dao.UserDao
import com.example.tp_appsmoviles_grupof.database.local.dao.comprasDao
import com.example.tp_appsmoviles_grupof.database.local.entities.userEntity
import com.example.tp_appsmoviles_grupof.database.local.entities.comprasEntity



@Database(entities = [userEntity::class, comprasEntity::class], version = 7,  exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun comprasDao(): comprasDao
}