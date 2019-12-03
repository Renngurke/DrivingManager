package com.example.drivingmanager.ui.reisekosten

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReisekostenViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is reisekosten Fragment"
    }
    val text: LiveData<String> = _text
}