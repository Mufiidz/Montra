package com.mufidz.montra.screen.discount

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DiscountViewModel : ViewModel() {

    private val listDiscount = MutableLiveData(mutableListOf<Int>())

    private val priceResult = MutableLiveData(0)

    val getListDiscount : LiveData<MutableList<Int>> = listDiscount

    val getPriceResult : LiveData<Int> = priceResult

    fun addDiscount(discount: Int) {
        listDiscount.value?.add(discount)
        listDiscount.value = listDiscount.value
    }

    fun deleteAllDiscount() {
        listDiscount.value = mutableListOf()
    }

    fun getResult(price: Int) {
        var priceResult = price
        var priceDiscounted = priceResult
        for (discount in listDiscount.value ?: mutableListOf()) {
            priceDiscounted = discount * priceDiscounted / 100
            priceResult -= priceDiscounted
        }
        this.priceResult.value = priceResult
    }
}