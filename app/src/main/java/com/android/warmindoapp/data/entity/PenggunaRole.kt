package com.android.warmindoapp.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class PenggunaRole(
  @Embedded val pengguna: Pengguna,
  @Relation(
    parentColumn = "idrole",
    entityColumn = "idrole"
  )
  val role: Role
)
