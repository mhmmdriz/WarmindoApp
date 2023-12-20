package com.android.warmindoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.warmindoapp.R
import com.android.warmindoapp.data.entity.Role

class RoleAdapter(private var list:List<Role>) : RecyclerView.Adapter<RoleAdapter.ViewHolder>()  {
  private lateinit var dialog:Dialog

  fun setDialog(dialog:Dialog){
    this.dialog = dialog
  }

  interface Dialog {
    fun onClick(position: Int)
  }

  inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
    var role: TextView
    var status: TextView
    init {
      role = view.findViewById(R.id.role)
      status = view.findViewById(R.id.status)

      view.setOnClickListener{
        dialog.onClick(layoutPosition)
      }
    }
  }
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.row_role, parent, false)
    return ViewHolder(view)
  }

  override fun getItemCount(): Int {
    return list.size
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.role.text = list[position].role
    holder.status.text = list[position].status
  }
}