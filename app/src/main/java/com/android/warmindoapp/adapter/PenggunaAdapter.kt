package com.android.warmindoapp.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.warmindoapp.R
import com.android.warmindoapp.data.entity.PenggunaRole
import com.android.warmindoapp.data.entity.Role
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView

class PenggunaAdapter(private var list:List<PenggunaRole>) : RecyclerView.Adapter<PenggunaAdapter.ViewHolder>()   {
  private lateinit var dialog:Dialog
  private var listener: RecyclerViewClickListener? = null
  fun setDialog(dialog:Dialog){
    this.dialog = dialog
  }

  interface Dialog {
    fun onClick(position: Int)
  }

  fun setClickListener(listener: RecyclerViewClickListener) {
    this.listener = listener
  }
  inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
    var tvRole: TextView
    var tvNamaPengguna: TextView
    var tvUsername: TextView
    var editButton: ImageView
    var deleteButton: ImageView
    var ivFotoUser: CircleImageView
    init {
      tvRole = view.findViewById(R.id.user_role)
      tvNamaPengguna = view.findViewById(R.id.namaPengguna)
      tvUsername = view.findViewById(R.id.username)
      editButton = view.findViewById(R.id.edit_button)
      deleteButton = view.findViewById(R.id.delete_button)
      ivFotoUser = view.findViewById(R.id.iv_fotouser)

      editButton.setOnClickListener {
        listener?.onEditClick(adapterPosition)
      }

      // Menangani klik pada ImageView Delete
      deleteButton.setOnClickListener {
        listener?.onDeleteClick(adapterPosition)
      }

    }
  }
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.row_user, parent, false)
    return ViewHolder(view)
  }

  override fun getItemCount(): Int {
    return list.size
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.tvRole.text = "role : " + list[position].role.role
    holder.tvNamaPengguna.text = list[position].pengguna.namapengguna
    holder.tvUsername.text = "username : " + list[position].pengguna.username

    val storageReference = FirebaseStorage.getInstance().reference
    val pathReference = list[position].pengguna.foto?.takeIf { it.isNotBlank() }?.let { storageReference.child(it) } // Ambil path dari kolom foto

    pathReference?.downloadUrl?.addOnSuccessListener { uri ->
        // Uri adalah URL gambar dari Firebase Storage
        // Gunakan library gambar seperti Glide atau Picasso untuk menampilkan gambar di ImageView
        Glide.with(holder.itemView.context)
          .load(uri)
          .diskCacheStrategy(DiskCacheStrategy.ALL) // Menyimpan gambar ke dalam cache
          .into(holder.ivFotoUser)
    }?.addOnFailureListener { exception ->
        // Handle jika terjadi kesalahan dalam mengambil URL gambar
    }
  }

}