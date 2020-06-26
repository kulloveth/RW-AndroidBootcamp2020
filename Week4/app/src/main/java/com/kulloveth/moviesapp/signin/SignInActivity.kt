package com.kulloveth.moviesapp.signin

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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
        userEditText = binding.userNameEditText
        userEditText?.setHintTextColor(Color.GRAY)
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
            userPassword?.setError("password must contain upper and lower case letters, numbers, special characters and should not beless thean 8 ")
        } else {
            startActivity(Intent(this, MainActivity::class.java))
        }


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


}
