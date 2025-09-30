package com.example.tp_appsmoviles_grupof

import android.app.Application
import com.example.tp_appsmoviles_grupof.database.local.appDataBase
import androidx.room.Room


class RoomApp: Application(){
    companion object{
        lateinit var database: appDataBase
    }

    override fun onCreate() {
        super.onCreate()

        database= Room.databaseBuilder(applicationContext, appDataBase::class.java, "appDataBase").build()
    }
}