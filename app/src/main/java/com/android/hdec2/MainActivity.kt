package com.android.hdec2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.android.hdec2.domain.toDomain
import com.android.hdec2.ui.model.CoefficientLetter
import com.android.hdec2.ui.screen.MainScreen
import com.android.hdec2.ui.theme.HDEC2Theme
import com.android.hdec2.ui.viewmodel.HDECViewModel

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val viewModel by viewModels<HDECViewModel>()
		setContent {
			HDEC2Theme {
				// A surface container using the 'background' color from the theme
				Surface(
					modifier = Modifier.fillMaxSize(),
					color = MaterialTheme.colorScheme.background
				) {
					Column(
						modifier = Modifier.fillMaxSize(),
						verticalArrangement = Arrangement.Center,
						horizontalAlignment = Alignment.CenterHorizontally
					) {
						MainScreen(
							inputCoefficients = viewModel.coefficients,
							onCoefficientAChange = {
								viewModel.coefficients = viewModel.coefficients.copy(
									a = it
								)
							},
							coefficientAError = with(viewModel) {
								isCoefficientError(
									coefficient = CoefficientLetter.A,
									coefficientValue = coefficients.a
								) { it == 0.0 }
							},
							onBCoefficientChange = {
								viewModel.coefficients = viewModel.coefficients.copy(
									b = it
								)
							},
							coefficientBError = with(viewModel) {
								isCoefficientError(
									coefficient = CoefficientLetter.B,
									coefficientValue = coefficients.b
								)
							},
							onCCoefficientChange = {
								viewModel.coefficients = viewModel.coefficients.copy(
									c = it
								)
							},
							coefficientCError = with(viewModel) {
								isCoefficientError(
									coefficient = CoefficientLetter.C,
									coefficientValue = coefficients.c
								)
							}
						)
						Button(
							enabled = viewModel.canCalculate,
							onClick = {
								viewModel.calculateRoots(
									numericCoefficients = viewModel.coefficients.toDomain()
								)
							}) {
							Text(text = getString(R.string.calculate_message))
						}
						LazyColumn {
							items(viewModel.steps.size) {
								Column(modifier = Modifier
									.fillMaxWidth()
									.padding(all = 12.dp)) {
									Text(
										text = stringResource(id = viewModel.steps[it].first),
										style = MaterialTheme.typography.titleLarge
									)
									Spacer(modifier = Modifier.height(IntrinsicSize.Max))
									Text(text = viewModel.steps[it].second)
								}
							}
						}
					}
				}
			}
		}
	}
}