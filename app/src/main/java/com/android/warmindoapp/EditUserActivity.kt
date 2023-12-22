package com.android.warmindoapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.android.warmindoapp.data.AppDatabase
import com.android.warmindoapp.data.entity.Pengguna
import com.android.warmindoapp.data.entity.Role
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage

class EditUserActivity : AppCompatActivity() {
  private lateinit var database: AppDatabase
  private lateinit var etNama: EditText
  private lateinit var etUsername: EditText
  private lateinit var etPassword: EditText
  private lateinit var spinStatus: Spinner
  private lateinit var spinRole: Spinner
  private lateinit var roleId : Array<Int?>
  private lateinit var statusItems : Array<String?>
  private lateinit var ivFoto : ImageView
  private lateinit var btnBrowse : Button
  private lateinit var btnSave: Button

  private var selectedRoleId: Int? = null
  private var selectedStatus: String? = null
  var pengguna: Pengguna? = null
  private var existingUser: Pengguna? = null
  private var usernameOld: String? = null

//  private var storageReference = Firebase.storage
  private var uri: Uri? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_edit_user)

    val storageReference = FirebaseStorage.getInstance().reference

    database = AppDatabase.getInstance(applicationContext)

    etNama = findViewById(R.id.et_nama)
    etUsername = findViewById(R.id.et_username)
    etPassword = findViewById(R.id.et_password)
    spinRole = findViewById(R.id.spinner)
    spinStatus = findViewById(R.id.spinner_status)
    ivFoto = findViewById(R.id.iv_foto)
    btnBrowse = findViewById(R.id.btn_browse)
    btnSave = findViewById(R.id.btn_save)

    // Inisialisasi Spinner dengan data dari database
    setupSpinner()

    val intent = intent.extras
    val id = intent?.getInt("id", 0) ?: 0
    if(intent != null){
      pengguna = database.penggunaDao().getById(id)

      etNama.setText(pengguna!!.namapengguna)
      etUsername.setText(pengguna!!.username)
      etPassword.setText(pengguna!!.password)
      spinStatus.setSelection(statusItems.indexOf(pengguna!!.status))
      spinRole.setSelection(roleId.indexOf(pengguna!!.idrole))

      selectedRoleId = pengguna!!.idrole
      selectedStatus = pengguna!!.status
      usernameOld = pengguna!!.username

      val pathFoto = pengguna?.foto
      if (!pathFoto.isNullOrBlank()) {
        val pathReference = storageReference.child(pathFoto) // Ambil path dari kolom foto

        pathReference.downloadUrl.addOnSuccessListener { uri ->
          // Uri adalah URL gambar dari Firebase Storage
          // Gunakan library gambar seperti Glide atau Picasso untuk menampilkan gambar di ImageView
          Glide.with(this)
            .load(uri)
            .diskCacheStrategy(DiskCacheStrategy.ALL) // Menyimpan gambar ke dalam cache
            .into(ivFoto)
        }.addOnFailureListener {
          // Handle jika terjadi kesalahan dalam mengambil URL gambar
        }
      }
    }
    
    val galleryImage = registerForActivityResult(
      ActivityResultContracts.GetContent(),
      ActivityResultCallback {
        ivFoto.setImageURI(it)
        if (it != null) {
          uri = it
        }
      }
    )

    btnBrowse.setOnClickListener {
      galleryImage.launch("image/*")
    }

    btnSave.setOnClickListener {
      // Panggil fungsi untuk melakukan insert ke tabel Pengguna
      // Mendapatkan data dari inputan pengguna
      val nama = etNama.text.toString()
      val username = etUsername.text.toString()
      val password = etPassword.text.toString()
      var filePath = pengguna?.foto

      // Mendapatkan ID role yang dipilih dari Spinner
      selectedRoleId = roleId[spinRole.selectedItemPosition]
      selectedStatus = statusItems[spinStatus.selectedItemPosition]

      if (
        ValidationUtils.isTextNotEmpty(username) &&
        ValidationUtils.isTextNotEmpty(nama) &&
        ValidationUtils.isTextNotEmpty(password)
      ){
        if(username != usernameOld){
          existingUser = database.penggunaDao().getByUsername(username)
        }else{
          existingUser = null
        }

        // Upload foto baru ke Firebase Storage di sini
        if (uri != null) {
          filePath = "images/foto_profil/${System.currentTimeMillis()}"
          storageReference.child(filePath!!)
            .putFile(uri!!)
            .addOnSuccessListener { taskSnapshot ->
              taskSnapshot.storage.downloadUrl.addOnSuccessListener { downloadUri ->
                // Gunakan URL gambar yang sudah diunggah
                // pathFoto = downloadUri.toString()
                // Lakukan sesuatu dengan URL gambar yang sudah diunggah, misalnya menyimpannya di database

                // Periksa apakah ada foto sebelumnya
                if (!pengguna?.foto.isNullOrBlank()) {
                  val oldPhotoReference =
                    storageReference.child(pengguna!!.foto!!) // Ambil referensi foto lama
                  oldPhotoReference.delete().addOnSuccessListener {
                    // Foto lama berhasil dihapus
                  }.addOnFailureListener {
                    // Gagal menghapus foto lama
                    // Handle sesuai kebutuhan
                  }
                }

              }.addOnFailureListener { exception ->
                // Handle jika terjadi kesalahan saat mengunggah gambar
              }
            }
        }
        // Update Data Pengguna
        if (existingUser == null) {
          if (intent != null) {
            database.penggunaDao().update(
              Pengguna(
                intent.getInt("id"),
                pengguna?.idpengguna,
                username,
                password,
                nama,
                selectedRoleId,
                selectedStatus,
                filePath
              )
            )
          }
          finish()
          Toast.makeText(this, "User updated", Toast.LENGTH_SHORT).show()
        } else {
          Toast.makeText(this, "Username is already registered", Toast.LENGTH_SHORT).show()
        }
      }else{
        Toast.makeText(this, "Please input all field", Toast.LENGTH_SHORT).show()
      }
    }
  }

  private fun setupSpinner() {
    val roles = database.roleDao().getAll()
    val roleNames = roles.map { it.role }.toTypedArray()
    roleId = roles.map { it.idrole }.toTypedArray()

    statusItems = arrayOf("Aktif", "Tidak")
    val statusAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, statusItems)
    spinStatus.adapter = statusAdapter

    // Mengatur adapter untuk Spinner
    val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, roleNames)
    spinRole.adapter = adapter
  }

}