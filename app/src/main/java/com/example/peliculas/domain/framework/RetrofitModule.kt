package com.example.peliculas.domain.framework

import android.content.Context
import androidx.room.Room
import com.example.peliculas.data.datasource.database.DatabasePeliculas
import com.example.peliculas.data.datasource.network.service.PeliculaApiServiceInterface

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    //Nombre de la base de datos
    private const val NAME_DATABASE = "database_peliculas"


    //Proporciona una instancia de la base de datos
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit
        .Builder()
        .baseUrl(NAME_DATABASE)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    //Proporciona el Dao
    @Singleton
    @Provides
    fun provideServiceApi(retrofit : Retrofit): PeliculaApiServiceInterface =
        retrofit
            .create(PeliculaApiServiceInterface::class.java)

}


