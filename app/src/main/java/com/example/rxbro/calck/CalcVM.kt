package com.example.rxbro.calck

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel

class CalcVM : ViewModel() {
    private var operand1: Double? = null
    private var pendingOperation = "="
    private val result = MutableLiveData<Double>()
    val stringResult : LiveData<String>
        get() = Transformations.map(result) { it.toString()}
    private val newNumber = MutableLiveData<String>()
    val stringNewNumber: LiveData<String>
        get() = newNumber
    private val operation = MutableLiveData<String>()
    val stringOperation: LiveData<String>
        get() = operation

    fun digitPressed(caption: String) {
        if (newNumber.value != null) {
            newNumber.value = newNumber.value + caption
        } else {
            newNumber.value = caption
        }
    }
    fun operandPressed(op: String) {
        try {
            val value = newNumber.value?.toDouble()
            if (value != null) {
                performOperation(value, op)
            }

        } catch (e: NumberFormatException) {
            newNumber.value = ""
        }
        pendingOperation = op
        operation.value = pendingOperation
    }
    fun negPressed() {
        val value = newNumber.value.toString()
        if (value.isEmpty()) {
            newNumber.value = "-"
        } else {
            try {
                var doubleValue = value.toDouble()
                doubleValue *= -1
                newNumber.value = doubleValue.toString()
            } catch (e: NumberFormatException) {
                // newNumber was "-" or ".", so clear it
                newNumber.value = ""
            }
        }
    }

    private fun performOperation(value: Double, operation: String) {

        if (operand1 == null) {
            operand1 = value
        } else {

            if (pendingOperation == "=") { // string comparison in Kotlin - must be "===" in Java
                pendingOperation = operation
            }
            when (pendingOperation) { // similar to case-switch statement in Java
                "=" -> operand1 = value
                "/" -> operand1 = if (value == 0.0) { // inline if statement
                    Double.NaN // handles divide by zero
                } else {
                    operand1!! / value // !! = returns a non-nullable value of operand1
                }
                "*" -> operand1 = operand1!! * value
                "-" -> operand1 = operand1!! - value
                "+" -> operand1 = operand1!! + value
            }
        }
        result.value = operand1
        newNumber.value = ""
    }

}