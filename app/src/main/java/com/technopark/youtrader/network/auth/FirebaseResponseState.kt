package com.technopark.youtrader.network.auth

//sealed class FirebaseResponseState : AuthResponseState<FirebaseResponseState> {
sealed class FirebaseResponseState : AuthResponseState {
    object NetworkError : FirebaseResponseState()
    object SuccessfulSignUp : FirebaseResponseState()
    object SuccessfulSignIn : FirebaseResponseState()
    object SignedIn : FirebaseResponseState()
    object SuccessfulSignOut : FirebaseResponseState()

    override fun getDefaultState(): FirebaseResponseState {
        return SuccessfulSignIn
    }

//     todo add something
}