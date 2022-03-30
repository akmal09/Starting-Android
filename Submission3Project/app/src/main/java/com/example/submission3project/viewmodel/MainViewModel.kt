package com.example.submission3project.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission3project.SettingsPreference
import com.example.submission3project.data.api.ApiConfig
import com.example.submission3project.data.api.ResponseData
import com.example.submission3project.data.entity.UserLikedEntity
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    private val listItems = MutableLiveData<List<UserLikedEntity>>()

    fun setUserSearch(query:String) {
        val client = ApiConfig.getApiService().getSearchQuery(query)
        client.enqueue(object : Callback<ResponseData> {
            override fun onResponse(
                call: Call<ResponseData>,
                response: Response<ResponseData>
            ){
                if(response.isSuccessful){
                    val listItemsItem = ArrayList<UserLikedEntity>()
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val itemsItem = responseBody.items
                        for (data in itemsItem) {
                            val userAdapter = UserLikedEntity(data.id, data.login, data.url, data.avatarUrl, isLiked = false)
                            listItemsItem.add(userAdapter)
                        }
                        listItems.postValue(listItemsItem)
                    }
                }else{
                Log.e(TAG, "onFailure: ${response.message()}")
                }

            }
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getUserSearch(): LiveData<List<UserLikedEntity>> {
        return listItems
    }

    companion object{
        private val TAG = FollowingViewModel::class.java.simpleName
    }
}

