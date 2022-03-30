package com.example.submission3project.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.submission3project.FollowersFragment
import com.example.submission3project.FollowingFragment

class SectionsPagerAdapter(activity : AppCompatActivity, private val userLogin:String): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment(userLogin)
            1 -> fragment = FollowingFragment(userLogin)
        }

        return fragment as Fragment
    }

}