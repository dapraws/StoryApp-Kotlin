package com.example.storyapp_kotlin.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.storyapp_kotlin.R
import com.example.storyapp_kotlin.adapter.StoryListAdapter
import com.example.storyapp_kotlin.data.api.Story
import com.example.storyapp_kotlin.databinding.ActivityMainBinding
import com.example.storyapp_kotlin.utils.animateVisibility
import com.example.storyapp_kotlin.view.create.CreateActivity
import com.example.storyapp_kotlin.view.welcome.WelcomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var recyclerView: RecyclerView
    private lateinit var listAdapter: StoryListAdapter

    private var token: String = ""
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        token = intent.getStringExtra(EXTRA_TOKEN)!!

        setSwipeRefreshLayout()
        setRecyclerView()
        getAllStory()

        binding.fabCreateStory.setOnClickListener {
            Intent(this, CreateActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_logout -> {
                viewModel.saveUserToken("")
                Intent(this, WelcomeActivity::class.java).also { intent ->
                    startActivity(intent)
                    finish()
                }
                true
            }
            R.id.menu_setting -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getAllStory() {
        binding.viewLoading.animateVisibility(true)
        binding.swipeRefresh.isRefreshing = true

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {

                viewModel.getAllStory(token).collect { result ->
                    result.onSuccess { response ->
                        updateRecyclerViewData(response.stories)

                        binding.apply {
                            tvNotFoundError.animateVisibility(response.stories.isEmpty())
                            ivNotFoundError.animateVisibility(response.stories.isEmpty())
                            rvStories.animateVisibility(response.stories.isNotEmpty())
                            viewLoading.animateVisibility(false)
                            swipeRefresh.isRefreshing = false
                        }
                    }

                    result.onFailure {
                        Toast.makeText(
                            this@MainActivity,
                            getString(R.string.error),
                            Toast.LENGTH_SHORT
                        ).show()

                        binding.apply {
                            tvNotFoundError.animateVisibility(true)
                            ivNotFoundError.animateVisibility(true)
                            rvStories.animateVisibility(false)
                            viewLoading.animateVisibility(false)
                            swipeRefresh.isRefreshing = false
                        }
                    }
                }
            }
        }
    }

    private fun setSwipeRefreshLayout() {
        binding.swipeRefresh.setOnRefreshListener {
            getAllStory()
            binding.viewLoading.animateVisibility(false)
        }
    }

    private fun setRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(this)
        listAdapter = StoryListAdapter()

        recyclerView = binding.rvStories
        recyclerView.apply {
            adapter = listAdapter
            layoutManager = linearLayoutManager
        }
    }

    private fun updateRecyclerViewData(stories: List<Story>) {
        // SaveInstanceState of recyclerview before add new data
        // It's prevent the recyclerview to scroll again to the top when load new data
        val recyclerViewState = recyclerView.layoutManager?.onSaveInstanceState()

        // Add data to the adapter
        listAdapter.submitList(stories)

        // Restore last recyclerview state
        recyclerView.layoutManager?.onRestoreInstanceState(recyclerViewState)
    }

    companion object {
        const val EXTRA_TOKEN = "extra_token"
    }
}