package com.example.voyd.presentation.ui.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.voyd.R
import com.example.voyd.data.remote.dto.RegisterUserDTO
import com.example.voyd.databinding.FragmentSignUpBinding
import com.example.voyd.presentation.ui.activities.MainActivity
import com.example.voyd.presentation.viewModel.VoydViewModel
import com.example.voyd.presentation.viewState.Resource
import com.example.voyd.utils.AppConstants.alertDialog
import com.example.voyd.utils.AppConstants.showToast
import com.example.voyd.utils.AppConstants.validatePasswordMismatch
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException
import org.json.JSONObject


@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val viewModel by activityViewModels<VoydViewModel>()
    private lateinit var dialog: AlertDialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog = alertDialog(requireContext())
        privacySpannableText()
        binding.login.setOnClickListener {
            findNavController().navigate(R.id.logInFragment)
        }


        binding.register.setOnClickListener {
            signUp()
        }


    }

    private fun signUp() {
        when {
            binding.email.text.toString().isEmpty() -> {
                binding.email.error =
                    getString(R.string.all_please_enter_first_name)
            }
            binding.email.text.toString().isEmpty() -> {
                binding.fragmentEmail.error =
                    getString(R.string.all_please_enter_last_name)
            }
            binding.password.text.toString().isEmpty() -> {
                showToast(getString(R.string.all_please_enter_password))
            }
            binding.confirmPassword.text.toString().isEmpty() -> {
                showToast(getString(R.string.confirm_password))
            }
            !validatePasswordMismatch(
                binding.password.text.toString().trim(),
                binding.confirmPassword.text.toString().trim()
            ) -> {
                showToast("Password mismatch")
            }
            binding.confirmPassword.text.toString().trim().isEmpty() -> {
                binding.password.error = ""
            }
            else -> {
                if (validateSignUpFieldsOnTextChange()) {
                    register()
                }
            }
        }
    }

    private fun register() {

        val registerRequest = RegisterUserDTO(
            is_refresh = false,
            role = "member",
            email = binding.email.text.toString().trim(),
            password = binding.password.text.toString().trim(),
        )

        viewModel.register(registerRequest)
        viewModel.registerResponse.observe(viewLifecycleOwner) {
            if(it is Resource.Loading){
                dialog.show()
            }else{
                dialog.dismiss()
            }
            when (it) {
                is Resource.Success -> {
                    //viewModel.clearUser()

                    it?.let { showToast("Registration successful") }
                    findNavController().navigate(R.id.logInFragment)

                }

                is Resource.Failure -> {
                    Log.d("FAILURE", it.toString())
                    try {
                        val jsonObject = JSONObject(it.errorBody)
                        val error = jsonObject.getString("message")
                        showToast(error)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
                else -> {}
            }
        }
    }


    private fun validateSignUpFieldsOnTextChange(): Boolean {
        var isValidated = true

        binding.email.doOnTextChanged { _, _, _, _ ->
            when {
                binding.email.text.toString().trim().isEmpty() -> {
                    showToast(getString(R.string.all_please_enter_first_name))
                    isValidated = false
                }
                binding.email.text.toString().trim().isNotEmpty() -> {
                    binding.email.error = ""
                    isValidated = true
                }
                else -> {
                    binding.email.error = null
                    isValidated = true
                }
            }
        }
        binding.confirmPassword.doOnTextChanged { _, _, _, _ ->
            when {
                binding.confirmPassword.text.toString().trim().isEmpty() -> {
                    showToast(getString(R.string.all_please_enter_email_address))
                    isValidated = false
                }
                binding.confirmPassword.text.toString().trim().isNotEmpty() -> {
                    binding.confirmPassword.error = ""
                    isValidated = true
                }
                else -> {
                    binding.confirmPassword.error = null
                    isValidated = true
                }
            }
        }
        binding.password.doOnTextChanged { _, _, _, _ ->
            when {
                binding.password.text.toString().trim().isEmpty() -> {
                    binding.password.error =
                        getString(R.string.all_please_enter_password)
                    isValidated = false
                }
                !validatePasswordMismatch(
                    binding.password.text.toString().trim(),
                    binding.confirmPassword.text.toString().trim()
                ) -> {
                    binding.password.error =
                        getString(R.string.all_password_mismatch)
                    isValidated = false
                }
                binding.confirmPassword.text.toString().trim().isEmpty() -> {
                    binding.password.error = ""
                    isValidated = true
                }
                else -> {
                    binding.password.error = null
                    isValidated = true
                }
            }
        }
        return isValidated
    }


    private fun privacySpannableText() {
        val signUpText = "Already a member?  "
        val termsAndConditionsText = "Login"

        val spannableString =
            SpannableString("$signUpText$termsAndConditionsText")

        // Set color for "terms & conditions"
        val termsAndConditionsStartIndex = signUpText.length
        val termsAndConditionsEndIndex =
            termsAndConditionsStartIndex + termsAndConditionsText.length
        spannableString.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.white)),
            termsAndConditionsStartIndex,
            termsAndConditionsEndIndex,
            0
        )

        // Set color for "privacy policy"
        val privacyPolicyStartIndex = termsAndConditionsEndIndex // Adding 5 for " and "
        val privacyPolicyEndIndex = spannableString.length
        spannableString.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.white)),
            privacyPolicyStartIndex,
            privacyPolicyEndIndex,
            0
        )
        Log.d("START", privacyPolicyStartIndex.toString())

        // Set the text to the TextView
        binding.login.text = spannableString
    }



}