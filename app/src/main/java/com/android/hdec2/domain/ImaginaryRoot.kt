package com.android.hdec2.domain

class ImaginaryRoot(val alpha: Double, val beta: Double, val discriminating: Double) : HDESecondGradeSolver {
	
	override fun resolve(): String {
		return "e^($alpha)x (C₁ × Cos(${beta}x) + C₂ × Sen(${beta}x)"
	}
}