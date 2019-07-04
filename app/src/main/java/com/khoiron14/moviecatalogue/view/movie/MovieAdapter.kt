package com.khoiron14.moviecatalogue.view.movie

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.khoiron14.moviecatalogue.R
import com.khoiron14.moviecatalogue.model.Movie
import kotlinx.android.synthetic.main.item_movie.view.*

/**
 * Created by khoiron14 on 7/3/2019.
 */
class MovieAdapter(private val movies: ArrayList<Movie>, private val listener: (Movie) -> Unit) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_movie, p0, false)
        val result = ViewHolder(view)

        view.setOnClickListener {
            val position = result.adapterPosition

            if (position != RecyclerView.NO_POSITION) {
                val movie: Movie = movies[position]
                listener(movie)
            }
        }

        return result
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bind(movies[p1])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(movie: Movie) {
            Glide.with(itemView).load(movie.poster).into(itemView.img_poster)
            itemView.tv_title.text = movie.title
            itemView.tv_rating.text = movie.rating
        }
    }
}