package com.example.submission1project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.submission1project.databinding.DetailUserBinding

class DetailUser : AppCompatActivity() {

    private lateinit var binding: DetailUserBinding

    companion object{
        const val EXTRA_PERSON = "extra_person"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<User>(EXTRA_PERSON) as User

        binding.avaPhotoDetail.setImageResource(user.avatar)
        binding.name.text = user.name
        binding.userName.text = user.username
        binding.followers.text = user.followers + " followers"
        binding.following.text = user.following + " following"
        binding.repository.text = user.repository + "\nrepository"
        binding.locationDetail.text = user.location
        binding.companyDetail.text = user.company
    }
}