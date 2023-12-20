package com.android.warmindoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.warmindoapp.R
import com.android.warmindoapp.data.entity.Warung

class WarungAdapter(private var list:List<Warung>) : RecyclerView.Adapter<WarungAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var namawarung : TextView
        var idwarung : TextView

        init {
            namawarung = view.findViewById(R.id.tv_namawarung)
            idwarung = view.findViewById(R.id.tv_idwarung)
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
    }

}