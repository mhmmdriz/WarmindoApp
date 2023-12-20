package com.android.warmindoapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity
data class Meja (
    @PrimaryKey(autoGenerate = true) var idmeja: Int? = null,
    @ColumnInfo(name = "idwarung") var idwarung: Int?,
    @ColumnInfo(name = "kodemeja") var kodemeja: String?,
)