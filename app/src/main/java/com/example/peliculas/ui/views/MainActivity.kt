package com.example.peliculas.ui.views

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
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
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

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
    private lateinit var registerImagen: ImageView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var shared: SharedPreferences
    private val REQUEST_IMAGE_PICK =100
    private val  RESPUESTA_PERMISO_GALERIA = 300;


    private var selectedImageUri: Uri? = null


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

                    startActivity(Intent(this, HomeMain::class.java))
                    true
                }

                R.id.nav_gallery -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.nav_login ->{
                    startActivity(Intent(this, LoginActivity::class.java))
                    true
                }

                else -> {

                    true
                }
            }
        }



        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        iniciarSesion()
        compruebaPermisoLecturaGaleria()
        cargarPreferenciasCompartidas()
        cargarFotoUsuario()
        init()


    }




    fun iniciarSesion() {

        val sharedPreferences = getSharedPreferences("misPreferencias", Context.MODE_PRIVATE)
        val registeredEmail = sharedPreferences.getString("email", null)


        if (!registeredEmail.isNullOrEmpty()) {
            val headerView = binding.navView.getHeaderView(0)
            val textViewCorreo: TextView = headerView.findViewById(R.id.email)


            textViewCorreo.text = registeredEmail
        } else {

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


            val botonImagen = view.findViewById<Button>(R.id.botonimagen)
            botonImagen.setOnClickListener {
                pickPhotoFromGallery()

            }



            builder.setPositiveButton("Agregar") { dialog, which ->
                val titulo = view.findViewById<EditText>(R.id.titulo).text.toString()
                val director = view.findViewById<EditText>(R.id.director).text.toString()
                val genero = view.findViewById<EditText>(R.id.genero).text.toString()
                val anno = view.findViewById<EditText>(R.id.anno).text.toString()


                val imagenUri = selectedImageUri

                if (titulo.isNotEmpty() && director.isNotEmpty() && genero.isNotEmpty() && anno.isNotEmpty() && imagenUri != null) {
                    val annoInt = anno.toInt()
                    val nuevaPelicula = Peliculas(titulo, director, genero, annoInt, imagenUri.toString())
                    controller.agregarPelicula(nuevaPelicula)
                    controller.adapter.notifyDataSetChanged()


                    dialog.dismiss()
                    Toast.makeText(this, "Se ha creado la película", Toast.LENGTH_SHORT).show()
                } else {

                    Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
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

    private fun requestPermission() {
           if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
               when{
                   ContextCompat.checkSelfPermission(
                       this,
                       Manifest.permission.READ_EXTERNAL_STORAGE
                   ) == PackageManager.PERMISSION_GRANTED ->{
                       pickPhotoFromGallery()
                   }
                    else-> requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
               }
           }else{
               pickPhotoFromGallery()
           }
    }
    private fun requestPermission1() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        } else {
            pickPhotoFromGallery()
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){isGranted->
        if(isGranted){
            pickPhotoFromGallery()
        }else{
            Toast.makeText(this, "Necesitas habilitar permisos", Toast.LENGTH_SHORT).show()
        }
    }

    private val startForActivityGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data?.data
            if (data != null) {
                selectedImageUri = data
                Toast.makeText(this, "Imagen seleccionada: $selectedImageUri", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No se pudo obtener la imagen seleccionada", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun pickPhotoFromGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startForActivityGallery.launch(intent)
    }




    private fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }

    private fun initRecyclerView() {
        binding.myRecyclerView.layoutManager = LinearLayoutManager( this)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {

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

        val imagenPath = shared.getString("imagenPath", null)
        return if (imagenPath != null) {

            Drawable.createFromPath(imagenPath)
        } else {

            null
        }
    }





    private fun compruebaPermisoLecturaGaleria(): Boolean {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), RESPUESTA_PERMISO_GALERIA)
            return false
        }
        return true
    }



    fun seleccionarImagenDesdeGaleria() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }





    private fun guardarDatosUsuario(email: String, password: String) {

        val sharedPreferences = getSharedPreferences("misPreferencias", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.putString("password", password)
        editor.apply()
    }



}
