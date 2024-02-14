package com.example.peliculas.controler

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import com.example.peliculas.ui.views.MainActivity
import com.example.peliculas.R
import com.example.peliculas.ui.adapter.AdapterPeliculas
import com.example.peliculas.models.DaoPeliculas
import com.example.peliculas.models.Peliculas

class Controller ( val context : Context){
    lateinit var adapter : AdapterPeliculas
    private lateinit var myList: MutableList<String>
    lateinit var listPelis : MutableList<Peliculas>
    init {

        initData()
        setAdapter()
    }
    fun initData(){

        listPelis = DaoPeliculas. myDao.getDataPelis(). toMutableList()

    }
    fun loggOut() {
        Toast.makeText( context, "He mostrado los datos en pantalla", Toast. LENGTH_LONG).show()
        listPelis.forEach{
            println(it)
        }
    }
    fun setAdapter() {
        val myActivity = context as MainActivity
        adapter = AdapterPeliculas(
            listPelis,
            {
                    position -> deletePeliculas(position)
            },
            {
                    position -> updatePeliculas(position)
            }
        )


        myActivity.binding.myRecyclerView.adapter = adapter


    }

    private fun updatePeliculas(position: Int) {
        val pelicula = listPelis[position]
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Editar Película")

        val view = LayoutInflater.from(context).inflate(R.layout.newpelicula, null)
        builder.setView(view)

        val titulo = view.findViewById<EditText>(R.id.titulo)
        val genero = view.findViewById<EditText>(R.id.director)
        val anno = view.findViewById<EditText>(R.id.genero)
        val director = view.findViewById<EditText>(R.id.anno)
        val imagen = view.findViewById<EditText>(R.id.image)

        titulo.setText(pelicula.titulo)
        genero.setText(pelicula.genero)
        anno.setText(pelicula.anno.toString())
        director.setText(pelicula.diretor)
        imagen.setText(pelicula.imageUrl)

        builder.setPositiveButton("Guardar") { dialog, which ->
            val nuevoTitulo = titulo.text.toString()
            val nuevoGenero = genero.text.toString()
            val nuevoAnno = anno.text.toString()
            val nuevoDirector = director.text.toString()
            val nuevaImagen = imagen.text.toString()

            if (nuevoTitulo.isNotEmpty()) {
                pelicula.titulo = nuevoTitulo
                pelicula.genero = nuevoGenero
                pelicula.anno = nuevoAnno.toIntOrNull() ?: 0
                pelicula.diretor = nuevoDirector
                pelicula.imageUrl = nuevaImagen

                adapter.notifyItemChanged(position)

                Toast.makeText(context, "Película actualizada correctamente", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "El título no puede estar vacío", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Cancelar") { dialog, which ->
            dialog.dismiss()
        }

        val alertDialog = builder.create()
        alertDialog.show()
    }


    private fun deletePeliculas(pos: Int) {
        val peliculaTitulo = listPelis[pos].titulo

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Confirmación")
        builder.setMessage("¿Seguro que quieres borrar la película \"$peliculaTitulo\"?")

        builder.setPositiveButton("Sí") { dialog, which ->

            Toast.makeText(context, "Has eliminado la película $peliculaTitulo", Toast.LENGTH_SHORT).show()
            listPelis.removeAt(pos)
            adapter.notifyItemRemoved(pos)
        }

        builder.setNegativeButton("No") { dialog, which ->

            dialog.dismiss()
        }

        val alertDialog = builder.create()
        alertDialog.show()
    }

    fun agregarPelicula(nuevaPelicula: Peliculas){
        listPelis.add(nuevaPelicula)
    }
}