package com.example.submission2project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.submission2project.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var detailUserActivityViewModel: DetailUserActivityViewModel
    private var followersUrl: String = ""
    private var reposUrl: String = ""

    companion object{
        const val EXTRA_PERSON = "extra_person"
        @StringRes
        private val JUDUL_TAB = intArrayOf(
            R.string.tab_followers,
            R.string.tab_following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userSearch = intent.getParcelableExtra<ItemsItem>(EXTRA_PERSON) as ItemsItem
        showLoading(true)

        detailUserActivityViewModel = ViewModelProvider(this@DetailUserActivity).get(DetailUserActivityViewModel::class.java)
        detailUserActivityViewModel.setUserDetail(userSearch.url)
        detailUserActivityViewModel.getUserDetail().observe(this,{
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

        detailUserActivityViewModel.setCountFollowing(userSearch.login)
        detailUserActivityViewModel.getTotalUsersFollowing().observe(this,{
            binding.following.text ="$it Following"
        })

        val sectionsPagerAdapter = SectionsPagerAdapter(this,userSearch.login)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager){tab, position ->
            tab.text = resources.getString(JUDUL_TAB[position])
        }.attach()
        supportActionBar?.elevation = 0f
        showLoading(false)

    }

    fun totalUsersFollowers(followersUrl:String){
        detailUserActivityViewModel.setCountFollowers(followersUrl)
        detailUserActivityViewModel.getTotalUsersFollowers().observe(this,{
            binding.followers.text = "$it Followers"
        })
    }

    fun totalRepos(reposUrl: String) {
        detailUserActivityViewModel.setCountRepository(reposUrl)
        detailUserActivityViewModel.getTotalUsersRepository().observe(this,{
            binding.repository.text = "$it repository"
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