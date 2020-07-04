package com.kulloveth.moviesapp.ui.signin

import androidx.preference.PreferenceManager
import com.kulloveth.moviesapp.MovieApplication

object SignInRepository : UserPrefRepository {
    val context = MovieApplication.getContext()
    fun sharedPrefs() = PreferenceManager.getDefaultSharedPreferences(
        context
    )

    override fun saveUser(userName: String, userImage: String?) {
        sharedPrefs().edit().putString(SignInFragment.USER_NAME_KEY, userName).apply()
        sharedPrefs().edit().putString(SignInFragment.USER_IMAGE_KEY, userImage).apply()
    }


    override fun clearUser() {

        sharedPrefs().edit().clear().apply()
    }


}