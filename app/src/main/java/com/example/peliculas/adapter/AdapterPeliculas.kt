package com.example.peliculas.adapter
import android.view.ViewGroup

import android.text.Layout
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.example.peliculas.R
import com.example.peliculas.databinding.ItemPeliculasBinding
import com.example.peliculas.models.Peliculas
import java.util.zip.Inflater

class AdapterPeliculas(
    private val peliculasList: List<Peliculas>,
    var deleteOnCLick:(Int) -> Unit
) : RecyclerView.Adapter<ViewPeliculas>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPeliculas {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layoutItemPelis = R.layout.recyclerview

        return ViewPeliculas(
            layoutInflater.inflate(layoutItemPelis,parent,false),
            deleteOnCLick
            )

    }

    override fun onBindViewHolder(holder: ViewPeliculas, position: Int) {
        val peliculas = peliculasList[position]
        holder.renderize(peliculas)
    }

    override fun getItemCount(): Int = peliculasList.size

}