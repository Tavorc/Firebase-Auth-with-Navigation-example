package com.test.user.tavorapplication.features.login.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

import com.test.user.tavorapplication.R
import com.test.user.tavorapplication.infrastructure.view.BaseFragment
import kotlinx.android.synthetic.main.fragment_confirmation.*

/**
 * A simple [Fragment] subclass.
 */
class ConfirmationFragment : BaseFragment() {

    private lateinit var mUserNameTextView : TextView
    private lateinit var mLogout : Button
    private lateinit var mNavOptions : NavOptions
    private lateinit var mNavController : NavController
    private lateinit var mAuth: FirebaseAuth

    override fun getLayout(): Int {
        return R.layout.fragment_confirmation
    }

    override fun initView(view: ViewGroup) {
        val name = arguments?.getString("userName")
        mUserNameTextView = view.findViewById(R.id.userNameTextView)
        mUserNameTextView.text = name
        mLogout = view.findViewById(R.id.logout)
        mAuth = FirebaseAuth.getInstance()
        mNavOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopUpTo(R.id.signupFragment,true)
            .build()
        setListener()
    }

    private fun setListener() {
        mLogout.setOnClickListener {
            mAuth.signOut()
            navigateToLoginFragment()
        }
    }

    private fun navigateToLoginFragment() {
        mNavController = view?.findNavController()!!
        mNavController.navigate(R.id.loginFragment, null, mNavOptions)
    }

}
