package ru.hits.hubabank.presentation.core

import android.content.res.Resources
import androidx.annotation.StringRes
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.compositionLocalOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

val LocalSnackbarController = compositionLocalOf<SnackbarController> { error("SnackbarController not provided") }

class SnackbarController(
    private val snackbarHostState: SnackbarHostState,
    private val coroutineScope: CoroutineScope,
    private val resources: Resources,
) {

    fun show(@StringRes messageRes: Int) {
        coroutineScope.launch {
            snackbarHostState.showSnackbar(resources.getString(messageRes))
        }
    }
}
