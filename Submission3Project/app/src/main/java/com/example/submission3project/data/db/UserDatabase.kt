package com.example.submission3project.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.submission3project.data.entity.UserLikedEntity

@Database(entities = [UserLikedEntity::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userLikedDao(): UserLikedDao

    companion object{
        @Volatile
        private var instance: UserDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): UserDatabase {
            if (instance == null) {
                synchronized(UserDatabase::class.java) {
                    instance = Room.databaseBuilder(context.applicationContext,
                    UserDatabase::class.java, "User.db").build()
                }
            }
            return instance as UserDatabase
        }
    }
}
