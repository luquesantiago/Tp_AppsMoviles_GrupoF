package com.example.tp_appsmoviles_grupof.database.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tp_appsmoviles_grupof.database.local.entities.prodEntity

@Dao
interface prodDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertProd(prod: prodEntity): Long

    @Update
    suspend fun updateProd(prod: prodEntity): Int

    @Query("SELECT * FROM prodEntity where stock>=1")
    suspend fun getAllProds(): List<prodEntity>

    @Query("SELECT * FROM prodEntity where idUsuarioCreador = :idUsuario")
    suspend fun productosDelUsuario(idUsuario: Int): List<prodEntity>

    @Query("UPDATE prodEntity SET stock = stock - :cantidad WHERE idProducto = :idProducto & stock >= :cantidad")
    suspend fun venderProducto(idProducto: Int, cantidad: Int)

    @Query("UPDATE prodEntity SET stock = stock + :cantidad WHERE idProducto = :idProducto")
    suspend fun agregarUnidades(idProducto: Int, cantidad: Int)




}