package com.example.peliculas.data.datasource.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Clase entidad de Room
@Entity
data class LoginEntity(
    //Clave primaria
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int=0,

    //Define el campo email y password
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name="password") val password: String,

)

