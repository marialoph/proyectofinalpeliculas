package com.example.peliculas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.peliculas.ui.views.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var btnLogin: Button
    private lateinit var textEmail: EditText
    private lateinit var textCont: EditText
    private lateinit var btnRegister : Button

    private val MYUSER = "maria@gmail.com"
    private val MYPASS = "maria"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        initEvent()
    }

    private fun initEvent() {

        btnLogin = findViewById(R.id.btnLogin)
        textEmail = findViewById(R.id.email)
        textCont = findViewById(R.id.password)

        btnLogin.setOnClickListener {
            val email = textEmail.text.toString()
            val password = textCont.text.toString()

            if (email == MYUSER && password == MYPASS) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "No se puede acceder, correo o contraseña incorrecta", Toast.LENGTH_SHORT).show()
            }
        }
    }

}