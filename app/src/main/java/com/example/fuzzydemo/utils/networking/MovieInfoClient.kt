package com.example.fuzzydemo.utils.networking

import com.example.fuzzydemo.models.MovieList
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieInfoClient {

    @GET(".")
    fun getMovies(@Query("s") searchString: String): Single<MovieList>



}