package com.technopark.youtrader.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.technopark.youtrader.base.BaseViewModel
import com.technopark.youtrader.model.CryptoCurrencyExample
import com.technopark.youtrader.network.auth.AuthService
import com.technopark.youtrader.network.auth.IAuthService
import com.technopark.youtrader.repository.CryptoCurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: CryptoCurrencyRepository,
    private val authService: AuthService
) : BaseViewModel() {

    private var _cryptoCurrencies: MutableLiveData<List<CryptoCurrencyExample>> = MutableLiveData()
    val cryptoCurrencies: LiveData<List<CryptoCurrencyExample>> = _cryptoCurrencies
    private val _authState: MutableStateFlow<AuthFragmentState> = MutableStateFlow(AuthFragmentState.SignedOut)
    val authState: StateFlow<AuthFragmentState> = _authState

    init {
        Firebase.auth.addAuthStateListener {
            Log.d("FirstFragmentTag", it.currentUser?.displayName ?: "empty name")
        }
        Firebase
    }

    fun getCryptoCurrencies() {
        viewModelScope.launch {
            repository.getCurrencies()
                .collect { cryptoCurrencies ->
                    _cryptoCurrencies.value = cryptoCurrencies

                    // TODO delete
                    if (_authState.value == AuthFragmentState.Success) {
                        _authState.value = AuthFragmentState.SignedIn
                    } else {
                        _authState.value = AuthFragmentState.Success
                    }
                }
        }
    }

    fun signUp(email: String, password: String) = authService.sighUp(email, password)

    fun signIn(email: String, password: String) = authService.signIn(email, password)

    fun checkSignIn(email: String): Boolean = authService.checkSignIn(email)

    fun signOut() = authService.sighOut()

    fun navigateToCurrenciesFragment() {
        val someString = "Random text"
        navigateTo(AuthFragmentDirections.actionAuthFragmentToCurrenciesFragment(someString))
    }
}
