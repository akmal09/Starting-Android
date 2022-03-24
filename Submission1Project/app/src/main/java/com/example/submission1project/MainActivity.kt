package com.example.submission1project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var showUser: RecyclerView
    private val listInject = ArrayList<User>()
    private val listUsers: ArrayList<User>
        get() {
            val dataUserName = resources.getStringArray(R.array.username)
            val dataName = resources.getStringArray(R.array.name)
            val dataLocation = resources.getStringArray(R.array.location)
            val dataCompany = resources.getStringArray(R.array.company)
            val dataAvaPhoto = resources.obtainTypedArray(R.array.avatar)
            val dataFollowers = resources.getStringArray(R.array.followers)
            val dataFollowing = resources.getStringArray(R.array.following)
            val dataRepsitory = resources.getStringArray(R.array.repository)
            val listUser = ArrayList<User>()
            for (i in dataName.indices) {
                val user = User(
                    dataUserName[i], dataName[i],
                    dataLocation[i], dataRepsitory[i], dataCompany[i],
                    dataFollowers[i], dataFollowing[i], dataAvaPhoto.getResourceId(i,-1)
                )
                listUser.add(user)
            }
            return listUser
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showUser = findViewById(R.id.list_users)
        showUser.setHasFixedSize(true)
        listInject.addAll(listUsers)

        showRecyclerList()
    }

    private fun showRecyclerList() {
        showUser.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = UsersAdapter(listInject)
        showUser.adapter = listUserAdapter

        listUserAdapter.setOnItemClickAva(object : UsersAdapter.OnItemClickAva {
            override fun onItemClicked(data: User) {
                moveToDetail(data)
            }
        })

    }

    private fun moveToDetail(user: User) {
        val moveToDetail = Intent(this@MainActivity, DetailUser::class.java)
        moveToDetail.putExtra(DetailUser.EXTRA_PERSON, user)
        startActivity(moveToDetail)
    }
}