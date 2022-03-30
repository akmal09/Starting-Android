package com.example.submission3project.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.submission3project.data.entity.UserLikedEntity
import com.example.submission3project.repository.UserLikedRepository

class GetUserLikedViewModel(application: Application) : ViewModel() {
    val mUserLikedRepository: UserLikedRepository = UserLikedRepository(application)

    fun getAllLikedUser(): LiveData<List<UserLikedEntity>> = mUserLikedRepository.getUserLiked()

    fun insertUser(userLikedEntity: UserLikedEntity){
        mUserLikedRepository.insert(userLikedEntity)
    }

    fun deleteLiked(id: Int) {
        mUserLikedRepository.deleteLiked(id)
    }
}