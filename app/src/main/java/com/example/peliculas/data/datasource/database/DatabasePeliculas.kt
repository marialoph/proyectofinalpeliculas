package com.example.peliculas.data.datasource.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.peliculas.data.datasource.database.dao.LoginDao
import com.example.peliculas.data.datasource.database.dao.PeliculasDao
import com.example.peliculas.data.datasource.database.entities.LoginEntity
import com.example.peliculas.data.datasource.database.entities.PeliculasEntity

@Database(entities = [LoginEntity:: class], version = 1)
abstract class DatabasePeliculas : RoomDatabase(){
    //Se define la funcion de manera abstracta para obtener el dao del logeo
    abstract fun LoginDao(): LoginDao
}