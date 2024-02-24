package com.example.peliculas.data.datasource.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PeliculasEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int=0,
    @ColumnInfo (name = "titulo") val titulo: String,
    @ColumnInfo (name="director") val director: String,
    @ColumnInfo (name="genero") val genero: String,
    @ColumnInfo (name="anno") val anno: Int,
    @ColumnInfo (name="imageUrl") val imageUrl: String
)

