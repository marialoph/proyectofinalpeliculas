package com.example.peliculas.data.datasource.network.models.Request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RequestLoginUser(


    @SerializedName("email")
    @Expose
    val email : String,


    @SerializedName("password")
    @Expose
    val passw : String
) {
}
