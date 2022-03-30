package com.example.submission3project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission3project.adapter.UsersAdapter
import com.example.submission3project.data.entity.UserLikedEntity
import com.example.submission3project.databinding.ActivityFavoriteUserBinding
import com.example.submission3project.viewmodel.GetUserLikedViewModel
import com.example.submission3project.viewmodel.ViewModelFactory

class FavoriteUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var usersLikedAdapter: UsersAdapter
    private lateinit var getUserLiked:GetUserLikedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_user)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Your Favorite"

        showLoading(true)
        val layoutManager = LinearLayoutManager(this)
        binding.listUsersLiked.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.listUsersLiked.addItemDecoration(itemDecoration)
        showLikedUserAdapter()
        getUserLiked = obtainUserLikedViewModel(this@FavoriteUserActivity)
        getUserLiked.getAllLikedUser().observe(this, {userLikedList ->
            if (userLikedList != null) {
                usersLikedAdapter.setData(userLikedList)
                showLoading(false)
            }
        })
    }

    private fun obtainUserLikedViewModel(activity: AppCompatActivity):GetUserLikedViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(GetUserLikedViewModel::class.java)
    }

    private fun showLikedUserAdapter() {
        binding.listUsersLiked.layoutManager = LinearLayoutManager(this)
        usersLikedAdapter = UsersAdapter()
        usersLikedAdapter.notifyDataSetChanged()
        binding.listUsersLiked.adapter = usersLikedAdapter
        usersLikedAdapter.setOnItemClick(object : UsersAdapter.OnClickItem {
            override fun onClicked(data: UserLikedEntity) {
                moveToDetail(data)
            }
        })
    }

    private fun moveToDetail(data: UserLikedEntity) {
        val move = Intent(this@FavoriteUserActivity, DetailUserActivity::class.java)
        move.putExtra(DetailUserActivity.EXTRA_PERSON, data)
        startActivity(move)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }
}