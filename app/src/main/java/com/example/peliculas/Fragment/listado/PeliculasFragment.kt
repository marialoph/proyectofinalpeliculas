package com.example.peliculas.Fragment.listado

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.peliculas.R
import com.example.peliculas.ui.adapter.AdapterPeliculas

import com.example.peliculas.databinding.RecyclerviewBinding
import com.example.peliculas.domain.models.Peliculas
/*
class PeliculasFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private var listaPeliculas: MutableList<Peliculas> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.recyclerview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.cardViewPelicula)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Asegúrate de que la lista no sea nula antes de asignarla al adaptador
        val adapter = AdapterPeliculas(listaPeliculas)
        recyclerView.adapter = adapter
    }
}
*/

