package com.example.penjualan_mfarrelalvareno

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.penjualan_mfarrelalvareno.Room.tbbarang
import kotlinx.android.synthetic.main.activity_adapter.view.*

class AdapterActivity (private val Barangs: ArrayList<tbbarang>,private val listener : OnAdapterListener)
    : RecyclerView.Adapter<AdapterActivity.BarViewHolder>(){
    class BarViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun getItemCount() = Barangs.size

    override fun onBindViewHolder(holder: BarViewHolder, position: Int) {
        val barang = Barangs [position]
        holder.view.text_tittle.text = barang.nm_brg
        holder.view.Tid.text = barang.id_brg.toString()
        holder.view.text_tittle.setOnClickListener {
            listener.onClick(barang)
        }
        holder.view.icon_edit.setOnClickListener {
            listener.onUpdate(barang)
        }
        holder.view.icon_delete.setOnClickListener {
            listener.onDelete(barang)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarViewHolder {
        return BarViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.activity_adapter,parent,false)
        )
    }
    fun setData (list: List<tbbarang>){
        Barangs.clear()
        Barangs.addAll(list)
        notifyDataSetChanged()
    }
    interface OnAdapterListener{
        fun onClick( tbBarang :tbbarang )
        fun onUpdate( tbBarang : tbbarang)
        fun onDelete( tbBarang : tbbarang)
    }

}