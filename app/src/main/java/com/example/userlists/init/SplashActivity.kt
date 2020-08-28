package com.example.userlists.init

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.userlists.usersList.UsersListActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!isTaskRoot
                && intent.hasCategory(Intent.CATEGORY_LAUNCHER)
                && intent.action != null && intent.action == Intent.ACTION_MAIN) {
            finish()
            return
        }

        startActivity(Intent(this, UsersListActivity::class.java))
        finish()
    }
}