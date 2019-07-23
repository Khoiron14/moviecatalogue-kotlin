package com.khoiron14.moviecatalogue.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.khoiron14.moviecatalogue.R
import com.khoiron14.moviecatalogue.ui.movie.MovieFragment
import com.khoiron14.moviecatalogue.ui.tvshow.TvshowFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val movieFragment = MovieFragment()
    private val tvshowFragment = TvshowFragment()
    private var activeFragment: Fragment = movieFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.main_container, tvshowFragment, "2")
            .hide(tvshowFragment)
            .commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.main_container, movieFragment, "1")
            .commit()

        nav_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_movie -> {
                    loadMovieFragment()
                    activeFragment = movieFragment
                }
                R.id.navigation_tvshow -> {
                    loadTvshowFragment()
                    activeFragment = tvshowFragment
                }
            }
            true
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
            .hide(activeFragment)
            .show(movieFragment)
            .commit()
    }

    private fun loadTvshowFragment() {
        supportFragmentManager.beginTransaction()
            .hide(activeFragment)
            .show(tvshowFragment)
            .commit()
    }
}
