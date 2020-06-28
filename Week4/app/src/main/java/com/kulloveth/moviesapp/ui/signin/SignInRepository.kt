package com.kulloveth.moviesapp.ui.signin

import android.content.Context
import com.kulloveth.moviesapp.MovieApplication

object SignInRepository : UserPrefRepository {
    const val SIGNIN_PREFS_REPOSIORY = "SIGNIN_PREFS_REPOSIORY"
    val context = MovieApplication.getContext()
    private fun sharedPrefs() = context.getSharedPreferences(
        SIGNIN_PREFS_REPOSIORY, Context.MODE_PRIVATE
    )

    override fun saveUser(userName: String) {
        sharedPrefs().edit().putString(SignInActivity.USER_NAME_KEY, userName).apply()
    }


    override fun clearUser() {
        val sharedPref = MovieApplication.getContext().getSharedPreferences(
            SIGNIN_PREFS_REPOSIORY,
            Context.MODE_PRIVATE
        )
        sharedPref.edit().clear().apply()
    }


}