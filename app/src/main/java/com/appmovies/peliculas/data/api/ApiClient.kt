package com.appmovies.peliculas.data.api

import com.appmovies.peliculas.data.Paths
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiClient {
    @Headers("${ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY}:true")
    @GET(Paths.TOP_RATED)
    fun getTopRatedMovies(): Call<ResponseBody>

    @Headers("${ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY}:true")
    @GET(Paths.NOW_PLAYING)
    fun getNowPlayingMovies(): Call<ResponseBody>

    @Headers("${ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY}:true")
    @GET(Paths.DETAIL_MOVIE)
    fun getDetailMovie(@Path("idMovie") id:Int): Call<ResponseBody>
}