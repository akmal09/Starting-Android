package com.example.submission3project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.submission3project.adapter.SectionsPagerAdapter
import com.example.submission3project.data.api.ItemsItem
import com.example.submission3project.data.entity.UserLikedEntity
import com.example.submission3project.databinding.ActivityDetailUserBinding
import com.example.submission3project.viewmodel.DetailUserViewModel
import com.example.submission3project.viewmodel.GetUserLikedViewModel
import com.example.submission3project.viewmodel.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var detailUserViewModel: DetailUserViewModel
    private var followersUrl: String = ""
    private var reposUrl: String = ""
    private lateinit var getUserLikedViewModel: GetUserLikedViewModel

    companion object{
        const val EXTRA_PERSON = "extra_person"
        @StringRes
        private val JUDUL_TAB = intArrayOf(
            R.string.tab_followers,
            R.string.tab_following
        )
        private val TAG = DetailUserActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        val viewModel: ViewModelFactory = ViewModelFactory.getInstance()

        val userSearch = intent.getParcelableExtra<ItemsItem>(EXTRA_PERSON) as UserLikedEntity
        detailUserViewModel = ViewModelProvider(this@DetailUserActivity).get(
            DetailUserViewModel::class.java)
        detailUserViewModel.isloading.observe(this, {
            showLoading(it)
        })
        detailUserViewModel.setUserDetail(userSearch.url)
        detailUserViewModel.getUserDetail().observe(this,{
            binding.apply {
                Glide.with(this@DetailUserActivity)
                    .load(it.ava)
                    .into(binding.avaPhotoDetail)
                userName.text = it.login
                name.text = it.name
                locationDetail.text = it.location
                companyDetail.text = it.company
            }
            followersUrl = it.followers_url
            totalUsersFollowers(followersUrl)
            reposUrl = it.repos_url
            totalRepos(reposUrl)
        })
        detailUserViewModel.setCountFollowing(userSearch.login!!)
        detailUserViewModel.getTotalUsersFollowing().observe(this,{
            val following = getString(R.string.following)
            binding.following.text ="$it $following"
        })
        val sectionsPagerAdapter = SectionsPagerAdapter(this,userSearch.login)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager){tab, position ->
            tab.text = resources.getString(JUDUL_TAB[position])
        }.attach()

        getUserLikedViewModel = obtainUserLikedViewModel(this@DetailUserActivity)

        setIsLiked(userSearch.isLiked)
        binding.likeIcon.setOnClickListener{
            if (userSearch.isLiked == false) {
                userSearch.isLiked = true
                getUserLikedViewModel.insertUser(userSearch)
                setIsLiked(userSearch.isLiked)
                Toast.makeText(this@DetailUserActivity, "Jadi Favorit Anda", Toast.LENGTH_SHORT).show()
            }else if(userSearch.isLiked == true ){
                userSearch.isLiked = false
                getUserLikedViewModel.deleteLiked(userSearch.id)
                setIsLiked(userSearch.isLiked)
                Toast.makeText(this@DetailUserActivity, "Bukan Favorit Anda Lagi", Toast.LENGTH_SHORT).show()
            }
        }
        supportActionBar?.elevation = 0f

    }

    private fun setIsLiked(liked: Boolean) {
        if (liked) {
            binding.likeIcon.setImageResource(R.drawable.ic_baseline_favorite_24)
        }else{
            binding.likeIcon.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }

    private fun obtainUserLikedViewModel(activity: AppCompatActivity):GetUserLikedViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(GetUserLikedViewModel::class.java)
    }

    fun totalUsersFollowers(followersUrl:String){
        detailUserViewModel.setCountFollowers(followersUrl)
        detailUserViewModel.getTotalUsersFollowers().observe(this,{
            val followers = getString(R.string.followers)
            binding.followers.text = "$it $followers"
        })
    }

    fun totalRepos(reposUrl: String) {
        detailUserViewModel.setCountRepository(reposUrl)
        detailUserViewModel.getTotalUsersRepository().observe(this,{
            Log.d(TAG, "$it repository" )
            val repository = getString(R.string.repository)
            binding.repository.text = "$it $repository"
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

}