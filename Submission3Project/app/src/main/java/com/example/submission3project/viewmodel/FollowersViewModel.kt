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

class FollowersViewModel : ViewModel() {

    private val listFollowers = MutableLiveData<List<UserFfAdapter>>()
    private val privateIsLoading = MutableLiveData<Boolean>()
    val isloading: LiveData<Boolean> = privateIsLoading

    fun setFollowers(userLogin: String?){
        privateIsLoading.value = true
        val getFollowers = ApiConfig.getApiService().getUserFollowers(userLogin)
        getFollowers.enqueue(object : Callback<List<UserDataObject>> {
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
                    listFollowers.postValue(listFfObject)
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

    fun getFollowers(): LiveData<List<UserFfAdapter>> {
        return listFollowers
    }

    companion object{
        private val TAG = FollowersViewModel::class.java.simpleName
    }
}