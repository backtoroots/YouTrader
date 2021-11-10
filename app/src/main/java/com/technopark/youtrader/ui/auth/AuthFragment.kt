package com.technopark.youtrader.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.technopark.youtrader.R
import com.technopark.youtrader.base.BaseFragment
import com.technopark.youtrader.databinding.AuthFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class AuthFragment : BaseFragment(R.layout.auth_fragment) {

    private val binding by viewBinding(AuthFragmentBinding::bind)

    override val viewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Don't forget to call super.onViewCreated
        super.onViewCreated(view, savedInstanceState)
        val email = "user3@mail.com"
        val password = "qwerty"

        with(binding) {
            buttonSignIn.setOnClickListener {
                viewModel.signIn(email, password)
            }

            buttonCheckSignIn.setOnClickListener {
                viewModel.checkSignIn(email)
            }

            buttonSignUp.setOnClickListener {
                viewModel.signUp(email, password)
            }

            buttonSignOut.setOnClickListener {
                viewModel.signOut()
            }

            buttonToNextFragment.setOnClickListener {
                viewModel.navigateToCurrenciesFragment()
            }

            buttonBack.setOnClickListener {
                viewModel.goBack()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.authState.collect { state ->
                when (state) {
                    is AuthFragmentState.Success -> {
                        Log.d(TAG, "State is success")
                    }
                    is AuthFragmentState.Failure -> {
                        Log.d(TAG, "State is failure")
                    }
                    is AuthFragmentState.SignedIn -> {
                        Log.d(TAG, "State is signed in")
                    }
                    is AuthFragmentState.SignedOut -> {
                        Log.d(TAG, "State is signed out")
                    }
                }
            }
        }

        viewModel.cryptoCurrencies.observe(
            viewLifecycleOwner,
            { currencies ->
                Log.d(TAG, "Received: ${currencies.first()}")
                Toast.makeText(
                    requireContext(),
                    "Received: ${currencies.first()}",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        )
        viewModel.getCryptoCurrencies()
    }

    companion object {
        const val TAG = "FirstFragmentTag"
    }
}
