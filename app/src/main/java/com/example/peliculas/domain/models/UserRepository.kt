package com.example.peliculas.domain.models

import com.example.peliculas.data.datasource.network.models.Request.RequestLoginUser
import com.example.peliculas.data.datasource.network.service.PeliculaApiService
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: PeliculaApiService
){

    suspend fun getUser(user : User) : User? {
        val userRequest = RequestLoginUser(user.email, user.passw)
        val result = apiService.getUser(userRequest)
        result
            .onSuccess {
                    responseUser->
                return User(
                    responseUser.id,
                    responseUser.token,
                    responseUser.email,
                    responseUser.passw,
                    responseUser.disponible,
                    responseUser.image)

            }
            .onFailure {
                    exception ->  println("Error en la excepcion ${exception.message}")
            }
        return null
    }
}