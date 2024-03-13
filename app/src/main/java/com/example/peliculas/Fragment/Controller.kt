package com.example.peliculas.Fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.peliculas.ui.views.MainActivity
import com.example.peliculas.R
import com.example.peliculas.ui.adapter.AdapterPeliculas
import com.example.peliculas.domain.models.DaoPeliculas
import com.example.peliculas.domain.models.Peliculas


class Controller (val context : Context){
    lateinit var adapter : AdapterPeliculas
    private lateinit var myList: MutableList<String>
    lateinit var listPelis : MutableList<Peliculas>
    val PICK_IMAGE_REQUEST_CODE = 100
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

    @SuppressLint("MissingInflatedId")
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
        val imagen = view.findViewById<ImageView>(R.id.iv_pelis)
        val botonImagen = view.findViewById<Button>(R.id.botonimagen)

        titulo.setText(pelicula.titulo)
        genero.setText(pelicula.genero)
        anno.setText(pelicula.anno.toString())
        director.setText(pelicula.diretor)
        botonImagen.setOnClickListener {
            pickPhotoFromGallery()
        }

        builder.setPositiveButton("Guardar") { dialog, which ->
            val nuevoTitulo = titulo.text.toString()
            val nuevoGenero = genero.text.toString()
            val nuevoAnno = anno.text.toString()
            val nuevoDirector = director.text.toString()

            if (nuevoTitulo.isNotEmpty()) {
                pelicula.titulo = nuevoTitulo
                pelicula.genero = nuevoGenero
                pelicula.anno = nuevoAnno.toIntOrNull() ?: 0
                pelicula.diretor = nuevoDirector


                adapter.notifyItemChanged(position)


                Toast.makeText(context, "Película actualizada correctamente", Toast.LENGTH_SHORT).show()
            } else {

                Toast.makeText(context, "Termine de rellenar todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Cancelar") { dialog, which ->
            dialog.dismiss()
        }

        builder.show()
    }

    private fun pickPhotoFromGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        val activity = context as? Activity
        activity?.startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
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
