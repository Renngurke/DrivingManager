package com.example.drivingmanager.ui.statistiken

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StatistikViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is statistiken Fragment"
    }
    val text: LiveData<String> = _text
}