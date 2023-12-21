package com.android.hdec2.domain

sealed interface HDESecondGradeSolver {
	fun resolve(): String
}