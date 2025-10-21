package com.example.tp_appsmoviles_grupof.database.local.dao

import androidx.room.*
import com.example.tp_appsmoviles_grupof.database.local.entities.comprasEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface comprasDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(compra: comprasEntity): Long

    @Query("SELECT EXISTS(SELECT 1 FROM compras WHERE userId = :userId AND movieId = :movieId)")
    suspend fun isPurchased(userId: Long, movieId: Int): Boolean

    @Query("SELECT * FROM compras WHERE userId = :userId ORDER BY purchasedAt DESC")
    fun getComprasByUser(userId: Long): Flow<List<comprasEntity>>

    @Query("SELECT trailerKey FROM compras WHERE userId = :userId AND movieId = :movieId LIMIT 1")
    suspend fun getTrailerKey(userId: Long, movieId: Int): String?
}
