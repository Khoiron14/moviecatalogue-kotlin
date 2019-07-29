package com.khoiron14.moviecatalogue.ui.favorite.movie

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bumptech.glide.Glide
import com.khoiron14.moviecatalogue.BuildConfig
import com.khoiron14.moviecatalogue.R
import com.khoiron14.moviecatalogue.model.favorite.MovieFavorite
import kotlinx.android.synthetic.main.item_movie.view.*

/**
 * Created by khoiron14 on 7/28/2019.
 */
class MovieFavoriteAdapter(private val listener: (MovieFavorite) -> Unit) :
    RecyclerView.Adapter<MovieFavoriteAdapter.ViewHolder>() {
    private var movies: List<MovieFavorite> = listOf()

    fun setData(items: List<MovieFavorite>) {
        movies = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_movie, p0, false)
        val result = ViewHolder(view)

        view.setOnClickListener {
            val position = result.adapterPosition

            if (position != RecyclerView.NO_POSITION) {
                val movie: MovieFavorite = movies[position]
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
        fun bind(movie: MovieFavorite) {
            itemView.tv_title.text = movie.movieTitle
            Glide.with(itemView)
                .load(BuildConfig.BASE_IMAGE_PATH_URL + movie.moviePosterPath)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(itemView.img_poster)
        }
    }
}