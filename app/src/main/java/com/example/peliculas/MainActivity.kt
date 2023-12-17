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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView( binding.root)
        init()
    }
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
                    Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                }
            }

            builder.setNegativeButton("Cancelar") { dialog, which ->
                dialog.dismiss()
            }

            val alertDialog = builder.create()
            alertDialog.show()
        }
    }
    private fun initRecyclerView() {
        binding.myRecyclerView.layoutManager = LinearLayoutManager( this)
    }
}