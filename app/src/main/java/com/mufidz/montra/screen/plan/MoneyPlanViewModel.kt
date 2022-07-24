package com.mufidz.montra.screen.plan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mufidz.montra.entity.MoneyPlan
import timber.log.Timber

class MoneyPlanViewModel : ViewModel() {

    private val listMoneyPlan = MutableLiveData(mutableListOf<MoneyPlan>())

    private val priceResult = MutableLiveData(0)

    private val total = MutableLiveData(0)

    val getListMoneyPlan : LiveData<MutableList<MoneyPlan>> = listMoneyPlan

    val getPriceResult : LiveData<Int> = priceResult

    val getTotal : LiveData<Int> = total

    fun addMoneyPlan(moneyPlan: MoneyPlan) {
        listMoneyPlan.value?.add(moneyPlan)
        listMoneyPlan.value = listMoneyPlan.value
    }

    fun deleteMoneyPlan(moneyPlan: MoneyPlan) {
        listMoneyPlan.value?.remove(moneyPlan)
        listMoneyPlan.value = listMoneyPlan.value
    }

    fun updateMoneyPlan(moneyPlan: MoneyPlan) {
        listMoneyPlan.value?.set(moneyPlan.id, moneyPlan)
        listMoneyPlan.value = listMoneyPlan.value
    }

    fun deleteAllPlan() {
        listMoneyPlan.value = mutableListOf()
        listMoneyPlan.value = listMoneyPlan.value
    }

    fun getResult() {
        var result  = priceResult.value ?: 0
        for (moneyPlan in listMoneyPlan.value ?: mutableListOf()) {
            result += moneyPlan.amount
        }
        priceResult.value = result
    }

    fun getTotal(price: Int, quantity: Int) {
        total.value = price * quantity
        total.value = total.value
    }

}