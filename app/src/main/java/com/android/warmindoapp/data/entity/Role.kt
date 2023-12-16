package com.android.warmindoapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity
data class Role (
  @PrimaryKey(autoGenerate = true) var idrole: Int? = null,
  @ColumnInfo(name = "role") var role: String?,
  @ColumnInfo(name = "status") var status: String?,
)
