package com.kulloveth.covid19virustracker.ui

import androidx.lifecycle.ViewModel
import com.kulloveth.covid19virustracker.data.Repository

abstract class BaseViewModel(private val repository: Repository) :ViewModel()
