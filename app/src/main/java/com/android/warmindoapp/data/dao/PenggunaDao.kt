package com.android.warmindoapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.android.warmindoapp.data.entity.Pengguna
import com.android.warmindoapp.data.entity.PenggunaRole

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
  fun getByUsername(username: String): Pengguna

  @Update
  fun update(pengguna: Pengguna)

  @Query(
    "SELECT idpengguna, username, password, namapengguna, pengguna.idrole, pengguna.status, foto, role" +
      " FROM pengguna, role WHERE pengguna.idrole = role.idrole"
  )
  fun loadPenggunaAndRole(): List<Pengguna>

  @Transaction
  @Query("SELECT * FROM Pengguna")
  fun getRoleWithUsers(): List<PenggunaRole>
}