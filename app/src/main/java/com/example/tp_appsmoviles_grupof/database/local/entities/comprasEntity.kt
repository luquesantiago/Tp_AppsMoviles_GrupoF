package com.example.tp_appsmoviles_grupof.database.local.entities

import androidx.room.Entity

@Entity(
    tableName = "compras",
    primaryKeys = ["userId", "movieId"]
)
data class comprasEntity(
    val userId: Long,
    val movieId: Int,
    val title: String,
    val overview: String?,
    val trailerKey: String?,
    val purchasedAt: Long = System.currentTimeMillis()
)
