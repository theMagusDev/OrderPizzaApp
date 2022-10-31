package com.example.pizza.ui.order.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

// One pizza cost
private const val PIZZA_PRICE = 2.00

// Additional cost for same day pickup of an order
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

// Delivery cost
private const val DELIVERY_PRICE = 3.00

class OrderViewModel : ViewModel() {
    private var _total = MutableLiveData<Double>()
    val total: LiveData<String> = Transformations.map(_total) {
        NumberFormat.getCurrencyInstance().format(it)
    }

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
        updateTotal()
    }

    fun addQuantity(amount: Int) {
        _quantity.value = _quantity.value?.plus(amount)
        updateTotal()
    }

    fun setQuantity(quantity: Int) {
        _quantity.value = quantity
        updateTotal()
    }

    fun setSize(size: Int) {
        _size.value = size
        updateTotal()
    }

    fun setPickupDate(date: String) {
        _date.value = date
        updateTotal()
    }

    fun getFlavorsString(): String {
        if (!_flavors.value.isNullOrEmpty())
            return _flavors.value!!.joinToString(", ")
        else
            return "No"
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

    private fun updateTotal() {
        var calculatedTotal = (_quantity.value ?: 0) * PIZZA_PRICE

        // Price update based on flavors selected
        if(!_flavors.value.isNullOrEmpty()) {
            for (flavor in _flavors.value!!) {
                when (flavor) {
                    "Bacon" -> calculatedTotal += 2.00
                    "Sausage" -> calculatedTotal += 2.50
                    "Extra Cheese" -> calculatedTotal += 2.75
                    "Mushrooms" -> calculatedTotal += 3.00
                    "Onions" -> calculatedTotal += 2.50
                    else -> {}
                }
            }
        }

        // Price update based on size selected
        calculatedTotal *= when(_size.value) {
            45 -> 1.80
            30 -> 1.10
            else -> 1.0
        }

        // Update the price if same day pickup selected
        if (date.value == dateOptions[0])
            calculatedTotal += PRICE_FOR_SAME_DAY_PICKUP
        
        _total.value = calculatedTotal
    }

    fun hasNoPickupDateSelected(): Boolean {
        return _date.value.isNullOrEmpty()
    }

    fun resetOrder() {
        _total.value = 0.0
        _quantity.value = 0
        _flavors.value = mutableListOf<String>()
        _size.value = 0
        _date.value = ""
    }
}