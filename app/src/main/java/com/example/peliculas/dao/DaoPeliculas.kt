package com.example.peliculas.dao

import com.example.peliculas.interfaces.PeliculasRepository
import com.example.peliculas.models.Peliculas
import com.example.peliculas.objects_models.Repository

class DaoPeliculas private constructor(): PeliculasRepository {
    companion object {
        val myDao: DaoPeliculas by lazy {
            DaoPeliculas()
        }
    }
    fun getDataPelis(): List<Peliculas> = Repository.listaPelis
    override fun obtenerPeliculas(): List<Peliculas> {
      return getDataPelis()
    }
}
