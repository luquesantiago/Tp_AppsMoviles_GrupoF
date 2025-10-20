// app/src/main/java/com/example/tp_appsmoviles_grupof/database/local/dao/comprasDao.kt
package com.example.tp_appsmoviles_grupof.database.local.dao

import androidx.room.*
import com.example.tp_appsmoviles_grupof.database.local.entities.comprasEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface comprasDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(compra: comprasEntity): Long

    @Query("""
        SELECT * FROM compras
        WHERE userId = :userId
        ORDER BY purchasedAt DESC
    """)
    fun getComprasByUser(userId: Long): Flow<List<comprasEntity>>

    @Query("""
        SELECT trailerKey FROM compras
        WHERE userId = :userId AND movieId = :movieId
        LIMIT 1
    """)
    suspend fun getTrailerKey(userId: Long, movieId: Int): String?
}
