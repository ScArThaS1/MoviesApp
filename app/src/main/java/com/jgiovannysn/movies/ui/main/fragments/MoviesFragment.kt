package com.jgiovannysn.movies.ui.main.fragments

import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jgiovannysn.movies.MoviesApplication
import com.jgiovannysn.movies.R
import com.jgiovannysn.movies.base.BaseFragment
import com.jgiovannysn.movies.ui.main.MainViewModel
import com.jgiovannysn.movies.ui.main.adapters.MoviesAdapter
import kotlinx.android.synthetic.main.fragment_movies.*

/**
 * @author JGiovannySN 06/01/22.
 * @version 1.0
 * @since 1.0
 */
class MoviesFragment: BaseFragment() {

    private lateinit var adapter: MoviesAdapter
    private var mainViewModel: MainViewModel? = null

    companion object {
        @JvmStatic
        fun newInstance() = MoviesFragment()
    }

    override fun getLayoutId(): Int = R.layout.fragment_movies

    override fun init() {
        MoviesApplication.appComponent.inject(this)

        adapter = MoviesAdapter()

        val layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)

        recycler.layoutManager = layoutManager
        recycler.setHasFixedSize(true)
        recycler.adapter = adapter
    }

    override fun initViewModel() {
        mainViewModel = activity?.let { ViewModelProviders.of(it).get(MainViewModel::class.java) }
    }

    override fun listeners() {

    }

    override fun observers() {
        mainViewModel?.fetchFromDatabase()
        mainViewModel?.movies?.observe(this, {
            adapter.data = it.toMutableList()
        })
    }
}