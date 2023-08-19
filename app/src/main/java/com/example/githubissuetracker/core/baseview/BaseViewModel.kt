package com.example.githubissuetracker.core.baseview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    protected fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }


    protected fun setLoadingAsync(isLoading: Boolean) {
        _isLoading.postValue(isLoading)
    }
}