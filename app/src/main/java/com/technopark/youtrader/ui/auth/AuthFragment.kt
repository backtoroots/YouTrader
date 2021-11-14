package com.technopark.youtrader.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.technopark.youtrader.R
import com.technopark.youtrader.base.BaseFragment
import com.technopark.youtrader.databinding.AuthFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : BaseFragment(R.layout.auth_fragment) {

    private val binding by viewBinding(AuthFragmentBinding::bind)

    override val viewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val email = "first.user@mail.com"
        val password = "qwerty"

        with(binding) {
            buttonSign.text = getString(R.string.sign_in)
            buttonToNextFragment.text = getString(R.string.to_sign_up)

            buttonSign.setOnClickListener {
                viewModel.signIn(email, password)
            }

            buttonToNextFragment.setOnClickListener {
                viewModel.navigateToRegFragment()
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
        const val TAG = "AuthFragmentTag"
    }
}
