package com.khoiron14.moviecatalogue.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.khoiron14.moviecatalogue.R
import com.khoiron14.moviecatalogue.ui.favorite.FavoriteFragment
import com.khoiron14.moviecatalogue.ui.movie.MovieFragment
import com.khoiron14.moviecatalogue.ui.tvshow.TvShowFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nav_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_movie -> {
                    loadMovieFragment()
                    supportActionBar?.elevation = 4F
                }
                R.id.navigation_tvshow -> {
                    loadTvShowFragment()
                    supportActionBar?.elevation = 4F
                }
                R.id.navigation_favorite -> {
                    loadFavoriteFragment()
                    supportActionBar?.elevation = 0F
                }
            }
            true
        }

        if (savedInstanceState == null) {
            nav_view.selectedItemId = R.id.navigation_movie
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.settings -> {
                startActivity<SettingsActivity>()
            }
        }
        return super.onOptionsItemSelected(item!!)
    }

    private fun loadMovieFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, MovieFragment(), MovieFragment::class.java.simpleName)
            .commit()
    }

    private fun loadTvShowFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, TvShowFragment(), TvShowFragment::class.java.simpleName)
            .commit()
    }

    private fun loadFavoriteFragment() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.main_container,
                FavoriteFragment(),
                FavoriteFragment::class.java.simpleName
            )
            .commit()
    }
}
