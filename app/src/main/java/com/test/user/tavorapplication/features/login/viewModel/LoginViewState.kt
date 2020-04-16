package com.test.user.tavorapplication.features.login.viewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

sealed class LoginViewState {
    class Error(var message: String) : LoginViewState()

    class Success(var response: Task<AuthResult>) : LoginViewState()

    class Loading(var isLoading: Boolean) : LoginViewState()
}