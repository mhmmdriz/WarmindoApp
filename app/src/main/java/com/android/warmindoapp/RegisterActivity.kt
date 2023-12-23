package com.android.warmindoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.warmindoapp.data.AppDatabase
import com.android.warmindoapp.data.entity.Pengguna
import com.android.warmindoapp.data.entity.Role
import com.android.warmindoapp.data.entity.Transaksi
import com.android.warmindoapp.data.entity.Warung
import com.android.warmindoapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)
        val pengguna = Pengguna("WT1202310X1", "pemilik", "password", "Pemilik", 1, "Aktif", "images/foto_profil/pemilik.jpg")
        db.penggunaDao().insert(pengguna)

        val role = Role(1, "Pemilik", "Aktif")
        db.roleDao().insertAll(role)

        val role1 = Role(2, "Kasir", "Aktif")
        db.roleDao().insertAll(role1)

        val role2 = Role(3, "Pengantar", "Aktif")
        db.roleDao().insertAll(role2)

        val role3 = Role(4, "Petugas Dapur", "Aktif")
        db.roleDao().insertAll(role3)

        val warung1 = Warung("WT1", "Sukses Jaya", "", "images/jason-leung-poI7DelFiVA.jpg")
        val warung2 = Warung("WT2", "Jaya Abadi", "", "images/maria-orlova-oMTlhdFUhdI.jpg")
        db.warungDao().insertAll(warung1)
        db.warungDao().insertAll(warung2)

        val transaksi = Transaksi(
            idtransaksi = 1,
            tanggal = "01-12-2023",
            waktu = "",
            shift = "",
            idpengguna = "WT1202312X01",
            idpelanggan = "pelanggan1",
            status = "",
            kodemeja = "",
            namapelanggan = "pelanggan1",
            total = "",
            metodepembayaran = "",
            totaldiskon = "",
            idpromosi = ""
        )
        db.transaksiDao().insertAll(transaksi)

        val transaksi2 = Transaksi(
            idtransaksi = 2,
            tanggal = "05-12-2023",
            waktu = "",
            shift = "",
            idpengguna = "WT1202312X01",
            idpelanggan = "pelanggan2",
            status = "",
            kodemeja = "",
            namapelanggan = "pelanggan2",
            total = "",
            metodepembayaran = "",
            totaldiskon = "",
            idpromosi = ""
        )

        db.transaksiDao().insertAll(transaksi2)
    }

}
