package com.raka.mviminiproject.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.raka.mviminiproject.R
import com.raka.mviminiproject.data.api.ApiHelperImpl
import com.raka.mviminiproject.data.api.RetrofitBuilder
import com.raka.mviminiproject.data.model.User
import com.raka.mviminiproject.ui.main.adapter.MainAdapter
import com.raka.mviminiproject.ui.main.intent.MainIntent
import com.raka.mviminiproject.ui.main.viewmodel.MainViewModel
import com.raka.mviminiproject.ui.main.viewstate.MainState
import com.raka.mviminiproject.util.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Tutorial for MVI with coroutine
 * https://blog.mindorks.com/mvi-architecture-android-tutorial-for-beginners-step-by-step-guide
 */
@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel:MainViewModel
    private var adapter = MainAdapter(arrayListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setupViewModel()
        observeViewModel()
        setupClicks()
    }

    private fun setupClicks() {
        buttonFetchUser.setOnClickListener {
            lifecycleScope.launch {
                mainViewModel.userIntent.send(MainIntent.FetchUser)
            }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch{
            mainViewModel.state.collect {
                when (it){
                    is MainState.idle -> {}
                    is MainState.loading -> {
                        buttonFetchUser.visibility = View.GONE
                        progressBar.visibility = View.VISIBLE
                    }
                    is MainState.Users -> {
                        progressBar.visibility = View.GONE
                        buttonFetchUser.visibility = View.GONE
                        renderList(it.user)
                    }
                    is MainState.Error -> {
                        progressBar.visibility = View.GONE
                        buttonFetchUser.visibility = View.VISIBLE
                        Toast.makeText(this@MainActivity,it.error,Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun renderList(user: List<User>) {
        recyclerView.visibility = View.VISIBLE
        user.let { listOfUsers-> listOfUsers.let { data -> adapter.addData(data) } }
        adapter.notifyDataSetChanged()
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.run { addItemDecoration(DividerItemDecoration(recyclerView.context,
            (recyclerView.layoutManager as LinearLayoutManager).orientation)) }
        recyclerView.adapter = adapter
    }

    private fun setupViewModel(){
        mainViewModel = ViewModelProviders.of(this,ViewModelFactory(ApiHelperImpl(RetrofitBuilder.apiService)))
            .get(MainViewModel::class.java)
    }
}