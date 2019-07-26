package com.khoiron14.moviecatalogue.ui.tvshow

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.khoiron14.moviecatalogue.BuildConfig
import com.khoiron14.moviecatalogue.R
import com.khoiron14.moviecatalogue.model.tvshow.Tvshow
import kotlinx.android.synthetic.main.item_movie.view.*

/**
 * Created by khoiron14 on 7/3/2019.
 */
class TvshowAdapter(private val listener: (Tvshow) -> Unit) :
    RecyclerView.Adapter<TvshowAdapter.ViewHolder>() {

    private var tvshows: List<Tvshow> = listOf()

    fun setData(items: List<Tvshow>) {
        tvshows = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_movie, p0, false)
        val result = ViewHolder(view)

        view.setOnClickListener {
            val position = result.adapterPosition

            if (position != RecyclerView.NO_POSITION) {
                val tvshow: Tvshow = tvshows[position]
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
        fun bind(tvshow: Tvshow) {
            itemView.tv_title.text = tvshow.name
            Glide.with(itemView)
                .load(BuildConfig.BASE_IMAGE_PATH_URL + tvshow.posterPath)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(itemView.img_poster)
        }
    }
}