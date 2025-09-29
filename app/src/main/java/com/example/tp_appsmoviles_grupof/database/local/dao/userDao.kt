package com.example.tp_appsmoviles_grupof.database.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.example.tp_appsmoviles_grupof.database.local.entities.userEntity

@Dao
interface userDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: userEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUsers(users: List<userEntity>):List<Long>
    @Update
    suspend fun updateUser(user: userEntity): Int
    @Delete
    suspend fun deleteUser(user: userEntity): Int

    @Query("SELECT * FROM userEntity")
    suspend fun getAllUsers(): List<userEntity>
}