package com.example.mainviewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    val tv by lazy { findViewById<TextView>(R.id.tv) }
    val btn by lazy {findViewById<Button>(R.id.btn)}
    val editT by lazy { findViewById<EditText>(R.id.editT) }
    val progressBar by lazy {findViewById<ProgressBar>(R.id.progress_Bar)}

    var condition: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        condition = viewModel.stateFlow2.value
        changeCondition()

        btn.isEnabled = false

        editT.addTextChangedListener {
            openButton()
            condition = viewModel.stateFlow2.value
            changeCondition()
        }

        lifecycleScope.launch {
            viewModel.stateFlow.collect { it: Int -> progressBar.setProgress(it) }
        }
        lifecycleScope.launch {
            viewModel.stateFlow2.collect { it: Int -> condition = it
                changeCondition()}
        }

        btn.setOnClickListener {
            viewModel.countingDown()
            changeCondition()
        }
    }

    fun openButton() {
        if (editT.text.length > 3) {
            btn.isEnabled = true
        } else {btn.isEnabled = false}

    }
    fun changeCondition() {
        when (condition) {
            1 -> {tv.isVisible = true
                tv.setText("Здесь будет отображаться результат запроса")
                progressBar.isVisible = false}
            2 -> {tv.isVisible = false
                progressBar.isVisible = true}
            3 -> {tv.isVisible = true
                val select = editT.text
                tv.setText("По запросу \"$select\" ничего не найдено")
                progressBar.isVisible = false}

        }

    }
}