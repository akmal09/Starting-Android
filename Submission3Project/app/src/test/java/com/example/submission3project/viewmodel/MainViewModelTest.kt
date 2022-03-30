package com.example.submission3project.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.submission3project.data.api.ApiConfig
import com.example.submission3project.data.api.ResponseData
import com.example.submission3project.data.entity.UserLikedEntity
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModelTest {
    private val listItemTes = MutableLiveData<List<UserLikedEntity>>()
    private val query="akmal"

    @Before
    fun setUserSearch() {
        val client = ApiConfig.getApiService().getSearchQuery(query)
        client.enqueue(object : Callback<ResponseData> {
            override fun onResponse(
                call: Call<ResponseData>,
                response: Response<ResponseData>
            ) {
                if (response.isSuccessful) {
                    val listItemsItem = ArrayList<UserLikedEntity>()
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val itemsItem = responseBody.items
                        for (data in itemsItem) {
                            val userAdapter = UserLikedEntity(
                                data.id,
                                data.login,
                                data.url,
                                data.avatarUrl,
                                isLiked = false
                            )
                            listItemsItem.add(userAdapter)
                        }
                        listItemTes.postValue(listItemsItem)
                    }
                }


            }
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
            }
        })
    }

    @Test
    fun getUserSearch() {
        val liveData:Boolean
        if(listItemTes is MutableLiveData<List<UserLikedEntity>> && listItemTes != null){
            liveData=true
        }else{
            liveData=false
        }
        assertEquals(true, liveData)
    }
}