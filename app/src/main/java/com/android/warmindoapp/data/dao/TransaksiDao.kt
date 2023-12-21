package com.android.warmindoapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.android.warmindoapp.data.entity.Transaksi

@Dao
interface TransaksiDao {
    @Query("SELECT * FROM transaksi")
    fun getAll(): List<Transaksi>

    @Insert
    fun insertAll(vararg transaksi: Transaksi)

    @Delete
    fun delete(transaksi: Transaksi)

    @Query("SELECT * FROM transaksi WHERE idtransaksi = :idtransaksi")
    fun getById(idtransaksi: Int): Transaksi

    @Query("SELECT * FROM transaksi WHERE namapelanggan = :namapelanggan")
    fun getByPelangganName(namapelanggan: String): Transaksi

    @Update
    fun update(transaksi: Transaksi)
}