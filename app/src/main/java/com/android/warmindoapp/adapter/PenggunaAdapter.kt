package com.android.warmindoapp.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.warmindoapp.R
import com.android.warmindoapp.data.entity.PenggunaRole
import com.android.warmindoapp.data.entity.Role

class PenggunaAdapter(private var list:List<PenggunaRole>) : RecyclerView.Adapter<PenggunaAdapter.ViewHolder>()   {
  private lateinit var dialog:Dialog

  fun setDialog(dialog:Dialog){
    this.dialog = dialog
  }

  interface Dialog {
    fun onClick(position: Int)
  }

  inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
    var tvRole: TextView
    var tvNamaPengguna: TextView
    var tvUsername: TextView
    init {
      tvRole = view.findViewById(R.id.user_role)
      tvNamaPengguna = view.findViewById(R.id.namaPengguna)
      tvUsername = view.findViewById(R.id.username)

      view.setOnClickListener{
        dialog.onClick(layoutPosition)
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
//    holder.tvRole.text = list[position].namarole
    holder.tvNamaPengguna.text = list[position].namapengguna
    holder.tvUsername.text = list[position].username
  }

}