package com.khoiron14.moviecatalogue.ui.tvshow

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bumptech.glide.Glide
import com.khoiron14.moviecatalogue.BuildConfig
import com.khoiron14.moviecatalogue.R
import com.khoiron14.moviecatalogue.database.database
import com.khoiron14.moviecatalogue.model.favorite.TvShowFavorite
import com.khoiron14.moviecatalogue.model.tvshow.TvShow
import com.khoiron14.moviecatalogue.visible
import kotlinx.android.synthetic.main.item_movie.view.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

/**
 * Created by khoiron14 on 7/3/2019.
 */
class TvShowAdapter(private val listener: (TvShow) -> Unit) :
    RecyclerView.Adapter<TvShowAdapter.ViewHolder>() {

    private var tvShows: List<TvShow> = listOf()

    fun setData(items: List<TvShow>) {
        tvShows = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_movie, p0, false)
        val result = ViewHolder(view)

        view.setOnClickListener {
            val position = result.adapterPosition

            if (position != RecyclerView.NO_POSITION) {
                val tvShow: TvShow = tvShows[position]
                listener(tvShow)
            }
        }
        return result
    }

    override fun getItemCount(): Int = tvShows.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bind(tvShows[p1])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(tvShow: TvShow) {
            itemView.tv_title.text = tvShow.name
            Glide.with(itemView)
                .load(BuildConfig.BASE_IMAGE_PATH_URL + tvShow.posterPath)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(itemView.img_poster)
            itemView.context.database.use {
                val result = select(TvShowFavorite.TABLE_TVSHOW_FAVORITE)
                    .whereArgs("(TVSHOW_ID = {id})", "id" to tvShow.id!!)
                val tvShowFav = result.parseList(classParser<TvShowFavorite>())
                if (tvShowFav.isNotEmpty()) {
                    itemView.favorite.visible()
                }
            }
        }
    }
}