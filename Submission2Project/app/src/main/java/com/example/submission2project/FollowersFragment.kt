package com.example.submission2project

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2project.databinding.FragmentFollowersBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersFragment(private val userLogin: String) : Fragment() {

    private lateinit var binding: FragmentFollowersBinding
    private lateinit var followersViewModel: FollowersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        followersViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel::class.java)
        followersViewModel.isloading.observe(viewLifecycleOwner,{
            showLoading(it)
        })
        followersViewModel.setFollowers(userLogin)
        followersViewModel.getFollowers().observe(viewLifecycleOwner, {listFollowers->
            setFFAdapterFollowers(listFollowers)
        })
    }

    private fun setFFAdapterFollowers(listFfObject : ArrayList<UserFfAdapter>) {
        binding.listFollowers.setHasFixedSize(true)
        Log.d(TAG, "berhasil kirim ${listFfObject.size}")
        val layoutManager = LinearLayoutManager(activity)
        binding.listFollowers.layoutManager = layoutManager
        val ffAdapter = FfAdapter(listFfObject)
        binding.listFollowers.adapter = ffAdapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object{
        private val TAG = DetailUserActivity::class.java.simpleName
    }
}
