package com.anisoft.simplecalculator

import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

class CalculatorEngine {

    private var firstOperand: BigDecimal? = null
    private var secondOperand: BigDecimal? = null
    private var currentOperator: Char? = null
    private var currentInput: String = "0"
    private var isNewInput: Boolean = true
    private var justCalculated: Boolean = false

    private val mathContext = MathContext(100, RoundingMode.HALF_UP)
    private val maxLength = 100

    companion object {
        const val ERROR_DIVIDE_BY_ZERO = "Cannot divide by zero"
        const val ERROR = "Error"
    }

    fun appendDigit(digit: Char): String {
        if (justCalculated) {
            clear()
            justCalculated = false
        }
        if (isNewInput || currentInput == "0") {
            currentInput = digit.toString()
            isNewInput = false
        } else if (currentInput.length < maxLength) {
            currentInput += digit
        }
        return currentInput
    }

    fun appendDecimal(): String {
        if (justCalculated) {
            clear()
            justCalculated = false
        }
        if (isNewInput) {
            currentInput = "0."
            isNewInput = false
        } else if (!currentInput.contains('.')) {
            if (currentInput.length < maxLength) {
                currentInput += "."
            }
        }
        return currentInput
    }

    fun setOperator(operator: Char): String {
        if (justCalculated) {
            justCalculated = false
        }
        if (currentOperator != null && !isNewInput) {
            calculateResult()
        }
        if (firstOperand == null) {
            firstOperand = parseCurrentInput()
        }
        currentOperator = operator
        isNewInput = true
        return getExpressionDisplay()
    }

    fun calculateResult(): String {
        if (currentOperator == null || firstOperand == null) {
            return currentInput
        }
        secondOperand = parseCurrentInput()
        val result = performCalculation()
        if (result == null) {
            currentInput = ERROR_DIVIDE_BY_ZERO
            firstOperand = null
            secondOperand = null
            currentOperator = null
            isNewInput = true
            justCalculated = true
            return currentInput
        }
        currentInput = formatOutput(result)
        firstOperand = result
        secondOperand = null
        currentOperator = null
        isNewInput = true
        justCalculated = true
        return currentInput
    }

    fun applyPercent(): String {
        if (justCalculated) {
            return currentInput
        }
        val value = parseCurrentInput()
        val percentValue = value.divide(BigDecimal.valueOf(100), mathContext)
        currentInput = formatOutput(percentValue)
        isNewInput = true
        return currentInput
    }

    fun backspace(): String {
        if (justCalculated || isNewInput) {
            return currentInput
        }
        if (currentInput.length <= 1 || (currentInput.length == 2 && currentInput.startsWith("-"))) {
            currentInput = "0"
            isNewInput = true
        } else {
            currentInput = currentInput.dropLast(1)
        }
        return currentInput
    }

    fun clear(): String {
        firstOperand = null
        secondOperand = null
        currentOperator = null
        currentInput = "0"
        isNewInput = true
        justCalculated = false
        return currentInput
    }

    fun getExpressionDisplay(): String {
        val operatorSymbol = when (currentOperator) {
            '+' -> " + "
            '-' -> " − "
            '×' -> " × "
            '÷' -> " ÷ "
            else -> ""
        }
        return if (firstOperand != null) {
            "${formatOutput(firstOperand!!)}$operatorSymbol"
        } else {
            ""
        }
    }

    fun getCurrentDisplay(): String = currentInput

    private fun parseCurrentInput(): BigDecimal {
        return try {
            BigDecimal(currentInput, mathContext)
        } catch (e: Exception) {
            BigDecimal.ZERO
        }
    }

    private fun performCalculation(): BigDecimal? {
        val a = firstOperand!!
        val b = secondOperand!!
        return try {
            when (currentOperator) {
                '+' -> a.add(b, mathContext)
                '-' -> a.subtract(b, mathContext)
                '×' -> a.multiply(b, mathContext)
                '÷' -> {
                    if (b == BigDecimal.ZERO) {
                        return null
                    }
                    a.divide(b, mathContext)
                }
                else -> a
            }
        } catch (e: ArithmeticException) {
            null
        }
    }

    private fun formatOutput(value: BigDecimal): String {
        val stripped = value.stripTrailingZeros()
        val plain = stripped.toPlainString()
        return if (plain.length > maxLength) {
            stripped.toEngineeringString()
        } else {
            plain
        }
    }
}