package com.android.warmindoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.warmindoapp.R
import com.android.warmindoapp.data.entity.Warung
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage

class WarungAdapter(private var list:List<Warung>) : RecyclerView.Adapter<WarungAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var namawarung : TextView
        var idwarung : TextView
        var gambarwarung : ImageView

        init {
            namawarung = view.findViewById(R.id.tv_namawarung)
            idwarung = view.findViewById(R.id.tv_idwarung)
            gambarwarung = view.findViewById(R.id.iv_gambarwarung)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_warung, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: WarungAdapter.ViewHolder, position: Int) {
        holder.namawarung.text = list[position].namawarung
        holder.idwarung.text = list[position].idwarung.toString()

        // Mengambil URL gambar dari Firebase Storage
        val storageReference = FirebaseStorage.getInstance().reference
        val pathReference =
            list[position].gambar?.let { storageReference.child(it) } // Ambil path dari kolom gambar

        if (pathReference != null) {
            pathReference.downloadUrl.addOnSuccessListener { uri ->
                // Uri adalah URL gambar dari Firebase Storage
                // Gunakan library gambar seperti Glide atau Picasso untuk menampilkan gambar di ImageView
                Glide.with(holder.itemView.context)
                    .load(uri)
                    .into(holder.gambarwarung)
            }.addOnFailureListener { exception ->
                // Handle jika terjadi kesalahan dalam mengambil URL gambar
            }
        }
    }

}