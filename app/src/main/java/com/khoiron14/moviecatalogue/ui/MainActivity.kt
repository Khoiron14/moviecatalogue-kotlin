package com.khoiron14.moviecatalogue.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.khoiron14.moviecatalogue.R
import com.khoiron14.moviecatalogue.ui.movie.MovieFragment
import com.khoiron14.moviecatalogue.ui.tvshow.TvshowFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nav_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_movie -> {
                    loadMovieFragment()
                }
                R.id.navigation_tvshow -> {
                    loadTvshowFragment()
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
            R.id.action_change_setting -> {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadMovieFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, MovieFragment(), MovieFragment::class.java.simpleName)
            .commit()
    }

    private fun loadTvshowFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, TvshowFragment(), TvshowFragment::class.java.simpleName)
            .commit()
    }
}
