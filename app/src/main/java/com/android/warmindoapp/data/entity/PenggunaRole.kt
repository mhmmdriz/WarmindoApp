package com.android.warmindoapp.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class PenggunaRole (
  @Embedded val role: Role,
  @Relation(
    parentColumn = "idrole",
    entityColumn = "idrole"
  )
  val playlists: List<Pengguna>
)