package com.example.submission3project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission3project.adapter.FfAdapter
import com.example.submission3project.data.api.UserFfAdapter
import com.example.submission3project.databinding.FragmentFollowingBinding
import com.example.submission3project.viewmodel.FollowingViewModel


class FollowingFragment() : Fragment() {

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
        val userLogin = arguments?.getString(ARG_USER)
        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowingViewModel::class.java)
        followingViewModel.isloading.observe(viewLifecycleOwner,{
            showLoading(it)
        })
        followingViewModel.setUserFollowing(userLogin)
        followingViewModel.getUserFollowing().observe(viewLifecycleOwner, { listUserFollowing ->
            setFFAdapterFollowing(listUserFollowing)
        })
//        if (savedInstanceState == null) {
//            followingViewModel.setUserFollowing(ARG_USER)
//            followingViewModel.getUserFollowing().observe(viewLifecycleOwner, { listUserFollowing ->
//                setFFAdapterFollowing(listUserFollowing)
//            })
//        }
    }

    private fun setFFAdapterFollowing(listFfObject: List<UserFfAdapter>) {
        binding.listFollowing.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(activity)
        binding.listFollowing.layoutManager = layoutManager
        val ffAdapter = FfAdapter(listFfObject)
        binding.listFollowing.adapter = ffAdapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object{
        private val ARG_USER = "userLogin"

        fun newInstance(userLogin: String?): FollowingFragment {
            val fragment = FollowingFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USER, userLogin)
            fragment.arguments = bundle

            return fragment
        }
    }
}