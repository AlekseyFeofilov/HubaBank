package ru.hits.hubabank.presentation.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow

@Composable
fun <Action: ScreenAction> Flow<Action>.CollectAction(actionHandler: (Action) -> Unit) {
    LaunchedEffect(key1 = Unit) {
        this@CollectAction.collect {
            actionHandler(it)
        }
    }
}
