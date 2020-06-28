package com.kulloveth.moviesapp.ui.signin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.kulloveth.moviesapp.MovieApplication
import com.kulloveth.moviesapp.databinding.ActivitySignInBinding
import com.kulloveth.moviesapp.ui.main.MainActivity
import java.util.regex.Matcher
import java.util.regex.Pattern


/**
 * A simple [Fragment] subclass.
 */
class SignInActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignInBinding
    var userEditText: TextInputEditText? = null
    var userPassword: TextInputEditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //retrieveUser()
        userEditText = binding.userNameEditText
        userPassword = binding.passwordEditText

        binding.signInBtn.setOnClickListener {
            validateUser()
        }


    }


    private fun validateUser() {
        val userName = userEditText?.text?.trim()
        val password = userPassword?.text?.trim()
        if (userName.isNullOrEmpty()) {
            userEditText?.setError("Username cannot be empty")
        } else if (password.isNullOrEmpty()) {
            userPassword?.setError("Password cannot be empty")
        } else if (userName.length < 5) {
            userEditText?.setError("username cannot be less than 5")
        } else if (!isValidPassword(password.toString())) {
            userPassword?.setError("password must contain upper and lower case letters, numbers and should not be less thean 8 and should not contain space, ")
        } else {
            saveUser(userName.toString(), password.toString())
            startActivity(Intent(this, MainActivity::class.java))
        }


    }


    override fun onStart() {
        super.onStart()
        //retrieveUser()
    }


    fun isValidPassword(password: String): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}$"
        pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(password)

        return matcher.matches()
    }


    /**
     * save [userName]  and [userPassword]
     * using [sharedPrefs]
     * */
    fun saveUser(userName: String, userPassWord: String) {
        SignInRepository.saveUser(userName)
    }


    fun retrieveUser() {
        val sharedPref = getSharedPreferences(SignInRepository.SIGNIN_PREFS_REPOSIORY,
            Context.MODE_PRIVATE
        )
            if (!sharedPref.getString(USER_NAME_KEY,"").equals("") ) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
               startActivity(Intent(this, SignInActivity::class.java))
            }

    }


    companion object {
        const val USER_NAME_KEY = "USER_NAME_KEY"
        const val USER_PASS_KEY = "USER_PASS_KEY"


    }


}
