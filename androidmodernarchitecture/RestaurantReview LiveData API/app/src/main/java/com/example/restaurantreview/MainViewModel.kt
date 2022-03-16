package com.example.restaurantreview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {


    private val privateRestaurant = MutableLiveData<Restaurant>()
    val restaurant: LiveData<Restaurant> = privateRestaurant

    private val privateListReview = MutableLiveData<List<CustomerReviewsItem>>()
    val listReview: LiveData<List<CustomerReviewsItem>> = privateListReview

    private val privateIsLoading = MutableLiveData<Boolean>()
    val isloading: LiveData<Boolean> = privateIsLoading

    companion object{
        private const val TAG = "MainViewModel"
        private const val RESTAURANT_ID = "uewq1zg2zlskfw1e867"
    }

    init {
        findRestaurant()
    }

    private fun findRestaurant() {

        privateIsLoading.value = true
        val client = ApiConfig.getApiService().getRestaurant(RESTAURANT_ID)
        client.enqueue(object : Callback<ResponseRestaurant> {
            override fun onResponse(
                call: Call<ResponseRestaurant>,
                response: Response<ResponseRestaurant>
            ) {
                privateIsLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        privateRestaurant.value = response.body()?.restaurant
                        privateListReview.value = response.body()?.restaurant?.customerReviews
                    }
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseRestaurant>, t: Throwable) {
                privateIsLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun postReview(review: String) {
        privateIsLoading.value = true
        val client = ApiConfig.getApiService().postReview(RESTAURANT_ID, "Dicoding", review)
        client.enqueue(object : Callback<PostReviewResponse> {
            override fun onResponse(
                call: Call<PostReviewResponse>,
                response: Response<PostReviewResponse>
            ) {
                privateIsLoading.value = false
                if (response.isSuccessful) {
                    privateListReview.value = response.body()?.customerReviews
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<PostReviewResponse>, t: Throwable) {
                privateIsLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }


}