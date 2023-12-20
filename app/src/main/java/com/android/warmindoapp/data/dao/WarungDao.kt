package com.android.warmindoapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.android.warmindoapp.data.entity.Warung

@Dao
interface WarungDao {
    @Query("SELECT * FROM warung")
    fun getAll(): List<Warung>

    @Insert
    fun insertAll(vararg warung: Warung)

    @Delete
    fun delete(warung: Warung)

    @Query("SELECT * FROM warung WHERE idwarung = :idwarung")
    fun getById(idwarung: Int): Warung

    @Query("SELECT * FROM warung WHERE namawarung = :namawarung")
    fun getByWarungName(namawarung: String): Warung

    @Update
    fun update(warung: Warung)
}