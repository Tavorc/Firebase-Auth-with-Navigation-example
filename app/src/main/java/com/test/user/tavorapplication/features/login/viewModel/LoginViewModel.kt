package com.test.user.tavorapplication.features.login.viewModel

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.test.user.tavorapplication.features.login.LoginActivity
import com.test.user.tavorapplication.infrastructure.viewModel.BaseViewModel

 class LoginViewModel  : BaseViewModel() {
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

     fun connectWithEmailAndPassword(email : String, password: String): MutableLiveData<LoginViewState> {

        val liveData = MutableLiveData<LoginViewState>()
        liveData.postValue(LoginViewState.Loading(true))
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    liveData.postValue(LoginViewState.Success(task))
                } else {
                    liveData.postValue(LoginViewState.Error(task.exception?.message.toString()))
                }
            }

         return liveData
    }
}