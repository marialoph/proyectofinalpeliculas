package com.example.peliculas.data

import com.example.peliculas.domain.models.Peliculas

object Repository {
    val listaPelis : List<Peliculas> = listOf(
        Peliculas("Mision Imposible", "Christopher McQuarrie", "Acción", 2023,"https://pics.filmaffinity.com/mission_impossible_dead_reckoning_part_one-902615661-large.jpg"),
        Peliculas("Ocean´s 8", "Gary Ross", "Comedia/crimen", 2018,"https://pics.filmaffinity.com/Ocean_s_8-925245994-mmed.jpg"),
        Peliculas("IT", "Andrés Muschietti", "Terror/misterio", 2017,"https://e00-marca.uecdn.es/assets/multimedia/imagenes/2019/09/04/15675951324126.jpg"),
        Peliculas("Malefica", "Robert Stromberg", "Ciencia ficción", 2015, "https://static.wikia.nocookie.net/disney/images/4/4d/Maleficent_poster.png/revision/latest?cb=20140514145417&path-prefix=es"),
        Peliculas("Titanic", "James Cameron", "Drama", 1997, "https://es.web.img3.acsta.net/c_310_420/medias/nmedia/18/86/91/41/19870073.jpg"),
        Peliculas("Avatar", "James Cameron","Fantasia", 2009,"https://i0.wp.com/imgs.hipertextual.com/wp-content/uploads/2020/01/hipertextual-arte-conceptual-avatar-2-muestra-nuevos-rincones-pandora-2020623395.jpg?fit=1200%2C759&quality=50&strip=all&ssl=1"),
        Peliculas("Interstellar", "Christopher Nolan", "Ciencia ficción", 2014, "https://images.justwatch.com/poster/193089381/s718/interstellar.jpg"),
        Peliculas("Escuadron Suicida", "James Gunn", "Ciencia ficción", 2021, "https://musicart.xboxlive.com/7/dfc15100-0000-0000-0000-000000000002/504/image.jpg?w=1920&h=1080"),
        Peliculas("Oppenheimer", "Christopher Nolan", "Suspense",2023,"https://www.aceprensa.com/wp-content/uploads/2023/07/oppenheimer.jpg")
    )


}