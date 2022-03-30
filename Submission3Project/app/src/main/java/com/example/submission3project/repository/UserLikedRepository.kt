package com.example.submission3project.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.submission3project.AppExecutors
import com.example.submission3project.data.api.ApiService
import com.example.submission3project.data.db.UserDatabase
import com.example.submission3project.data.db.UserLikedDao
import com.example.submission3project.data.entity.UserLikedEntity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserLikedRepository(application: Application){
    private val mUserLikedDao: UserLikedDao
    private val executorService:ExecutorService = Executors.newSingleThreadExecutor()

    init{
        val db = UserDatabase.getDatabase(application)
        mUserLikedDao = db.userLikedDao()
    }

    fun getUserLiked(): LiveData<List<UserLikedEntity>> = mUserLikedDao.getUserLiked()

    fun insert(user: UserLikedEntity) {
        executorService.execute{ mUserLikedDao.insertLikedUser(user) }
    }

    fun deleteLiked(id: Int) {
        executorService.execute{ mUserLikedDao.deleteLikedUser(id) }
    }
}