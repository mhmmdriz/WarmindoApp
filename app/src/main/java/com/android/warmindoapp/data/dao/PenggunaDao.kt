package com.android.warmindoapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.android.warmindoapp.data.entity.Pengguna

@Dao
interface PenggunaDao {
  @Query("SELECT * FROM pengguna")
  fun getAll(): List<Pengguna>

  @Insert
  fun insertAll(vararg pengguna: Pengguna)

  @Delete
  fun delete(pengguna: Pengguna)

  @Query("SELECT * FROM pengguna WHERE idpengguna = :idpengguna")
  fun getById(idpengguna: Int): Pengguna

  @Query("SELECT * FROM pengguna WHERE username = :username")
  fun getByUsername(username: Int): Pengguna

  @Update
  fun update(pengguna: Pengguna)
}