package com.example.submission2project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2project.databinding.FragmentFollowingBinding


class FollowingFragment(private val userLogin: String) : Fragment() {

    private lateinit var binding:FragmentFollowingBinding
    private lateinit var followingViewModel: FollowingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)
        followingViewModel.setUserFollowing(userLogin)
        followingViewModel.getUserFollowing().observe(viewLifecycleOwner, { listUserFollowing ->
            setFFAdapterFollowing(listUserFollowing)
        })
    }

    private fun setFFAdapterFollowing(listFfObject: ArrayList<UserFfAdapter>) {
        binding.listFollowing.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(activity)
        binding.listFollowing.layoutManager = layoutManager
        val ffAdapter = FfAdapter(listFfObject)
        binding.listFollowing.adapter = ffAdapter
    }
}