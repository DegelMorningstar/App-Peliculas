package com.appmovies.peliculas.data.api

import com.appmovies.peliculas.data.Sentences
import okhttp3.Interceptor
import okhttp3.Response
import java.lang.RuntimeException

object ApiServiceInterceptor:Interceptor {

    const val NEEDS_AUTH_HEADER_KEY = "needs_authentication"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()
        if(request.header(NEEDS_AUTH_HEADER_KEY)!=null){
            requestBuilder.addHeader("Authorization","Bearer " +Sentences.api_key)
        }
        return  chain.proceed(requestBuilder.build())
    }

}