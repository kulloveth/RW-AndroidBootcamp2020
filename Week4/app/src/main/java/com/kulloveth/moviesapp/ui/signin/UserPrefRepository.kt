package com.kulloveth.moviesapp.ui.signin

interface UserPrefRepository {

    fun saveUser(userName: String, userImage: String?)

    fun clearUser()

}