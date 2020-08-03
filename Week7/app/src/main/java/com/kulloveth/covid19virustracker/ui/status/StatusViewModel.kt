package com.kulloveth.covid19virustracker.ui.status

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kulloveth.covid19virustracker.data.Repository
import com.kulloveth.covid19virustracker.data.db.StatusEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StatusViewModel(private val repository: Repository) : ViewModel() {


    val sstatusLiveData = MutableLiveData<StatusEntity>()
    private val newStatusLiveData = MutableLiveData<List<StatusEntity>>()

    init {
        repository.fetchStatus()
    }

    fun getNewStatus(): LiveData<List<StatusEntity>> {
        viewModelScope.launch(Dispatchers.IO) {
            val status = repository.fetchStatusFromRoom()
            newStatusLiveData.postValue(status)
        }
        return newStatusLiveData
    }

    //setup status to pass between fragments
    fun setUpStatus(statusEntity: StatusEntity) {
        sstatusLiveData.value = statusEntity
    }


}