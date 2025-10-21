
package com.example.tp_appsmoviles_grupof.database.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "compras",
    foreignKeys = [
        ForeignKey(
            entity = userEntity::class,
            parentColumns = ["idUser"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["userId", "movieId"], unique = true)]
)
data class comprasEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idCompra") val idCompra: Long = 0L,

    @ColumnInfo(name = "userId") val userId: Long,
    @ColumnInfo(name = "movieId") val movieId: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "overview") val overview: String?,
    @ColumnInfo(name = "trailerKey") val trailerKey: String?,
    @ColumnInfo(name = "purchasedAt") val purchasedAt: Long = System.currentTimeMillis()
)
