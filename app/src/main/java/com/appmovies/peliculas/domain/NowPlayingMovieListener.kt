package com.appmovies.peliculas.domain

interface NowPlayingMovieListener {
    fun onSuccess(movieList: List<Pelicula>)
    fun onErrror(error: String)
}