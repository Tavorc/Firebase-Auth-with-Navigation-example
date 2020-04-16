package com.test.user.tavorapplication.features.login.fragments

import android.content.ContentValues.TAG
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

import com.test.user.tavorapplication.R
import com.test.user.tavorapplication.infrastructure.view.BaseFragment
import kotlinx.android.synthetic.main.fragment_signup.*

/**
 * A simple [Fragment] subclass.
 */
class SignupFragment : BaseFragment() {

    private lateinit var mConnectButton: Button
    private lateinit var mNavOptions : NavOptions
    private lateinit var mNavController : NavController
    private lateinit var mAuth: FirebaseAuth

    override fun getLayout(): Int {
       return R.layout.fragment_signup
    }

    override fun initView(view: ViewGroup) {
        mConnectButton = view.findViewById(R.id.connectButton)
        mAuth = FirebaseAuth.getInstance()
        mNavOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setExitAnim(R.anim.slide_in_right)
            .setPopExitAnim(R.anim.slide_out_right)
            .setPopUpTo(R.id.signupFragment,true)
            .build()

        setListener()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser

    }

    fun setListener(){
        mConnectButton.setOnClickListener {
           val email= emailEditText.text.toString()
           val password=passwordEditText.text.toString()
            if(checkValidation(email, password)){
                createUserWithEmail(email, password)
            }
        }
    }

    private fun createUserWithEmail(email : String, password: String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity!!) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = mAuth.currentUser
                    navigateToConfirmationFragment(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(activity, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }

            }
    }

    fun checkValidation(email:String, password: String) : Boolean{
        if(email.isNotEmpty() && password.isNotEmpty() && password.length>5){
            return true
        }
       return false
    }


    fun navigateToConfirmationFragment(user: FirebaseUser?) {
        var bundle = bundleOf("userName" to user?.email)
        mNavController = view?.findNavController()!!
        mNavController.navigate(R.id.confirmationFragment, bundle, mNavOptions)
    }

}
