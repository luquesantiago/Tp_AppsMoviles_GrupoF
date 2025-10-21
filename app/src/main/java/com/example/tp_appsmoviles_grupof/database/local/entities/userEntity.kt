package com.example.tp_appsmoviles_grupof.database.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo


@Entity(tableName = "users")
data class userEntity(
    @PrimaryKey(autoGenerate = true)  val idUser: Long = 0L,
    @ColumnInfo(name = "nombre")   val nombre: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "email")    val email: String? = null,
    @ColumnInfo(name = "telefono") val telefono: String? = null,
    @ColumnInfo(name = "direccion")val direccion: String? = null,
)

