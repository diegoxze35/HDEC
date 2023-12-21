package com.android.hdec2.ui.viewmodel

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.hdec2.R
import com.android.hdec2.domain.ImaginaryRoot
import com.android.hdec2.domain.NumericCoefficients
import com.android.hdec2.domain.HDESecondGradeSolver
import com.android.hdec2.domain.RepeatRoot
import com.android.hdec2.domain.SecondGradeSolver
import com.android.hdec2.ui.model.CoefficientLetter
import com.android.hdec2.ui.model.InputCoefficients
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.sqrt

class HDECViewModel : ViewModel() {
	var coefficients by mutableStateOf(
		InputCoefficients(a = String(), b = String(), c = String())
	)
	val steps = mutableStateListOf<Pair<@StringRes Int, String>>()
	private lateinit var currentResult: HDESecondGradeSolver
	
	private val allInvalidCoefficients = mutableStateMapOf(
		CoefficientLetter.A to true,
		CoefficientLetter.B to true,
		CoefficientLetter.C to true,
	)
	
	fun isCoefficientError(
		coefficient: CoefficientLetter,
		coefficientValue: String,
		extra: (coefficient: Double) -> Boolean = { false }
	) = (coefficientValue.toDoubleOrNull()
		?.let { extra(it) } ?: true).also {
		allInvalidCoefficients[coefficient] = it
	}
	
	val canCalculate: Boolean
		get() = allInvalidCoefficients.values.all { !it }
	
	fun calculateRoots(numericCoefficients: NumericCoefficients) {
		steps.clear()
		viewModelScope.launch(context = Dispatchers.Default) {
			val (a, b, c) = numericCoefficients
			val bString = when {
				b == 0.0 -> ""
				b > 0 -> " + ${b}x"
				else -> " - ${-b}x"
			}
			val cString = when {
				c == 0.0 -> ""
				c > 0 -> " + $c"
				else -> " - ${-c}"
			}
			steps.add(R.string.characteristic_polynomial to "${a}x²${bString}${cString}")
			val discriminating = (b * b) - (4 * a * c)
			currentResult = if (discriminating < 0) {
				val alpha = -b / (2 * a)
				val beta = sqrt(-discriminating) / (2 * a)
				steps.add(R.string.imaginary_roots to (if (alpha > 0.0) "$alpha" else "0") +
						" ± (√(${-discriminating})i) / ${2 * a}\n" +
						"${if (alpha > 0.0) "$alpha" else "0"} ± ${beta}i")
				ImaginaryRoot(alpha = alpha, beta = beta, discriminating = -discriminating)
			} else if (discriminating == 0.0) {
				val root = -b / (2 * a)
				steps.add(R.string.repeat_root to "x₁,₂ = $root")
				RepeatRoot(root = root)
			} else {
				val x1 = (-b + sqrt(discriminating)) / 2 * a
				val x2 = (-b - sqrt(discriminating)) / 2 * a
				steps.add(
					R.string.roots to "x₁ = (${-b} + √($b² - 4×$a×$c)) / ${2 * a}" +
							"\nx₂ = (${-b} - √($b² - 4×$a×$c)) / ${2 * a}\n" +
							"x₁ = ${x1}\nx₂ = $x2"
				)
				SecondGradeSolver(x1 = x1, x2 = x2)
			}
			steps.add(R.string.Solution to currentResult.resolve())
		}
	}
}