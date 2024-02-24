package com.example.peliculas.framework

import android.content.Context
import androidx.room.Room
import com.example.peliculas.data.datasource.database.DatabasePeliculas

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    //Nombre de la base de datos
    private const val NAME_DATABASE = "database_peliculas"


    //Proporciona una instancia de la base de datos
    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context)=

       Room.databaseBuilder(context,DatabasePeliculas::class.java,
            NAME_DATABASE).build()


    //Proporciona el Dao
    @Singleton
    @Provides
    fun providePeliculasDao(database: DatabasePeliculas)=database.LoginDao()

}


