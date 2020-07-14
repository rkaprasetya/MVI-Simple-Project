package com.raka.mviminiproject.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.raka.mviminiproject.data.api.ApiHelper
import com.raka.mviminiproject.data.repository.MainRepository
import com.raka.mviminiproject.ui.main.viewmodel.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class ViewModelFactory(private val apiHelper: ApiHelper):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(MainRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("unknown class name")
    }
}