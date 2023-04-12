package com.appmovies.peliculas.domain

import com.appmovies.peliculas.data.api.ApiService
import com.appmovies.peliculas.data.api.database.PeliculaDao
import com.appmovies.peliculas.data.api.database.PeliculaDataBase
import javax.inject.Inject

class PeliculaUseCase @Inject constructor(
    private val apiService: ApiService,
    private val peliculaDao: PeliculaDao
) {

    fun invokeLocaleTopRatedMovieList():List<Pelicula> {
        val movies = peliculaDao.getPeliculas()
        return movies.filter {
            it.type == TipoLista.TOP_RATED.ordinal
        }.sortedByDescending {
            it.vote_average
        }
    }
    fun invokeLocaleNowPlayingMovieList():List<Pelicula> {
        val movies = peliculaDao.getPeliculas()
        return movies.filter {
            it.type == TipoLista.NOW_PLAYING.ordinal
        }.sortedByDescending {
            it.release_date
        }
    }

    fun invokeNumberOfItemsInDb() =
        peliculaDao.numberOfItemsInDB()

    suspend fun invokeGetTopRatedMovies(
        listener: TopRatedMovieListener
    ){
        apiService.getTopRatedMoviesService(
            listener
        )
    }

    suspend fun invokeGetNowPlayingMovies(
        listener: NowPlayingMovieListener
    ){
        apiService.getNowPlayingMoviesService(
            listener
        )
    }
    suspend fun invokeDetailMovie(
        listener: DetailMovieListener,
        id:Int
    ){
        apiService.getDetailMovieService(
            listener,
            id
        )
    }
}