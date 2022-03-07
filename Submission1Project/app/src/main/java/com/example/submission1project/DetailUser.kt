package com.example.submission1project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class DetailUser : AppCompatActivity() {

    companion object{
        const val EXTRA_PERSON = "extra_person"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_user)

        val user = intent.getParcelableExtra<User>(EXTRA_PERSON) as User

        val avaPhotoDetail : ImageView = findViewById(R.id.ava_photo_detail)
        avaPhotoDetail.setImageResource(user.avatar)

        val name:TextView = findViewById(R.id.name)
        name.text = user.name

        val userName:TextView = findViewById(R.id.user_name)
        userName.text = user.username

        val followers:TextView = findViewById(R.id.followers)
        followers.text = user.followers + " followers"

        val following:TextView = findViewById(R.id.following)
        following.text = user.following + " following"

        val repository:TextView = findViewById(R.id.repository)
        repository.text = user.repository + "\nrepository"

        val locationDetail:TextView = findViewById(R.id.location_detail)
        locationDetail.text = user.location

        val companyDetail:TextView = findViewById(R.id.company_detail)
        companyDetail.text = user.company

    }
}