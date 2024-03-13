package com.example.peliculas.domain.usecase

import com.example.peliculas.domain.models.User
import com.example.peliculas.domain.models.UserRepository
import javax.inject.Inject


class LoginUserCase  @Inject constructor(
    private val userRepository: UserRepository,
    private var posibleUser : User
){
    fun setUser(_posibleUser : User){
        posibleUser = _posibleUser
    }

    suspend operator fun invoke(): User ?{
        return (userRepository.getUser(posibleUser))
    }
}