package com.example.tp_appsmoviles_grupof.database.local.entities
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.tp_appsmoviles_grupof.database.local.entities.userEntity
import com.example.tp_appsmoviles_grupof.database.local.entities.prodEntity

    @Entity(
        foreignKeys = [
            ForeignKey(entity = userEntity::class, parentColumns = ["idUsuario"], childColumns = ["idUsuario"]),
            ForeignKey(entity = prodEntity::class, parentColumns = ["idProducto"], childColumns = ["idProducto"])
        ]
    )
    data class historialCompraEntity(
        @PrimaryKey(autoGenerate = true) val idHistorial: Int = 0,
        val idUsuario: Int,
        val idProducto: Int,
        val fechaCompra: Long,
        val cantidad: Int
    )
