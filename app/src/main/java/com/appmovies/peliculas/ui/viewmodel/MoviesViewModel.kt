package com.appmovies.peliculas.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appmovies.peliculas.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val peliculaUseCase: PeliculaUseCase,
): ViewModel(){

    val offline = MutableLiveData<Boolean>(false)

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _moviesList = MutableLiveData<List<Pelicula>>()
    val movieList: LiveData<List<Pelicula>> = _moviesList

    private val _movie = MutableLiveData<Pelicula>()
    val movie: LiveData<Pelicula> = _movie

    private val _nowPlayingMoviesList = MutableLiveData<List<Pelicula>>()
    val nowPlayingMoviesList: LiveData<List<Pelicula>> = _nowPlayingMoviesList

    init {
        viewModelScope.launch {
            getTopRatedMovies()
            getNowPlayingMovies()
        }

    }

    private suspend fun getTopRatedMovies(){
        _isLoading.value = true
        offline.value = false
        _moviesList.value = peliculaUseCase.invokeLocaleTopRatedMovieList()
        peliculaUseCase.invokeGetTopRatedMovies(
            object : TopRatedMovieListener{
                override fun onSuccess(movieList: List<Pelicula>) {
                    _moviesList.value =movieList
                    _isLoading.value = false
                }

                override fun onErrror(error: String) {
                    Log.d("test error", "error $error")
                    _isLoading.value = false
                    offline.value = true
                }

            }
        )
    }
    private suspend fun getNowPlayingMovies(){
        _isLoading.value = true
        offline.value = false
        _nowPlayingMoviesList.value = peliculaUseCase.invokeLocaleNowPlayingMovieList()
        peliculaUseCase.invokeGetNowPlayingMovies(
            object : NowPlayingMovieListener{
                override fun onSuccess(movieList: List<Pelicula>) {
                    _nowPlayingMoviesList.value =movieList
                    _isLoading.value = false
                }

                override fun onErrror(error: String) {
                    Log.d("test error", "error $error")
                    _isLoading.value = false
                    offline.value = true
                }

            }
        )
    }

    suspend fun getDetailMovie(id: Int){
        viewModelScope.launch {
            _isLoading.value = true
            offline.value = false
            peliculaUseCase.invokeDetailMovie(
                id = id,
                listener = object : DetailMovieListener {
                    override fun onSuccess(movie: Pelicula) {
                        _movie.value = movie
                        _isLoading.value = false
                    }

                    override fun onErrror(error: String) {
                        Log.d("test error", "error $error")
                        _isLoading.value = false
                        offline.value = true
                    }

                }
            )
        }
    }

}