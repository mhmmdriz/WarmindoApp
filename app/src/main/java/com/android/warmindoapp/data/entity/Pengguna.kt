package com.android.warmindoapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["username"], unique = true)])
data class Pengguna (
  @PrimaryKey(autoGenerate = false) var idpengguna: String = "",
  @ColumnInfo(name = "username") var username: String?,
  @ColumnInfo(name = "password") var password: String?,
  @ColumnInfo(name = "namapengguna") var namapengguna: String?,
  @ColumnInfo(name = "idrole") var idrole: Int?,
  @ColumnInfo(name = "status") var status: String?, // "aktif" atau "tidak"
  @ColumnInfo(name = "foto") var foto: String?
)
