package com.example.peliculas.domain.models

data class User(var id: Int, var token: String, var email:String, var passw:String, val disponible: Int, var imagen: String){

    //constructor primario
    constructor(email: String, passw: String):
            this(0, "", email, passw, 0, "")

}
