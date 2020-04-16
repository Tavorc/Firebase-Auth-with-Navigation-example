package com.test.user.tavorapplication.features.login.fragments

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.test.user.tavorapplication.R
import com.test.user.tavorapplication.features.login.LoginActivity
import com.test.user.tavorapplication.features.login.viewModel.LoginViewModel
import com.test.user.tavorapplication.features.login.viewModel.LoginViewState
import com.test.user.tavorapplication.infrastructure.view.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*


/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : BaseFragment(), View.OnClickListener {

    private val RC_SIGN_IN: Int =1
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private lateinit var mGoToRegisterButton : Button
    private lateinit var mNavOptions : NavOptions
    private lateinit var mNavController : NavController
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mConnectButton : Button
    private lateinit var mSignInButton : SignInButton
    private lateinit var mLoginViewModel : LoginViewModel

    override fun getLayout(): Int {
        return R.layout.fragment_login
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        if(currentUser!=null){
            navigateToConfirmationFragment(currentUser.email.toString())
        }
    }

    override fun initView(view: ViewGroup) {
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(activity!!, gso);
        mGoToRegisterButton = view.findViewById(R.id.goToRegisterButton)
        mSignInButton = view.findViewById(R.id.sign_in_button)
        mLoginViewModel = LoginViewModel()
        mConnectButton = view.findViewById(R.id.connectButton)
        mConnectButton.setOnClickListener(this)
        mSignInButton.setOnClickListener(this)
        mGoToRegisterButton.setOnClickListener(this)
        mAuth = FirebaseAuth.getInstance()
        setNavOptions()
    }

    private fun connectWithEmailAndPassword(email : String, password: String){

        mLoginViewModel.connectWithEmailAndPassword(email, password).observe(this, Observer<LoginViewState>{

            if (it is LoginViewState.Success) {
                ( activity as LoginActivity).hideProgressBar()
                val result = it.response.result
                val emailVerified = result?.user?.isEmailVerified
                navigateToConfirmationFragment(result?.user?.email.toString())
            }

            if(it is LoginViewState.Error){
                ( activity as LoginActivity).hideProgressBar()
            }

            if(it is LoginViewState.Loading){
                ( activity as LoginActivity).showProgressBar()
            }
        })
    }

    private fun navigateToConfirmationFragment(email: String) {
        var bundle = bundleOf("userName" to email)
        mNavController = view?.findNavController()!!
        mNavController.navigate(R.id.confirmationFragment, bundle, mNavOptions)
    }

    private fun checkValidation(email:String, password: String) : Boolean{
        if(email.isNotEmpty() && password.isNotEmpty() && password.length>5){
            return true
        }
        return false
    }
    private fun setNavOptions(){
        mNavOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setExitAnim(R.anim.slide_in_right)
            .setPopExitAnim(R.anim.slide_out_right)
            .setPopUpTo(R.id.loginFragment,true)
            .build()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.sign_in_button -> signIn()
            R.id.connectButton ->{
                val email= emailEditText.text.toString()
                val password=passwordEditText.text.toString()
                if(checkValidation(email, password)){
                    connectWithEmailAndPassword(email, password)
                }
            }
            R.id.goToRegisterButton -> {
                mNavController = view?.findNavController()!!
                mNavController.navigate(R.id.signupFragment, null, mNavOptions)
            }
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account =
                completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.

            navigateToConfirmationFragment(account?.email.toString())
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
        }
    }
}
