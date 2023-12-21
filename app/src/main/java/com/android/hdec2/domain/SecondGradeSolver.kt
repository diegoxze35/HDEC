package com.android.hdec2.domain

class SecondGradeSolver(val x1: Double, val x2: Double): HDESecondGradeSolver {
	override fun resolve(): String {
		return "C₁e^(${x1}x) + C₂e^(${x2}x)"
	}
}