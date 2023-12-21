package com.android.warmindoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.warmindoapp.R
import com.android.warmindoapp.data.entity.Transaksi

class TransaksiAdapter(private var list:List<Transaksi>) : RecyclerView.Adapter<TransaksiAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var idtransaksi : TextView
        var namapelanggan : TextView

        init {
            idtransaksi = view.findViewById(R.id.IdTransaksi)
            namapelanggan = view.findViewById(R.id.NamaPelanggan)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_transaksi, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.idtransaksi.text = list[position].idtransaksi.toString()
        holder.namapelanggan.text = list[position].namapelanggan
    }

}