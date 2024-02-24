package com.example.peliculas.dialogues

import androidx.fragment.app.DialogFragment


import com.example.peliculas.domain.models.Peliculas
import android.os.Bundle;
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.peliculas.R
import com.example.peliculas.domain.models.ArgumentsPeliculas.ARGUMENT_ANNO
import com.example.peliculas.domain.models.ArgumentsPeliculas.ARGUMENT_DIRECTOR
import com.example.peliculas.domain.models.ArgumentsPeliculas.ARGUMENT_GENERO
import com.example.peliculas.domain.models.ArgumentsPeliculas.ARGUMENT_IMAGE
import com.example.peliculas.domain.models.ArgumentsPeliculas.ARGUMENT_TITULO

class DialogEditPeliculas(
    val PeliculaToUpdate: Peliculas,
    val updatePeliculaDialog: (Peliculas) -> Unit
) : DialogFragment() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arguments = arguments
        setValuesIntoDialog(view, arguments)
    }

    private fun setValuesIntoDialog(view: View, arguments: Bundle?) {
        val titulo = view.findViewById<TextView>(R.id.titulo)
        val director = view.findViewById<TextView>(R.id.director)
        val genero = view.findViewById<TextView>(R.id.genero)
        val anno = view.findViewById<TextView>(R.id.anno)
        val imagen = view.findViewById<TextView>(R.id.image)

        if (arguments != null) {
            titulo.text= arguments.getString(ARGUMENT_TITULO)
            director.text=arguments.getString(ARGUMENT_DIRECTOR)
            genero.text = arguments.getString(ARGUMENT_GENERO)
            anno.text= arguments.getString(ARGUMENT_ANNO)
            imagen.text = arguments.getString(ARGUMENT_IMAGE)

            view.findViewById<Button>(R.id.btn_edit).setOnClickListener {
                updatePeliculaDialog(PeliculaToUpdate)
            }
        }
    }


}




