package com.example.peliculas.controler

import android.content.Context
import android.widget.Toast
import com.example.peliculas.MainActivity
import com.example.peliculas.adapter.AdapterPeliculas
import com.example.peliculas.dao.DaoPeliculas
import com.example.peliculas.models.Peliculas

class Controller ( val context : Context){
    private lateinit var adapter : AdapterPeliculas
    private lateinit var myList: MutableList<String>
    lateinit var listPelis : MutableList<Peliculas>
    init {

        initData()
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
        adapter = AdapterPeliculas(listPelis) { position ->
            deletePeliculas(position)
        }
        myActivity. binding.myRecyclerView.adapter = adapter


    }
    private fun deletePeliculas(pos: Int) {

        Toast.makeText(context, "Has eliminado esta peliculas ${listPelis[pos].titulo}", Toast.LENGTH_SHORT).show()
        listPelis.removeAt(pos)
        adapter.notifyItemRemoved(pos)
    }
}