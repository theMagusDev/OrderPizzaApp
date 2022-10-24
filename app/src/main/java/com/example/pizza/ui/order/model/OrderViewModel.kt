package com.example.pizza.ui.order.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

// Additional cost for same day pickup of an order
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

class OrderViewModel : ViewModel() {
    private var _price = MutableLiveData<Double>()
    val price: LiveData<Double> = _price

    private var _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int> = _quantity

    private var _flavors = MutableLiveData<MutableList<String>>()
    val flavors: LiveData<MutableList<String>> = _flavors

    private var _size = MutableLiveData<Int>()
    val size: LiveData<Int> = _size

    private var _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    val dateOptions = getPickupOptions()

    init {
        resetOrder()
    }

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

    fun setPickupDate(date: String) {
        _date.value = date
    }

    private fun getPickupOptions(): List<String> {
        val options = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()

        // Create a list of dates starting with the current date and the following 4 dates
        repeat(5) {
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }

        return options
    }

    private fun resetOrder() {
        _price.value = 0.0
        _quantity.value = 0
        _flavors.value = mutableListOf<String>()
        _size.value = 0
        _date.value = ""
    }
}