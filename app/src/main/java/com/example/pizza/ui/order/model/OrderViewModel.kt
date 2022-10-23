package com.example.pizza.ui.order.model

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

    fun addOrRemoveFlavor(flavor: String) {
        if(_flavors.value?.contains(flavor) == true) {
            _flavors.value!!.remove(flavor)
        } else {
            _flavors.value!!.add(flavor)
        }
    }

    fun setSize(size: Int) {
        _size.value = size
    }

    fun resetOrder() {
        _price.value = 0.0
        _flavors.value = mutableListOf<String>()
        _size.value = 0
    }
}