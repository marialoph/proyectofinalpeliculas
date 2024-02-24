package com.example.peliculas.domain.models

import com.example.peliculas.domain.models.Peliculas
import javax.inject.Inject

interface PeliculasRepository{
    fun obtenerPeliculas() : List<Peliculas>
}