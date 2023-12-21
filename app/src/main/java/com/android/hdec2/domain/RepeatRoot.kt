package com.android.hdec2.domain

class RepeatRoot(val root: Double) : HDESecondGradeSolver {
	override fun resolve(): String {
		return "C₁e^(${root}x) + C₂xe^(${root}x)"
	}
}