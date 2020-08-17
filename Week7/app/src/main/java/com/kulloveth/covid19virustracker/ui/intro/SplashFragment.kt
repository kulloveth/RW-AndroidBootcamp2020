package com.kulloveth.covid19virustracker.ui.intro

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.kulloveth.covid19virustracker.R
import com.kulloveth.covid19virustracker.data.PreferenceRepository
import com.kulloveth.covid19virustracker.ui.MainActivity
import com.kulloveth.covid19virustracker.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_splash.*

/**
 * A simple [Fragment] subclass.
 */
class SplashFragment : BaseFragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.app_name_animation)
        app_nam.startAnimation(animation)
    }

    private val handler = Handler()

    private val runnable = Runnable {
        checkOnBoarding()
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 5000)
    }

    override fun getLayoutId() =
        R.layout.fragment_splash

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    fun checkOnBoarding() {
        val sharedPref = PreferenceRepository.sharedPref
        if (sharedPref.contains(PreferenceRepository.ONBOARDING_KEY)) {
            requireActivity().finish()
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        } else {
            requireView().findNavController()
                .navigate(R.id.action_splashFragment_to_onboardingFragment)
        }
    }

}
