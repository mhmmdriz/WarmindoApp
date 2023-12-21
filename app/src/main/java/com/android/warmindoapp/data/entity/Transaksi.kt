package com.android.warmindoapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["idtransaksi"], unique = true)])
data class Transaksi(
    @PrimaryKey(autoGenerate = true) var idtransaksi: Int? = null,
    @ColumnInfo(name = "tanggal") var tanggal: String?,
    @ColumnInfo(name = "waktu") var waktu: String?,
    @ColumnInfo(name = "shift") var shift: String?,
    @ColumnInfo(name = "idpengguna") var idpengguna: String?,
    @ColumnInfo(name = "idpelanggan") var idpelanggan: String?,
    @ColumnInfo(name = "status") var status: String?,
    @ColumnInfo(name = "kodemeja") var kodemeja: String?,
    @ColumnInfo(name = "namapelanggan") var namapelanggan: String?,
    @ColumnInfo(name = "total") var total: String?,
    @ColumnInfo(name = "metodepembayaran") var metodepembayaran: String?,
    @ColumnInfo(name = "totaldiskon") var totaldiskon: String?,
    @ColumnInfo(name = "idpromosi") var idpromosi: String?,
)
