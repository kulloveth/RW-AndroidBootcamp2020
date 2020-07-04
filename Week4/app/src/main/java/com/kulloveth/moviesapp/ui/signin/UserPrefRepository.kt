package com.kulloveth.moviesapp.ui.signin

import android.app.Activity

interface UserPrefRepository {

    fun saveUser(userName:String,userImage:String?)

    fun clearUser()

}