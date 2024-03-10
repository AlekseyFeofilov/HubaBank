package ru.hits.hubabank.presentation.credit.info

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CreditInfoScreen(
    viewModel: CreditInfoViewModel = hiltViewModel(),
) {
    Text(text = "CreditInfo")
}
