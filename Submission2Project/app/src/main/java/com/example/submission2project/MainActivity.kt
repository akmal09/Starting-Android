package com.example.submission2project

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2project.databinding.ActivityMainBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import android.view.Menu
import androidx.lifecycle.ViewModelProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(false)
        supportActionBar?.title = "list user"
        val layoutManager = LinearLayoutManager(this)
        binding.listUsers.layoutManager = layoutManager
        val itemDecoration =DividerItemDecoration(this, layoutManager.orientation)
        binding.listUsers.addItemDecoration(itemDecoration)

        mainViewModel = ViewModelProvider(this@MainActivity).get(MainViewModel::class.java)

        mainViewModel.getUserSearch().observe(this,{item ->
            setUserAdapter(item)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean{
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                getUserFromApi(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    private fun setUserAdapter(userData: List<ItemsItem>) {
        val listItemsItem = ArrayList<ItemsItem>()
        for (data in userData) {
            val userAdapter = ItemsItem(data.login, data.url, data.avatarUrl)
            listItemsItem.add(userAdapter)
        }
        val adapter = UsersAdapter(listItemsItem)
        binding.listUsers.adapter = adapter
        adapter.setOnItemClick(object : UsersAdapter.OnClickItem{
            override fun onClicked(data: ItemsItem) {
                moveToDetail(data)
            }
        })
        showLoading(false)
    }

    private fun moveToDetail(data: ItemsItem) {
        val move = Intent(this@MainActivity, DetailUserActivity::class.java)
        move.putExtra(DetailUserActivity.EXTRA_PERSON, data)
        startActivity(move)
    }

    private fun getUserFromApi(query: String) {
        showLoading(true)
        mainViewModel.setUserSearch(query)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object{
        private const val TAG ="MainActivity"
    }

}