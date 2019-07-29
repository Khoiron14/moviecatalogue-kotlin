package com.khoiron14.moviecatalogue.ui.favorite.tvshow

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bumptech.glide.Glide
import com.khoiron14.moviecatalogue.BuildConfig
import com.khoiron14.moviecatalogue.R
import com.khoiron14.moviecatalogue.model.favorite.TvshowFavorite
import kotlinx.android.synthetic.main.item_movie.view.*

/**
 * Created by khoiron14 on 7/28/2019.
 */
class TvshowFavoriteAdapter(private val listener: (TvshowFavorite) -> Unit) :
    RecyclerView.Adapter<TvshowFavoriteAdapter.ViewHolder>() {
    private var tvshows: List<TvshowFavorite> = listOf()

    fun setData(items: List<TvshowFavorite>) {
        tvshows = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_movie, p0, false)
        val result = ViewHolder(view)

        view.setOnClickListener {
            val position = result.adapterPosition

            if (position != RecyclerView.NO_POSITION) {
                val tvshow: TvshowFavorite = tvshows[position]
                listener(tvshow)
            }
        }
        return result
    }

    override fun getItemCount(): Int = tvshows.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bind(tvshows[p1])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(tvshow: TvshowFavorite) {
            itemView.tv_title.text = tvshow.tvshowName
            Glide.with(itemView)
                .load(BuildConfig.BASE_IMAGE_PATH_URL + tvshow.tvshowPosterPath)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(itemView.img_poster)
        }
    }
}