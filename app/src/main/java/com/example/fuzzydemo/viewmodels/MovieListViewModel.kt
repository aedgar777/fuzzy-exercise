package com.example.fuzzydemo.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.fuzzydemo.extensions.scheduleIOUI
import com.example.fuzzydemo.models.Movie
import com.example.fuzzydemo.utils.networking.MovieApiService
import io.reactivex.disposables.CompositeDisposable


class MovieListViewModel(application: Application) : BaseViewModel(application) {

    private val apiClient = MovieApiService.getInstance()
    private val disposable = CompositeDisposable()
    val movies = MutableLiveData<List<Movie>>()
    val moviesLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun fetchMoviesBySearch(searchTerm:String) {

        loading.value = true
        disposable.add(
            apiClient.getMoviesBySearch(searchTerm)
                .scheduleIOUI()!!.subscribe({

                    Log.d("TAG", "Call Succeeded, List size ${it.movies?.size}")

                    for (item in it.movies?: listOf()){
                        Log.d("Title:","${item.title}")
                    }

                    makeListOfRetrievedMovies(it.movies ?: listOf())
                }, {

                    Log.d("TAG", "Call failed with error ${it.localizedMessage}")

                    moviesLoadError.value = true
                    loading.value = false
                    it.printStackTrace()
                })

        )
    }

    private fun makeListOfRetrievedMovies(list: List<Movie>) {
        movies.value = list
        moviesLoadError.value = false
        loading.value = false
    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}