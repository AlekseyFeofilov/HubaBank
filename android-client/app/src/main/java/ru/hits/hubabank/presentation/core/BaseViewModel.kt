package ru.hits.hubabank.presentation.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<State: ScreenState, Action : ScreenAction>(
    initState: State,
) : ViewModel(), CoroutineScope {

    protected val _screenState by lazy { MutableStateFlow(initState) }
    protected val currentState: State get() = screenState.value
    val screenState: StateFlow<State> = _screenState.asStateFlow()

    private val _action = Channel<Action>()
    val action = _action.receiveAsFlow()

    override val coroutineContext: CoroutineContext = viewModelScope.coroutineContext +
            Dispatchers.Default + CoroutineExceptionHandler { coroutineContext, throwable ->
        Timber.e(throwable, "Exception in $coroutineContext")
    }


    protected fun sendAction(action: Action) {
        launch {
            _action.send(action)
        }
    }
}