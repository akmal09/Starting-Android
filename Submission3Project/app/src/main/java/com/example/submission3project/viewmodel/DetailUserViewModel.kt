package com.example.submission3project.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission3project.BuildConfig
import com.example.submission3project.DetailUserActivity
import com.example.submission3project.data.api.ApiConfig
import com.example.submission3project.data.api.UserDataObject
import com.example.submission3project.data.api.UserDetail
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel: ViewModel() {

    private val privateIsLoading = MutableLiveData<Boolean>()
    val isloading: LiveData<Boolean> = privateIsLoading

    val userDetail = MutableLiveData<UserDetail>()
    val totalFollowersUserUrl = MutableLiveData<String>()
    val totalFollowingUserUrl = MutableLiveData<String>()
    val totalRepositoryUrl = MutableLiveData<String>()

    fun setUserDetail(userData: String) {
        privateIsLoading.value = true
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        client.get(userData, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {
                val result = String(responseBody)
                try {
                    val jsonObject =JSONObject(result)
                    val login = jsonObject.getString("login")
                    val name = jsonObject.getString("name")
                    val ava = jsonObject.getString("avatar_url")
                    val followersUrl = jsonObject.getString("followers_url")
                    val location = jsonObject.getString("location")
                    val company = jsonObject.getString("company")
                    val repos_url = jsonObject.getString("repos_url")

                    val userInstance= UserDetail(login,name,company,location, repos_url,followersUrl, ava)
                    userDetail.postValue(userInstance)
                    privateIsLoading.value = false
                } catch (e: Exception) {

                }
            }
            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                privateIsLoading.value = false
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Log.e(TAG, errorMessage)
            }
        })
    }

    fun setCountFollowers(followersUrl:String) {
        val accessFollowers = AsyncHttpClient()
        accessFollowers.get(followersUrl, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                try {
                    val jsonArray =JSONArray(result)
                    val jsonArraySize = jsonArray.length()
                    totalFollowersUserUrl.postValue(jsonArraySize.toString())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Log.e(TAG, errorMessage)
            }
        })
        accessFollowers.addHeader("Authorization", "token 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")
        accessFollowers.addHeader("User-Agent","request")
    }

    fun setCountFollowing(loginName:String?) {
        val followingAccess = ApiConfig.getApiService().getUsersFollowing(loginName)
        followingAccess.enqueue(object : Callback<List<UserDataObject>> {
            override fun onResponse(
                call: Call<List<UserDataObject>>,
                response: Response<List<UserDataObject>>
            ) {
                val responseBody =response.body()
                if (response.isSuccessful && responseBody!=null) {
                    val total = responseBody.size
                    totalFollowingUserUrl.postValue(total.toString())
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserDataObject>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun setCountRepository(repo:String) {
        val repoAccess = AsyncHttpClient()
        repoAccess.get(repo, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                try {
                    val jsonArray = JSONArray(result)
//                    Log.d(TAG, "JML REPO ${jsonArray.length()}")
                    val jsonArraySize = jsonArray.length()
                    totalRepositoryUrl.postValue(jsonArraySize.toString())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Log.e(TAG, errorMessage)
            }
        })
        repoAccess.addHeader("Authorization", "token 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")
        repoAccess.addHeader("User-Agent", "request")
    }

    fun getTotalUsersFollowing(): LiveData<String> {
        return totalFollowingUserUrl
    }

    fun getTotalUsersFollowers(): LiveData<String> {
        return totalFollowersUserUrl
    }

    fun getTotalUsersRepository(): LiveData<String> {
        return totalRepositoryUrl
    }

    fun getUserDetail(): LiveData<UserDetail> {
        return userDetail
    }

    companion object{
        private val TAG = DetailUserActivity::class.java.simpleName
    }
}