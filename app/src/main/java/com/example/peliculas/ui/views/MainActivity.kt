package com.example.peliculas.ui.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.peliculas.AboutUs
import com.example.peliculas.HomeMain
import com.example.peliculas.LoginActivity
import com.example.peliculas.R
import com.example.peliculas.Fragment.Controller
import com.example.peliculas.HomePlataformas
import com.example.peliculas.databinding.ActivityMainBinding
import com.example.peliculas.domain.models.Peliculas


import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var appBarConfiguration: AppBarConfiguration
    @Inject
    lateinit var controller : Controller
    lateinit var binding : ActivityMainBinding
    lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var fab: FloatingActionButton
    private lateinit var imagenHome : ImageButton
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var shared: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView( binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView : NavigationView = binding.navView

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    // Acción cuando se selecciona el elemento "Home"
                    // Por ejemplo, puedes iniciar una nueva actividad
                    startActivity(Intent(this, HomeMain::class.java))
                    true // Devuelve true para indicar que el evento ha sido manejado
                }

                R.id.nav_gallery -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true // Devuelve true para indicar que el evento ha sido manejado
                }
                R.id.nav_login ->{
                    startActivity(Intent(this, LoginActivity::class.java))
                    true
                }

                else -> {
                    // Acción por defecto
                    true // Devuelve true para indicar que el evento ha sido manejado
                }
            }
        }


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        iniciarSesion()
        cargarPreferenciasCompartidas()


        cargarFotoUsuario()
        init()
    }


    fun iniciarSesion() {
        // Obtener el correo del usuario registrado
        val sharedPreferences = getSharedPreferences("misPreferencias", Context.MODE_PRIVATE)
        val registeredEmail = sharedPreferences.getString("email", null)

        // Verificar si se ha registrado un correo
        if (!registeredEmail.isNullOrEmpty()) {
            val headerView = binding.navView.getHeaderView(0)
            val textViewCorreo: TextView = headerView.findViewById(R.id.email)

            // Mostrar el correo del usuario en el NavigationView
            textViewCorreo.text = registeredEmail
        } else {
            // No se encontró un correo registrado, puedes manejarlo de acuerdo a tus necesidades
            Toast.makeText(this, "No se encontró un correo registrado", Toast.LENGTH_SHORT).show()
        }
    }



    @SuppressLint("SuspiciousIndentation")
    fun init(){
        initRecyclerView()
        controller = Controller(this)
        controller.setAdapter()
        binding.btnAdd.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Nueva película")

            val view = layoutInflater.inflate(R.layout.newpelicula, null)
            builder.setView(view)

            builder.setPositiveButton("Agregar") { dialog, which ->
               val titulo = view.findViewById<EditText>(R.id.titulo).text.toString()
                val director = view.findViewById<EditText>(R.id.director).text.toString()
                val genero = view.findViewById<EditText>(R.id.genero).text.toString()
                val anno = view.findViewById<EditText>(R.id.anno).text.toString()
                val imagen = view.findViewById<EditText>(R.id.image).text.toString()

                if (titulo.isNotEmpty() && director.isNotEmpty() && genero.isNotEmpty() && anno.isNotEmpty() && imagen.isNotEmpty()) {
                    val anno = anno.toInt()
                    val nuevaPelicula = Peliculas(titulo, director, genero, anno, imagen)
                    controller.agregarPelicula(nuevaPelicula)
                    controller.adapter.notifyDataSetChanged()

                    controller.listPelis.forEach {
                        println(it)
                    }

                    dialog.dismiss()
                    Toast.makeText(this, "Se ha creado la pelicula", Toast.LENGTH_SHORT).show()
                }
            }

            builder.setNegativeButton("Cancelar") { dialog, which ->
                dialog.dismiss()
            }

            val alertDialog = builder.create()
            alertDialog.show()
        }
        val casaView: View = findViewById(R.id.casa)

        casaView.setOnClickListener {
            startActivity(Intent(this, HomePlataformas::class.java))
            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
        }

     val aboutUs : View = findViewById(R.id.aboutus)
        aboutUs.setOnClickListener {
            startActivity(Intent(this, AboutUs::class.java))
            Toast.makeText(this, "About us", Toast.LENGTH_SHORT).show()
        }
    }
    private fun initRecyclerView() {
        binding.myRecyclerView.layoutManager = LinearLayoutManager( this)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
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
            R.id.login -> {
                startActivity(Intent(this, LoginActivity::class.java))
                Toast.makeText(this, "Cerrar sesion", Toast.LENGTH_SHORT).show()
                return true
            }




        }
        return super.onOptionsItemSelected(item)
    }

    private fun cargarPreferenciasCompartidas() {
        shared = getSharedPreferences("misPreferencias", Context.MODE_PRIVATE)
    }

    private fun cargarFotoUsuario() {
        val sharedPreferences = getSharedPreferences("misPreferencias", Context.MODE_PRIVATE)
        val imagenUriString = sharedPreferences.getString("imagenUri", null)
        if (imagenUriString != null) {
            val headerView = binding.navView.getHeaderView(0)
            val imageView: ImageView = headerView.findViewById(R.id.imageView)
            imageView.setImageURI(Uri.parse(imagenUriString))
        }
    }

    private fun obtenerFotoUsuario(): Drawable? {
        // Obtener la foto del usuario desde las preferencias compartidas
        val imagenPath = shared.getString("imagenPath", null)
        return if (imagenPath != null) {
            // Cargar la imagen desde el almacenamiento interno usando la ruta de la imagen
            Drawable.createFromPath(imagenPath)
        } else {
            // Si no hay imagen guardada, devuelve null o una imagen predeterminada
            null
        }
    }
}