package com.kulloveth.moviesapp.favorites

import android.content.Context
import android.preference.PreferenceManager

object Favorites {




//    private fun saveFavorites(key: String, list: List<Int>, context: Context) {
//        sharedPrefs(context).edit().putString(key, json).apply()
//    }

    private fun sharedPrefs(context: Context) = PreferenceManager.getDefaultSharedPreferences(context)
}