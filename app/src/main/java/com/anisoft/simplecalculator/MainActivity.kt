package com.anisoft.simplecalculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.anisoft.simplecalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val engine = CalculatorEngine()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupButtonListeners()
        updateDisplays()
    }

    private fun setupButtonListeners() {
        val digitButtons = mapOf(
            binding.btn0 to '0',
            binding.btn1 to '1',
            binding.btn2 to '2',
            binding.btn3 to '3',
            binding.btn4 to '4',
            binding.btn5 to '5',
            binding.btn6 to '6',
            binding.btn7 to '7',
            binding.btn8 to '8',
            binding.btn9 to '9'
        )

        digitButtons.forEach { (button, digit) ->
            button.setOnClickListener {
                engine.appendDigit(digit)
                updateDisplays()
            }
        }

        binding.btnDot.setOnClickListener {
            engine.appendDecimal()
            updateDisplays()
        }

        val operatorButtons = mapOf(
            binding.btnAdd to '+',
            binding.btnSubtract to '-',
            binding.btnMultiply to '×',
            binding.btnDivide to '÷'
        )

        operatorButtons.forEach { (button, operator) ->
            button.setOnClickListener {
                engine.setOperator(operator)
                updateDisplays()
            }
        }

        binding.btnEquals.setOnClickListener {
            engine.calculateResult()
            updateDisplays()
        }

        binding.btnClear.setOnClickListener {
            engine.clear()
            updateDisplays()
        }

        binding.btnBack.setOnClickListener {
            engine.backspace()
            updateDisplays()
        }

        binding.btnPercent.setOnClickListener {
            engine.applyPercent()
            updateDisplays()
        }
    }

    private fun updateDisplays() {
        binding.tvExpression.text = engine.getExpressionDisplay()
        binding.tvResult.text = engine.getCurrentDisplay()
    }
}