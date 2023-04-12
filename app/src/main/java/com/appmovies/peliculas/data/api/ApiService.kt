package com.appmovies.peliculas.data.api

import android.util.Log
import com.appmovies.peliculas.data.api.database.PeliculaDao
import com.appmovies.peliculas.data.api.responses.DetailResponse
import com.appmovies.peliculas.data.api.responses.NowPlayingResponse
import com.appmovies.peliculas.data.api.responses.TopRatedResponse
import com.appmovies.peliculas.data.api.util.Mapper
import com.appmovies.peliculas.domain.DetailMovieListener
import com.appmovies.peliculas.domain.NowPlayingMovieListener
import com.appmovies.peliculas.domain.TipoLista
import com.appmovies.peliculas.domain.TopRatedMovieListener
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ApiService @Inject constructor(
    private val apiClient: ApiClient,
    private val gson: Gson,
    private val peliculaDao: PeliculaDao
){

    suspend fun getTopRatedMoviesService(
        listener: TopRatedMovieListener
    ){
        withContext(Dispatchers.IO){
            val response = apiClient.getTopRatedMovies()
            response.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.d("RESPONSE/SERVICE", response.toString())
                    if (response.isSuccessful) {
                        val json = response.body()?.string() ?: ""
                        Log.d("test", json)
                        val topRatedResponse: TopRatedResponse =
                            gson.fromJson(json, TopRatedResponse::class.java)
                        Log.d("response", topRatedResponse.toString())
                        val movieList = topRatedResponse.results
                        movieList.map {
                            it.type = TipoLista.TOP_RATED.ordinal
                        }
                        peliculaDao.insertAll(movieList.toMutableList())
                        listener.onSuccess(movieList)
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    t.message?.let { listener.onErrror(it) }
                }

            })
        }
    }

    suspend fun getNowPlayingMoviesService(
        listener: NowPlayingMovieListener
    ){
        withContext(Dispatchers.IO){
            val response = apiClient.getNowPlayingMovies()
            response.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.d("RESPONSE/SERVICE", response.toString())
                    if (response.isSuccessful) {
                        val json = response.body()?.string() ?: ""
                        Log.d("test", json)
                        val nowPlayingResponse: NowPlayingResponse =
                            gson.fromJson(json, NowPlayingResponse::class.java)
                        Log.d("response", nowPlayingResponse.toString())
                        val movies = nowPlayingResponse.results
                        movies.map {
                            it.type = TipoLista.NOW_PLAYING.ordinal
                        }
                        peliculaDao.insertAll(movies.toMutableList())
                        listener.onSuccess(movies)
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    t.message?.let { listener.onErrror(it) }
                }

            })
        }
    }

    suspend fun getDetailMovieService(
        listener: DetailMovieListener,
        id: Int
    ){
        withContext(Dispatchers.IO){
            val response = apiClient.getDetailMovie(id)
            response.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.d("RESPONSE/SERVICE", response.toString())
                    if (response.isSuccessful) {
                        val json = response.body()?.string() ?: ""
                        Log.d("test", json)
                        val detailResponse: DetailResponse =
                            gson.fromJson(json, DetailResponse::class.java)
                        Log.d("response", detailResponse.toString())
                        val mapper = Mapper()
                        val movie = mapper.fromDetailResponseToMovieDomain(detailResponse)
                        Log.d("movie", movie.toString())
                        listener.onSuccess(movie)
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    t.message?.let { listener.onErrror(it) }
                }

            })
        }
    }
}