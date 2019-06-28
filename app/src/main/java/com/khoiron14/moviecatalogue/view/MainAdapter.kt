package com.khoiron14.moviecatalogue.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.khoiron14.moviecatalogue.R
import com.khoiron14.moviecatalogue.model.Movie
import kotlinx.android.synthetic.main.item_movie.view.*


/**
 * Created by khoiron14 on 6/27/2019.
 */
class MainAdapter(private val context: Context, private val movies: ArrayList<Movie>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)

        val movie = getItem(position)
        view.tv_title.text = movie.title
        view.tv_rating.text = movie.rating
        view.tv_release.text = movie.releaseYear
        view.tv_overview.text = movie.overview
        Glide.with(view).load(movie.poster).into(view.img_poster)
        return view
    }

    override fun getItem(position: Int): Movie = movies[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = movies.size
}