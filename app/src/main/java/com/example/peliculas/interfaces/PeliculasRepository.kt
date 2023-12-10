package com.example.peliculas.interfaces

import com.example.peliculas.models.Peliculas

interface PeliculasRepository {
    fun obtenerPeliculas() : List<Peliculas>
}