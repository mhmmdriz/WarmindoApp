package com.android.warmindoapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.android.warmindoapp.data.entity.Meja

@Dao
interface MejaDao {
    @Query("SELECT * FROM meja")
    fun getAll(): List<Meja>

    @Insert
    fun insertAll(vararg meja: Meja)

    @Delete
    fun delete(meja: Meja)

    @Query("SELECT * FROM meja WHERE idmeja = :idmeja")
    fun getById(idmeja: Int): Meja

    @Update
    fun update(meja: Meja)
}