package ru.androidschool.intensiv.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class BaseViewModel<State, Effect, Action> : ViewModel() {

    protected val mutableState = MutableStateFlow<State?>(null)
    val state: StateFlow<State?> get() = mutableState.asStateFlow()

    protected val mutableEffect: Channel<Effect> = Channel()
    val effect: Flow<Effect> = mutableEffect.receiveAsFlow()

    protected val scope = SupervisorJob() + Dispatchers.IO

    abstract fun handleAction(action: Action)
}
