package com.example.drivingmanager.ui.tankrechnung

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TankrechnungViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is tankrechnung Fragment"
    }
    val text: LiveData<String> = _text
}