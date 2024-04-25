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
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.voyd.R
import com.example.voyd.data.remote.dto.LoginRequestDTO
import com.example.voyd.databinding.FragmentLogInBinding
import com.example.voyd.domain.interactors.SessionManager
import com.example.voyd.presentation.ui.activities.MainActivity
import com.example.voyd.presentation.viewModel.VoydViewModel
import com.example.voyd.presentation.viewState.Resource
import com.example.voyd.utils.AppConstants.alertDialog
import com.example.voyd.utils.AppConstants.showToast
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject


@AndroidEntryPoint
class LogInFragment : Fragment() {

    private lateinit var binding: FragmentLogInBinding
    private val viewModel by activityViewModels<VoydViewModel>()
    private lateinit var dialog: AlertDialog
    private var isPasswordVisible = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_log_in, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog = alertDialog(requireContext())
        privacySpannableText()
        binding.login.setOnClickListener {
            if (binding.email.text.toString().trim().isEmpty()) {
                showToast("Email cannot be empty")
                return@setOnClickListener
            }
            if (binding.password.text.toString().trim().isEmpty()) {
                showToast("Password cannot be empty")
                return@setOnClickListener
            }
            loginUser()
        }

        binding.register.setOnClickListener {
            findNavController().navigate(R.id.signUpFragment)
        }


        binding.forgotPassword.setOnClickListener {
            findNavController().navigate(R.id.forgotPasswordFragment)
        }


    }


    private fun loginUser() {
           val email = binding.email.text.toString().trim()
           val password = binding.password.text.toString().trim()
        viewModel.login(email, password)
        dialog.show()

        viewModel.loginResponse.observe(viewLifecycleOwner) {
            if(it is Resource.Loading){
                dialog.show()
            }else{
                dialog.dismiss()
            }
            when (it) {
                is Resource.Success -> {
                    //viewModel.clearUser()
                    Log.d("NOT_TOKEN", it.value.toString())
                    showToast("Login Successful")
                    val intent = Intent(requireActivity(), MainActivity::class.java)
                    startActivity(intent)

                }

                is Resource.Failure -> {
                    Log.d("FAILURE", it.toString())
                    try {
                        val jsonObject = JSONObject(it.errorBody)
                        val error = jsonObject.getString("message")
                        showToast(error)
                        viewModel.loginResponse.removeObservers(viewLifecycleOwner)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
                else -> {}
            }

        }
    }

    private fun privacySpannableText() {
        val signUpText = "Don't have an account?  "
        val termsAndConditionsText = "Sign up"

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

        // Set the text to the TextView
        binding.register.text = spannableString
    }


}