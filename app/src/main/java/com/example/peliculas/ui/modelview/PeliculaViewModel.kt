package com.example.peliculas.ui.modelview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.peliculas.models.Peliculas
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class PeliculaViewModel  @Inject constructor(): ViewModel() {
    private val peliculaLiveData = MutableLiveData<Peliculas>()

    fun getPelicula(): MutableLiveData<Peliculas> {
        return peliculaLiveData
    }

    fun setPelicula(pelicula: Peliculas) {
        peliculaLiveData.value = pelicula
    }
}

