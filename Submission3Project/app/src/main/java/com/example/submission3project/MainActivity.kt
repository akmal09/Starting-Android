package com.example.submission3project

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission3project.databinding.ActivityMainBinding
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.example.submission3project.adapter.UsersAdapter
import com.example.submission3project.data.entity.UserLikedEntity
import com.example.submission3project.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userSearchadapter: UsersAdapter
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

        setUserAdapter()
        mainViewModel.getUserSearch().observe(this,{item ->
            userSearchadapter.setData(item)
            showLoading(false)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        setMode(item.itemId)
        return super.onOptionsItemSelected(item)
    }

    private fun setMode(selectedMode: Int) {
        when (selectedMode) {
            R.id.user_liked ->{
                goToFavoriteUserActivity()
            }
            R.id.action_settings ->{
                goToSetting()
            }
        }
    }

    private fun goToFavoriteUserActivity() {
        startActivity(Intent(this@MainActivity, FavoriteUserActivity::class.java))
    }

    private fun goToSetting() {
        startActivity(Intent(this@MainActivity, SettingPrefActivity::class.java))
    }

    private fun setUserAdapter() {
        binding.listUsers.layoutManager = LinearLayoutManager(this)
        userSearchadapter = UsersAdapter()
        userSearchadapter.notifyDataSetChanged()
        binding.listUsers.adapter  = userSearchadapter
        userSearchadapter.setOnItemClick(object : UsersAdapter.OnClickItem{
            override fun onClicked(data: UserLikedEntity) {
                moveToDetail(data)
            }
        })
    }

    private fun moveToDetail(data: UserLikedEntity) {
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