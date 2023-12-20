package com.android.warmindoapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["namawarung"], unique = true)])
data class Warung (
    @PrimaryKey(autoGenerate = true) var idwarung: Int? = null,
    @ColumnInfo(name = "namawarung") var namawarung: String?,
    @ColumnInfo(name = "logo") var logo: String?,
    @ColumnInfo(name = "gambar") var gambar: String?,
)