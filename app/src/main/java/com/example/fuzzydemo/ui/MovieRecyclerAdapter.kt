package com.example.fuzzydemo.ui


import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.fuzzydemo.R
import com.example.fuzzydemo.models.Movie


internal class MovieRecyclerAdapter : RecyclerView.Adapter<MovieRecyclerAdapter.EmployeeViewHolder>() {

    private var movies: MutableList<Movie> = mutableListOf()

    fun clearAndaAddAllMovies(movies: List<Movie>) {
        this.movies.clear()
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    fun clear(){
        movies.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return EmployeeViewHolder(inflater.inflate(R.layout.item_movie, parent, false))
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {

        val model = movies[position]

        holder.title.text = model.title
        holder.releaseYear.text = model.releaseYear.toString()
        Glide.with(holder.itemView.context).load(model.poster).into(holder.thumbnail)



    }


    internal inner class EmployeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var thumbnail: ImageView = itemView.findViewById(R.id.thumbIv) as ImageView
        var title: TextView = itemView.findViewById<View>(R.id.titleTv) as TextView
        var releaseYear: TextView = itemView.findViewById<View>(R.id.releaseYearTv) as TextView


    }
}
