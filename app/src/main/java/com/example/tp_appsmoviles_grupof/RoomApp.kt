package com.example.tp_appsmoviles_grupof.database.local

import android.content.Context
import androidx.room.Room

object RoomApp {
    lateinit var database: AppDatabase
        private set

    fun init(context: Context) {

        if (!::database.isInitialized) {
            database = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "movies-db"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
