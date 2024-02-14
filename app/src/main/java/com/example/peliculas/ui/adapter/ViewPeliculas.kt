package com.example.peliculas.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.peliculas.R
import com.example.peliculas.models.Peliculas
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel


class ViewPeliculas (
    view: View,
    var deleteOnClick: (Int) -> Unit,
    var updateOnClick:(Int) ->Unit
    ) : RecyclerView.ViewHolder(view) {
    private val titulo: TextView = view.findViewById(R.id.titulo)
    private val director: TextView = view.findViewById(R.id.director)
    private val genero: TextView = view.findViewById(R.id.genero)
    private val anno: TextView = view.findViewById(R.id.anno)
    private val ivPelis: ImageView = view.findViewById(R.id.iv_pelis)
    init {

        setOnClickListener()
    }

    fun renderize(pelis: Peliculas) {
        titulo.text = pelis.titulo
        director.text = pelis.diretor
        genero.text = pelis.genero
        anno.text = pelis.anno.toString()

        Glide.with(itemView.context)
            .load(pelis.imageUrl)
            .centerCrop()
            .into(ivPelis)
    }

    private fun setOnClickListener() {
       itemView.findViewById<ImageView>(R.id.btn_delete).setOnClickListener {
            deleteOnClick(adapterPosition)
        }
        itemView.findViewById<ImageView>(R.id.btn_edit).setOnClickListener {
            updateOnClick(adapterPosition)
        }
    }
}