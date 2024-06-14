package com.example.aboolean

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class data:ViewModel(){
    val expression: MutableLiveData<String> by lazy {
        MutableLiveData<String>()

    }
}