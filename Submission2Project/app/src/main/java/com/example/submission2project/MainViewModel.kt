package com.example.submission2project

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    private val listItems = MutableLiveData<List<ItemsItem>>()

    fun setUserSearch(query:String) {
        val client = ApiConfig.getApiService().getSearchQuery(query)
        client.enqueue(object : Callback<ResponseData> {
            override fun onResponse(
                call: Call<ResponseData>,
                response: Response<ResponseData>
            ){
//            showLoading(false)
                if(response.isSuccessful){
                    val listItemsItem = ArrayList<ItemsItem>()
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val itemsItem = responseBody.items
                        for (data in itemsItem) {
                            val userAdapter = ItemsItem(data.login, data.url, data.avatarUrl)
                            listItemsItem.add(userAdapter)
                        }
                        listItems.postValue(listItemsItem)
                    }
                }else{
                Log.e(TAG, "onFailure: ${response.message()}")
                }

            }
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {

            }
        })
    }

    fun getUserSearch(): LiveData<List<ItemsItem>> {
        return listItems
    }

    companion object{
        private val TAG =FollowingViewModel::class.java.simpleName
    }
}

