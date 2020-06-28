package com.kulloveth.moviesapp.ui.signin

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.kulloveth.moviesapp.R
import com.kulloveth.moviesapp.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_splash.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val animation: Animation = AlphaAnimation(1f, 0f)
        animation.duration = 1000
        animation.interpolator = LinearInterpolator()
        animation.repeatCount = Animation.INFINITE
        animation.repeatMode = Animation.REVERSE
        splash_image.startAnimation(animation)
        loadScreen()
    }


    //check if there is an esiting user and move to main screen
    fun retrieveUser() {
        val sharedPref = SignInRepository.sharedPrefs()
        if (sharedPref.contains(SignInFragment.USER_NAME_KEY)) {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
            requireActivity().finish()
        } else {
            requireView().findNavController()
                .navigate(SplashFragmentDirections.actionFirstFragmentToSecondFragment())
        }

    }

    private fun loadScreen() {
        val handler = Handler()
        handler.postDelayed({
            retrieveUser()
        }, 3000L)

    }


}
