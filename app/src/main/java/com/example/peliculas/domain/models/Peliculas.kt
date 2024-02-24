package com.example.peliculas.domain.models

class Peliculas(
    var titulo: String,
    var diretor: String,
    var genero: String,
    var anno: Int,
    var imageUrl : String? = null
){
    override fun toString(): String {
        return "Peliculas(titulo='$titulo', diretor='$diretor', genero='$genero', anno=$anno, imageUrl=$imageUrl)"
    }
}