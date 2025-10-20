package com.example.tp_appsmoviles_grupof.database.local.dao

import androidx.room.*

import com.example.tp_appsmoviles_grupof.database.local.entities.userEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: userEntity): Long

    @Query("SELECT * FROM users WHERE nombre = :nombre LIMIT 1")
    suspend fun buscarPorNombre(nombre: String): userEntity?

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<userEntity>
}
