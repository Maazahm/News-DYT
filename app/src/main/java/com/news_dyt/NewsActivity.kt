package com.news_dyt

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.news_dyt.adapter.NewsAdapter
import com.news_dyt.utils.Constants
import com.news_dyt.utils.Resource
import com.news_dyt.viewmodels.NewsViewModel
import com.news_dyt.viewmodels.NewsViewModelFactory
import kotlinx.android.synthetic.main.activity_news.*
import javax.inject.Inject

class NewsActivity : AppCompatActivity() {

    lateinit var newsViewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    // Field Injection
    @Inject
    lateinit var newsViewModelFactory: NewsViewModelFactory

    private val news: RecyclerView
    get() = findViewById(R.id.newsRecyclerView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        (application as HelperApplication).applicationComponent.inject(this)
        newsViewModel = ViewModelProvider(this, newsViewModelFactory).get(NewsViewModel::class.java)

        setupRecyclerView()
        newsViewModel.newsLiveData.observe(this, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    //hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                        Log.d(Constants.TAG, "activity: response.success ${newsResponse.articles}")
                    }
                }
                is Resource.Error -> {
                    //hideProgressBar()
                    response.message?.let { message ->
                        Log.d(Constants.TAG, "activity: response.error $message")
                    }
                }
                is Resource.Loading -> {
                    //showProgressBar()
                }
            }
        })

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (direction == ItemTouchHelper.RIGHT) {
                    val position = viewHolder.adapterPosition
                    val article = newsAdapter.differ.currentList[position]
                    newsViewModel.deleteArticle(article)
                    Log.d(Constants.TAG, "activity: onSwipe delete $direction $position $article")
                    Snackbar.make(viewHolder.itemView, "Article removed successfully!", Snackbar.LENGTH_LONG)
                        .apply {
                            setAction("Undo") {
                                newsViewModel.insert(article)
                                Log.d(Constants.TAG, "activity: onSwipe after delete insert $article")
                            }
                            show()
                        }
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(newsRecyclerView)
        }

        newsViewModel.savedNewsLiveData.observe(this, Observer { articles ->
            newsAdapter.differ.submitList(articles)
            Log.d(Constants.TAG, "activity: observe $articles")
        })
    }

    /*private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }*/

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        newsRecyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(this@NewsActivity)
        }
    }
}