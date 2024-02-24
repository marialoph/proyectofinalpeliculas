package com.example.peliculas

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
    private lateinit var textPass: EditText
    private lateinit var btnRegister : Button
    private lateinit var shared : SharedPreferences



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        cargaPreferenciasCompartidas()

        textEmail = findViewById(R.id.email)
        textPass = findViewById(R.id.password)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegister = findViewById(R.id.btnRegister)


        btnLogin.setOnClickListener {
            iniciarLogin()
        }

        if(islogeado()){
            //val i = Intent(this, MainActivity::class.java)
            //startActivity(i)
        }
    }

    private fun iniciarLogin() {
        val email = textEmail.text.toString()
        val pass = textPass.text.toString()
        textEmail.setText("")
        textPass.setText("")

        if (email == "maria@gmail.com" && pass == "123") {
            val editor = shared.edit()
            editor.putString("preferenciasEmail", email)
            editor.putBoolean("isLogin", true)
            editor.commit()
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        } else {
            Toast.makeText(
                this, "Email o contraseña no es correcta",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    private fun islogeado(): Boolean {
        val isLogin = shared.getBoolean("isLogin", false)
        return isLogin
    }


    private fun cargaPreferenciasCompartidas(){
        val fichPreferencias = "preferenciasAppPeliculas"
        shared = this.getSharedPreferences(fichPreferencias, Context.MODE_PRIVATE)
    }

}