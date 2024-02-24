package com.example.peliculas.data.datasource.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.peliculas.LoginActivity
import com.example.peliculas.data.datasource.database.entities.LoginEntity

@Dao
interface LoginDao {
    //Inserta el registro del login
    @Insert
    suspend fun insertLogin(login: LoginEntity)

    //Se realiza la consulta para tener los datos del registro
    @Query("SELECT * FROM LoginEntity WHERE email = :email AND password = :password")
    suspend fun getLoginByEmailAndPassword(email: String, password: String): LoginEntity?

}