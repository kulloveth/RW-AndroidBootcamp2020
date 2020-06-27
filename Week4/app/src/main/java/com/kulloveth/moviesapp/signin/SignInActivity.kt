package com.kulloveth.moviesapp.signin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.google.android.material.textfield.TextInputEditText
import com.kulloveth.moviesapp.databinding.ActivitySignInBinding
import com.kulloveth.moviesapp.main.MainActivity
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
            userPassword?.setError("password must contain upper and lower case letters, numbers, should not contain space,special characters and should not beless thean 8 ")
        } else {
            saveUser(userName.toString(), password.toString())
            startActivity(Intent(this, MainActivity::class.java))
        }


    }


    override fun onResume() {
        super.onResume()
        // retrieveUser()
    }


    fun isValidPassword(password: String): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$"
        pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(password)
        return matcher.matches()
    }


    fun saveUser(userName: String, userPassWord: String) {
        val sharedPrefs = sharedPref(this).edit()
        sharedPrefs.putString(USER_NAME_KEY, userName)
        sharedPrefs.putString(USER_PASS_KEY, userPassWord)
        sharedPrefs.apply()
    }


    fun retrieveUser() {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        if (sharedPrefs.contains(USER_NAME_KEY) && sharedPrefs.contains(USER_PASS_KEY)) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }


    companion object {
        const val USER_NAME_KEY = "USER_NAME_KEY"
        const val USER_PASS_KEY = "USER_PASS_KEY"

        fun sharedPref(context: Context) = PreferenceManager.getDefaultSharedPreferences(context)
    }




}
