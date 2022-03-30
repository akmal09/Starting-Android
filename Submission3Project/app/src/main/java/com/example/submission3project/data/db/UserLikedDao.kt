package com.example.submission3project.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.submission3project.data.entity.UserLikedEntity
import retrofit2.http.DELETE

@Dao
interface UserLikedDao {
    @Query("SELECT * from user_like where isLiked = 1")
    fun getUserLiked(): LiveData<List<UserLikedEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLikedUser(userLikedEntity: UserLikedEntity)

    @Query("DELETE from user_like where id=:id")
    fun deleteLikedUser(id:Int)
}