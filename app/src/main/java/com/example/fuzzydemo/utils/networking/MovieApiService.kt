package com.example.fuzzydemo.utils.networking

import android.util.Log
import com.example.fuzzydemo.models.MovieList
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

open class MovieApiService {

    private val movieApi = createMovieInfoClient()

    private fun createMovieInfoClient(): MovieInfoClient {

         val authInterceptor = Interceptor {

            Log.d("Request Url","${it.request().url}")
            val originalRequest = it.request()
            val newHttpUrl = originalRequest.url.newBuilder()
                .build()
            val newRequest = originalRequest.newBuilder()
                .url(newHttpUrl)
                .addHeader("X-RapidAPI-Host", API_HOST)
                .addHeader("X-RapidAPI-Key", API_KEY)
                .build()


            it.proceed(newRequest)
        }

       val loggingInterceptor = HttpLoggingInterceptor()


        val okHttpClient = createHttpClientBuilder().addInterceptor(loggingInterceptor).addInterceptor(authInterceptor).build()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build()
            .create(MovieInfoClient::class.java)

    }


    fun getMoviesBySearch(searchTerm:String):Single<MovieList>{

        return movieApi.getMovies(searchTerm)
    }




    companion object {
        private const val BASE_URL = "https://movie-database-imdb-alternative.p.rapidapi.com/"
        private const val API_KEY ="abfc22cf6dmsh8157dd24d0c0894p1daafajsne1a702488bc1"
        private const val API_HOST = "movie-database-imdb-alternative.p.rapidapi.com"
        private var instance: MovieApiService? = null

        fun getInstance(): MovieApiService {
            if (instance == null) {
                instance = MovieApiService()
            }
            return instance as MovieApiService
        }



        private fun createHttpClientBuilder(): OkHttpClient.Builder {
            return OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.MINUTES)
                .connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(600, TimeUnit.MINUTES)

        }
    }


}