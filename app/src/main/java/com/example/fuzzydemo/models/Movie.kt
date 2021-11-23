package com.example.fuzzydemo.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class MovieList(
    @Json(name = "Search")
    var movies: List<Movie>?
)

data class Movie(
    @Json(name = "Title") val title: String?,
    @Json(name = "Year") var releaseYear: String?,
    @Json(name = "Type") var type: String?,
    @Json(name = "Poster") var poster: String,

    )