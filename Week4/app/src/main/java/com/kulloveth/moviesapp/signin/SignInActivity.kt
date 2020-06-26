package com.kulloveth.moviesapp.signin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

import com.kulloveth.moviesapp.R
import com.kulloveth.moviesapp.databinding.ActivitySignInBinding
import com.kulloveth.moviesapp.main.MainActivity

/**
 * A simple [Fragment] subclass.
 */
class SignInActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signIn.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }



}
