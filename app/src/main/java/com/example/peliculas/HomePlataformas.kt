package com.example.peliculas

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.peliculas.ui.views.MainActivity

class HomePlataformas : AppCompatActivity() {
    private lateinit var textH: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inicio_home)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initEvent()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                startActivity(Intent(this, HomeMain::class.java))
                Toast.makeText(this, "Estreno peliculas", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.lista -> {
                startActivity(Intent(this, MainActivity::class.java))
                Toast.makeText(this, "Lista de Peliculas", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.login-> {
                startActivity(Intent(this, LoginActivity::class.java))
                Toast.makeText(this, "Vuelta al Login", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initEvent() {
        textH = findViewById(R.id.Home)
    }
}