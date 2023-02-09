package com.example.challengeroom1delliisna

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.challengeroom1delliisna.room.tbSiswa
import kotlinx.android.synthetic.main.activity_adapter.view.*

class AdapterActivity (private val siswa : ArrayList<tbSiswa>, private val listener:OnAdapterListener)
    : RecyclerView.Adapter<AdapterActivity.SiswaViewHolder>(){

    class SiswaViewHolder( val view: View): RecyclerView.ViewHolder(view)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SiswaViewHolder {
      return  SiswaViewHolder(
          LayoutInflater.from(parent.context).inflate(R.layout.activity_adapter,parent,false)
      )
    }

    override fun onBindViewHolder(holder: SiswaViewHolder, position: Int) {
      val murid = siswa [position]
        holder.view.title.text = murid.nama
        holder.view.title.setOnClickListener{
            listener.OnClick(murid)
        }
        holder.view.icon_edit.setOnClickListener{
            listener.OnUpdate(murid)
        }
        holder.view.icon_delete.setOnClickListener{
            listener.OnDelete(murid)
        }

    }

    override fun getItemCount()= siswa.size

    fun setData(list: List<tbSiswa>){
        siswa.clear()
        siswa.addAll(list)
        notifyDataSetChanged()
    }
    interface  OnAdapterListener{
        fun OnClick(tbsiswa:tbSiswa)
        fun OnUpdate(tbsiswa:tbSiswa)
        fun OnDelete(tbsiswa:tbSiswa)
    }
}