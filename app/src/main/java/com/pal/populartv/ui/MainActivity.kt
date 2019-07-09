package com.pal.populartv.ui

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pal.populartv.R
import com.pal.populartv.di.AppComponent
import com.pal.populartv.di.DaggerAppComponent
import com.pal.populartv.domain.entity.TvShow
import com.pal.populartv.utils.InfiniteScrollListener
import com.pal.populartv.viewmodel.TvShowsViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder().applicationContext(applicationContext).build()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var tvShowsViewModel: TvShowsViewModel

    private val recyclerView: RecyclerView by bind(R.id.recycler_view)
    private val textFeedback: TextView by bind(R.id.text_feedback)
    private val progress: ProgressBar by bind(R.id.progress)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appComponent.inject(this)

        setContentView(R.layout.activity_main)

        recyclerView.apply {
            val linearLayoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            layoutManager = linearLayoutManager
            itemAnimator = DefaultItemAnimator()
            adapter = TvShowsAdapter()
            addOnScrollListener(InfiniteScrollListener({ tvShowsViewModel.getTvShows() }, linearLayoutManager))
        }

        tvShowsViewModel = ViewModelProviders.of(this, viewModelFactory).get(TvShowsViewModel::class.java)
        tvShowsViewModel.tvShowsLiveData.observe(this, Observer { state -> viewStateChanged(state) })
        tvShowsViewModel.getTvShows()
    }

    private fun viewStateChanged(tvShowsState: ScreenState) {
        when (tvShowsState) {
            is ScreenState.Loading -> loading()
            is ScreenState.Error -> showError(tvShowsState.message)
            is ScreenState.Success -> updateTvShows(tvShowsState.tvShows)
        }
    }

    private fun updateTvShows(tvShows: List<TvShow>) {
        (recyclerView.adapter as TvShowsAdapter).addItems(tvShows)

        textFeedback.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        progress.visibility = View.GONE
    }

    private fun showError(message: String) {
        textFeedback.text = message

        textFeedback.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        progress.visibility = View.GONE
    }

    private fun loading() {
        textFeedback.visibility = View.GONE
        progress.visibility = View.VISIBLE
    }


    fun <T : View> Activity.bind(@IdRes res: Int): Lazy<T> {
        @Suppress("UNCHECKED_CAST")
        return lazy(LazyThreadSafetyMode.NONE) { findViewById<T>(res) }
    }
}
