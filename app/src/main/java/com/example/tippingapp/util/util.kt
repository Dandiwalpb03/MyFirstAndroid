package com.example.tippingapp.util

fun calculatingTotalTip(totalBill: Double, tipPercentage: Int): Double{
    return if(totalBill > 1 && totalBill.toString().isNotEmpty())
        (totalBill * tipPercentage)/100 else 0.0
}

fun calculateTotalPerPerson(
    totalBill: Double,
    splitBy: Int,
    tipPercentage: Int): Double {
    return ((calculatingTotalTip(totalBill,tipPercentage)+totalBill)/splitBy)
}