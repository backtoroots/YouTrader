package com.technopark.youtrader.network.auth

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface IAuthService {

    fun checkSignIn(email: String): Boolean
    fun signIn(email: String, password: String)
    fun sighUp(email: String, password: String)
    fun sighOut()
    fun updatePassword(password: String)
}

// abstract class AuthService<T : AuthResponseState> : IAuthService {
abstract class AuthService : IAuthService {
    lateinit var stateClass: AuthResponseState
    protected val _authResponseState: MutableStateFlow<AuthResponseState> =
        MutableStateFlow(stateClass.getDefaultState())
    val authResponseState: StateFlow<AuthResponseState> = _authResponseState

        fun getDefaultValue(state: AuthResponseState) {
            stateClass = state
        }
    companion object {
    }
}
