package com.example.peliculas.data.datasource.network.service

import com.example.peliculas.data.datasource.network.models.Request.RequestLoginUser
import com.example.peliculas.data.datasource.network.models.Responses.ResponseLogin
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PeliculaApiServiceInterface {

    @POST("auth")
    suspend fun login(@Body loginUser : RequestLoginUser): Response<ResponseLogin>


}