package com.example.drivingmanager.ui.kilometerstand

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class KmViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Kilometerstand Fragment"
    }
    val text: LiveData<String> = _text
}