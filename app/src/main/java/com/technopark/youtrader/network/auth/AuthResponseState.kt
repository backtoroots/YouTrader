package com.technopark.youtrader.network.auth

sealed interface AuthResponseState {
    fun getDefaultState(): AuthResponseState
}
