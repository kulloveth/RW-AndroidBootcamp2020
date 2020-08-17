package com.kulloveth.covid19virustracker.data

import android.preference.PreferenceManager
import com.kulloveth.covid19virustracker.App

object PreferenceRepository {
const val ONBOARDING_KEY = "onboard"
    val context  = App.getContext()
    val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)

    fun saveOnboarding(once:Boolean){
     sharedPref.edit().putBoolean(ONBOARDING_KEY,once).apply()
    }


}
