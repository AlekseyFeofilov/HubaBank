package ru.hits.hubabank.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ErrorSnackbar(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    SnackbarHost(hostState = snackbarHostState, modifier = modifier) {
        Snackbar(
            snackbarData = it,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            containerColor = MaterialTheme.colorScheme.error,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

fun SnackbarHostState.showSnackbar(coroutineScope: CoroutineScope, message: String) {
    coroutineScope.launch {
        showSnackbar(message)
    }
}
