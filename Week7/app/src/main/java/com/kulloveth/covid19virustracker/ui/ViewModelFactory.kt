package com.kulloveth.covid19virustracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kulloveth.covid19virustracker.data.Repository
import com.kulloveth.covid19virustracker.ui.base.BaseViewModel

class ViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (!BaseViewModel::class.java.isAssignableFrom(modelClass)) {
            throw IllegalArgumentException("ViewModel must extend BaseViewModel")
        }
        try {
            return modelClass.getConstructor().newInstance()
        }catch (e:Exception){
            throw RuntimeException()
        }
    }
}