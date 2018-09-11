package com.example.rxbro.calck

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    // Variables for operands and types of calculation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val viewModel = ViewModelProviders.of(this).get(CalcVM::class.java)
        val viewModel = ViewModelProviders.of(this).get(BigDecimalVM::class.java)
        viewModel.stringResult.observe(this, Observer<String> { stringResult -> result.setText(stringResult)})
        viewModel.stringNewNumber.observe(this, Observer<String> { stringNumber -> newNumber.setText(stringNumber)})
        viewModel.stringOperation.observe(this, Observer<String> {stringOperation -> operation.text = stringOperation})
        val listener = View.OnClickListener { v ->
            viewModel.digitPressed((v as Button).text.toString())
        }
        button0.setOnClickListener(listener)
        button1.setOnClickListener(listener)
        button2.setOnClickListener(listener)
        button3.setOnClickListener(listener)
        button4.setOnClickListener(listener)
        button5.setOnClickListener(listener)
        button6.setOnClickListener(listener)
        button7.setOnClickListener(listener)
        button8.setOnClickListener(listener)
        button9.setOnClickListener(listener)
        buttonDot.setOnClickListener(listener)
        val opListener = View.OnClickListener { v ->
            viewModel.operandPressed((v as Button).text.toString())


        }
        buttonEqual.setOnClickListener(opListener)
        buttonMinus.setOnClickListener(opListener)
        buttonPlus.setOnClickListener(opListener)
        buttonDivide.setOnClickListener(opListener)
        buttonMultiply.setOnClickListener(opListener)
        buttonNeg.setOnClickListener {
            viewModel.negPressed()

        }
    }

}
