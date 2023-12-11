package com.example.peliculas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.peliculas.controler.Controller
import com.example.peliculas.databinding.ActivityMainBinding
import com.example.peliculas.models.Peliculas

class MainActivity : AppCompatActivity() {
    lateinit var controller : Controller
    lateinit var binding : ActivityMainBinding
    // lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate( layoutInflater)
        setContentView( binding.root)
        init() //inicializo la clase
    }
    fun init(){
        initRecyclerView()
        controller = Controller(this) //Creamos el controler
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
                    // Crear un nuevo objeto Peliculas con los datos
                    val anno = anno.toInt()
                    val nuevaPelicula = Peliculas(titulo, director, genero, anno, imagen)

                    // Agregar la nueva película a la lista en el controlador
                    controller.agregarPelicula(nuevaPelicula)

                    controller.adapter.notifyDataSetChanged()

                    // Opcional: Puedes imprimir la lista actualizada en la consola
                    controller.listPelis.forEach {
                        println(it)
                    }

                    // Cerrar el cuadro de diálogo
                    dialog.dismiss()
                } else {
                    // Mostrar un mensaje de error si algún campo está vacío
                    Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                }
            }

            builder.setNegativeButton("Cancelar") { dialog, which ->
                // Cerrar el cuadro de diálogo sin hacer nada
                dialog.dismiss()
            }

            // Mostrar el cuadro de diálogo
            val alertDialog = builder.create()
            alertDialog.show()
        }
    }
    private fun initRecyclerView() {
        binding.myRecyclerView.layoutManager = LinearLayoutManager( this)
    }
}