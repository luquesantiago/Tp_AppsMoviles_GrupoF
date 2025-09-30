package com.example.tp_appsmoviles_grupof.database.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity
data class userEntity(
    @PrimaryKey(autoGenerate = true) val idUser: Int,
    @ColumnInfo(name = "nombre") val nombre: String,
    @ColumnInfo(name = "password")val password: String,
    @ColumnInfo(name = "email")val email: String,
    @ColumnInfo(name = "telefono")val telefono: String,
    @ColumnInfo(name = "direccion")val direccion: String,
   )