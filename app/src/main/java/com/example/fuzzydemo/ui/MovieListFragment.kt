package com.example.fuzzydemo.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fuzzydemo.R
import com.example.fuzzydemo.models.Movie
import com.example.fuzzydemo.viewmodels.MovieListViewModel
import kotlinx.android.synthetic.main.fragment_movie_list.*


class MovieListFragment : Fragment() {

    private lateinit var viewModel: MovieListViewModel
    private val movieAdapter = MovieRecyclerAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        searchBar.setOnClickListener {
            movieAdapter.clear()
            viewModel.fetchMoviesBySearch(   searchEt.text.toString())
        }
        movieRecycler.apply {
            layoutManager = GridLayoutManager(context,2)
            adapter = movieAdapter
        }
        viewModel = ViewModelProviders.of(this).get(MovieListViewModel::class.java)




        observeViewModel()

    }


    private fun observeViewModel() {

        viewModel.movies.observe(viewLifecycleOwner) { movies: List<Movie> ->

            movies.let {

                movieAdapter.clearAndaAddAllMovies(movies)

                if (it.isEmpty()) {

                    emptyState.visibility = View.VISIBLE
                    movieRecycler.visibility = View.GONE
                    errorTv.text = getString(R.string.no_movies)
                } else {

                    movieRecycler.visibility = View.VISIBLE
                    emptyState.visibility = View.GONE
                }
            }
        }


        viewModel.moviesLoadError.observe(viewLifecycleOwner) { isError: Boolean ->

            isError.let {

                if (it){
                    errorTv.visibility = View.VISIBLE
                    errorTv.text = getString(R.string.error_loading_movies)
                }


            }

        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading: Boolean ->

            isLoading.let {
                loadingView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    emptyState.visibility = View.GONE
                    movieRecycler.visibility = View.GONE
                }
            }
        }
    }

}