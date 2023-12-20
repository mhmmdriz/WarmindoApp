package com.android.warmindoapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.android.warmindoapp.data.entity.PenggunaRole

@Dao
interface PenggunaroleDao {
  @Query(
    "SELECT idpengguna, username, password, namapengguna, pengguna.idrole, pengguna.status, foto, role" +
      " FROM pengguna, role WHERE pengguna.idrole = role.idrole"
  )
  fun loadPenggunaAndRole(): LiveData<List<PenggunaRole>>
}