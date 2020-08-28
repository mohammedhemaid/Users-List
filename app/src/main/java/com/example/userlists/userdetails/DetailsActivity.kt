package com.example.userlists.userdetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.userlists.commondialogs.TimeOutDialog
import com.example.userlists.databinding.ActivityDetailsBinding
import com.example.userlists.userdetails.model.UserDetails

const val EXTRA_USER_DETAILS = "userDetails"

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private var timeOutDialog: TimeOutDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userDetails = intent.getParcelableExtra<UserDetails>(EXTRA_USER_DETAILS)!!
        binding.firstName.text = userDetails.firstName
        binding.lastName.text = userDetails.lastName
        binding.age.text = userDetails.age.toString()
        binding.gender.text = userDetails.gender
        binding.country.text = userDetails.country
    }

    override fun onDestroy() {
        super.onDestroy()
        timeOutDialog = null
    }
}