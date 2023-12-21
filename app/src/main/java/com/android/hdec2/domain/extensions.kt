package com.android.hdec2.domain

import com.android.hdec2.ui.model.InputCoefficients

fun InputCoefficients.toDomain(): NumericCoefficients = NumericCoefficients(
	a = a.toDouble(),
	b = b.toDouble(),
	c = c.toDouble()
)