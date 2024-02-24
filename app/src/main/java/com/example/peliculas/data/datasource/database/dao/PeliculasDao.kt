package com.example.peliculas.data.datasource.database.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.peliculas.data.datasource.database.entities.PeliculasEntity

interface PeliculasDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPelicula(pelicula: PeliculasEntity)

    @Query("SELECT * FROM peliculasentity")
    suspend fun getAllPeliculas(): List<PeliculasEntity>

    @Query("SELECT * FROM peliculasentity WHERE id = :id")
    suspend fun getPeliculaById(id: Int): PeliculasEntity?

    @Query("DELETE FROM peliculasentity")
    suspend fun deleteAll()
}