package com.example.peliculas.models

import com.example.peliculas.models.Peliculas
import javax.inject.Inject

interface PeliculasRepository{
    fun obtenerPeliculas() : List<Peliculas>
}