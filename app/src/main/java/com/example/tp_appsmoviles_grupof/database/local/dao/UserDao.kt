package com.example.tp_appsmoviles_grupof.database.local.dao

import androidx.room.*
import com.example.tp_appsmoviles_grupof.database.local.entities.userEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: userEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUsers(users: List<userEntity>): List<Long>

    @Update suspend fun updateUser(user: userEntity): Int
    @Delete suspend fun deleteUser(user: userEntity): Int

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<userEntity>

    @Query("SELECT * FROM users WHERE nombre = :nombre AND password = :password LIMIT 1")
    suspend fun validarUsuario(nombre: String, password: String): userEntity?

    @Query("SELECT * FROM users WHERE nombre = :nombre LIMIT 1")
    suspend fun buscarPorNombre(nombre: String): userEntity?
}
