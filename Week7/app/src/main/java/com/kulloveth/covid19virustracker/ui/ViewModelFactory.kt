package com.kulloveth.covid19virustracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kulloveth.covid19virustracker.data.Repository
import com.kulloveth.covid19virustracker.ui.status.StatusViewModel
import java.lang.RuntimeException

class ViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (!BaseViewModel::class.java.isAssignableFrom(modelClass)) {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
        try {
            return modelClass.getConstructor(Repository::class.java).newInstance(repository)
        }catch (e:Exception){
            throw RuntimeException()
        }


    }
}