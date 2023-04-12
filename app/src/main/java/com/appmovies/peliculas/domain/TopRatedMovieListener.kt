package com.appmovies.peliculas.domain

interface TopRatedMovieListener {
    fun onSuccess(movieList: List<Pelicula>)
    fun onErrror(error: String)
}