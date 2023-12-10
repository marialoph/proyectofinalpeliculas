package com.example.peliculas.dialogues

import com.example.peliculas.adapter.ViewPeliculas

class Adapter (var modules : List<String>,

               val onDeleteClick: (Int) -> Unit
) {

    init {
        paintViews()
    }

    private fun paintViews() {
        for (i in modules.indices) {
            val module = modules[i]
            onDeleteClick(i)
        }
    }
}