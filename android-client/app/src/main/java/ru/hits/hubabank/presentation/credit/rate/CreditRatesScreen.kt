package ru.hits.hubabank.presentation.credit.rate

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CreditRatesScreen(
    viewModel: CreditRatesViewModel = hiltViewModel(),
) {
    Text(text = "CreditRates")
}
