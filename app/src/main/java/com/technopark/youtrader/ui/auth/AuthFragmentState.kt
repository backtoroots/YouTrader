package com.technopark.youtrader.ui.auth

sealed class AuthFragmentState {
    object Success : AuthFragmentState()
    data class Failure(val msg: Throwable) : AuthFragmentState()
    object SignedIn : AuthFragmentState()
    object SignedOut : AuthFragmentState()
}
