package ru.hits.hubabank.presentation.credit.adding

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CreditAddingScreen(
    viewModel: CreditAddingViewModel = hiltViewModel(),
) {
    Text(text = "CreditAdding")
}
