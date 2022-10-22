package com.example.pizza.ui.order

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrderViewModel : ViewModel() {
    private var _price = MutableLiveData<Double>()
    val price: LiveData<Double> = _price

    private var _flavors = MutableLiveData<MutableList<String>>()
    val flavors: LiveData<MutableList<String>> = _flavors

    private var _size = MutableLiveData<Int>()
    val size: LiveData<Int> = _size

    private var _size_text = MutableLiveData<String>()
    val size_text: LiveData<String> = _size_text

    fun addOrRemoveFlavor(flavor: String) {
        Log.d("OrderViewModel", _flavors.value.toString().plus(" <-- _flavors var;").plus(flavor).plus(" <-- flavor var."))
        if(_flavors.value?.contains(flavor) == true) {
            _flavors.value!!.remove(flavor)
        } else {
            _flavors.value!!.add(flavor)
        }
    }

    fun setSize(size: Int) {
        _size.value = size
        _size_text.value = "Pizza's size: ".plus(_size)
    }

    fun resetOrder() {
        _price.value = 0.0
        _flavors.value = mutableListOf<String>()
        _size.value = 0
        _size_text.value = ""
    }
}