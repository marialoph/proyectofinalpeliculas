package com.example.peliculas.controler

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import com.example.peliculas.MainActivity
import com.example.peliculas.R
import com.example.peliculas.adapter.AdapterPeliculas
import com.example.peliculas.dao.DaoPeliculas
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

        // Inflar tu diseño personalizado para el diálogo de edición
        val view = LayoutInflater.from(context).inflate(R.layout.newpelicula, null)
        builder.setView(view)

        // Obtener referencias a las vistas del diseño
        val tituloEditText = view.findViewById<EditText>(R.id.titulo)
        val generoEditText = view.findViewById<EditText>(R.id.director)
        val annoEditText = view.findViewById<EditText>(R.id.genero)
        val directorEditText = view.findViewById<EditText>(R.id.anno)
        val imagenEditText = view.findViewById<EditText>(R.id.image)

        // Establecer los valores actuales de la película en las vistas
        tituloEditText.setText(pelicula.titulo)
        generoEditText.setText(pelicula.genero)
        annoEditText.setText(pelicula.anno.toString())
        directorEditText.setText(pelicula.diretor)
        imagenEditText.setText(pelicula.imageUrl)

        builder.setPositiveButton("Guardar") { dialog, which ->
            // Obtener los nuevos valores de las vistas
            val nuevoTitulo = tituloEditText.text.toString()
            val nuevoGenero = generoEditText.text.toString()
            val nuevoAnno = annoEditText.text.toString()
            val nuevoDirector = directorEditText.text.toString()
            val nuevaImagen = imagenEditText.text.toString()

            // Validar que el título no esté vacío
            if (nuevoTitulo.isNotEmpty()) {
                // Actualizar los atributos de la película
                pelicula.titulo = nuevoTitulo
                pelicula.genero = nuevoGenero
                pelicula.anno = nuevoAnno.toIntOrNull() ?: 0
                pelicula.diretor = nuevoDirector
                pelicula.imageUrl = nuevaImagen

                // Notificar al adaptador que se ha actualizado un elemento
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