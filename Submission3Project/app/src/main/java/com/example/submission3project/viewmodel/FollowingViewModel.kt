package com.example.submission3project.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission3project.data.api.ApiConfig
import com.example.submission3project.data.api.UserDataObject
import com.example.submission3project.data.api.UserFfAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel: ViewModel() {

    private val listFollowing = MutableLiveData<ArrayList<UserFfAdapter>>()
    private val privateIsLoading = MutableLiveData<Boolean>()
    val isloading: LiveData<Boolean> = privateIsLoading

    fun setUserFollowing(userLogin:String) {
        privateIsLoading.value = true
        val getFollowing = ApiConfig.getApiService().getUsersFollowing(userLogin)
        getFollowing.enqueue(object : Callback<List<UserDataObject>> {
            override fun onResponse(
                call: Call<List<UserDataObject>>,
                response: Response<List<UserDataObject>>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    val listFfObject = ArrayList<UserFfAdapter>()
                    for (data in responseBody) {
                        val ffAdapterObject = UserFfAdapter(data.login, data.avatarUrl)
                        listFfObject.add(ffAdapterObject)
                    }
                    listFollowing.postValue(listFfObject)
                    privateIsLoading.value = false
                }else{
                    Log.e(TAG,"onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserDataObject>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getUserFollowing(): LiveData<ArrayList<UserFfAdapter>> {
        return listFollowing
    }

    companion object{
        private val TAG = FollowingViewModel::class.java.simpleName
    }
}