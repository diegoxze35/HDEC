package com.android.hdec2.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.hdec2.R
import com.android.hdec2.ui.model.InputCoefficients
import com.android.hdec2.ui.theme.HDEC2Theme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MainScreen(
	modifier: Modifier = Modifier,
	inputCoefficients: InputCoefficients,
	onCoefficientAChange: (String) -> Unit,
	coefficientAError: Boolean,
	onBCoefficientChange: (String) -> Unit,
	coefficientBError: Boolean,
	onCCoefficientChange: (String) -> Unit,
	coefficientCError: Boolean
) {
	Row(
		modifier = modifier,
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.Center
	) {
		val width = LocalConfiguration.current.screenWidthDp.dp / 4
		val (a, b, c) = inputCoefficients
		OutlinedTextField(
			modifier = Modifier.width(width),
			singleLine = true,
			keyboardOptions = KeyboardOptions(
				keyboardType = KeyboardType.Number,
				autoCorrect = false,
				imeAction = ImeAction.Next
			),
			value = a,
			onValueChange = onCoefficientAChange,
			trailingIcon = {
				Text(text = stringResource(R.string.y_second))
			},
			isError = coefficientAError
		)
		Icon(imageVector = Icons.Default.Add, contentDescription = null)
		OutlinedTextField(
			modifier = Modifier.width(width),
			singleLine = true,
			keyboardOptions = KeyboardOptions(
				keyboardType = KeyboardType.Number,
				autoCorrect = false,
				imeAction = ImeAction.Next
			),
			value = b,
			onValueChange = onBCoefficientChange,
			trailingIcon = {
				Text(text = stringResource(R.string.y_first))
			},
			isError = coefficientBError
		)
		Icon(imageVector = Icons.Default.Add, contentDescription = null)
		OutlinedTextField(
			modifier = Modifier.width(width),
			singleLine = true,
			keyboardOptions = KeyboardOptions(
				keyboardType = KeyboardType.Number,
				autoCorrect = false,
				imeAction = ImeAction.Done
			),
			value = c,
			onValueChange = onCCoefficientChange,
			trailingIcon = {
				Text(text = stringResource(R.string.y_function))
			},
			isError = coefficientCError
		)
	}
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
	HDEC2Theme {
		Surface(
			modifier = Modifier.fillMaxSize(),
			color = MaterialTheme.colorScheme.background
		) {
			MainScreen(
				inputCoefficients = InputCoefficients(a = "6", b = "2", c = "3"),
				onCoefficientAChange = {},
				coefficientAError = false,
				onBCoefficientChange = {},
				coefficientBError = false,
				onCCoefficientChange = {},
				coefficientCError = false
			)
		}
	}
}