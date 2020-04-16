package com.test.user.tavorapplication.features.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.firebase.auth.FirebaseAuth
import com.test.user.tavorapplication.R

class LoginActivity : AppCompatActivity() {

    private var mProgressBar: ProgressBar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mProgressBar = findViewById(R.id.progressBar)
    }




    fun showProgressBar() {
        if (mProgressBar != null) mProgressBar?.visibility = View.VISIBLE
    }

     fun hideProgressBar() {
        if (mProgressBar != null) mProgressBar?.visibility = View.GONE
    }
}
