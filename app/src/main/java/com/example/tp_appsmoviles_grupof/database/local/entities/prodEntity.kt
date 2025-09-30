package com.example.tp_appsmoviles_grupof.database.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.tp_appsmoviles_grupof.database.local.entities.userEntity

    @Entity(
        foreignKeys = [
            ForeignKey(
                entity = userEntity::class,
                parentColumns = ["idUsuario"],
                childColumns = ["idUsuarioCreador"],
                onDelete = ForeignKey.NO_ACTION
            )
        ],
        indices = [Index("idUsuarioCreador")]
    )
    data class prodEntity(
        @PrimaryKey(autoGenerate = true) val idProducto: Int = 0,
        val nombre: String,
        val descripcion: String,
        val precio: Double,
        val stock: Int,
        val idUsuarioCreador: Int
    )
