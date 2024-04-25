package com.example.voyd.presentation.ui.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.voyd.R
import com.example.voyd.databinding.FragmentForgotPasswordBinding
import com.example.voyd.databinding.FragmentLogInBinding
import com.example.voyd.presentation.ui.activities.MainActivity
import com.example.voyd.presentation.viewModel.VoydViewModel
import com.example.voyd.presentation.viewState.Resource
import com.example.voyd.utils.AppConstants
import com.example.voyd.utils.AppConstants.alertDialog
import com.example.voyd.utils.AppConstants.showToast
import org.json.JSONException
import org.json.JSONObject


/**
 * A simple [Fragment] subclass.
 * Use the [ForgotPasswordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ForgotPasswordFragment : Fragment() {

    private lateinit var binding: FragmentForgotPasswordBinding
    private val viewModel by activityViewModels<VoydViewModel>()
    private lateinit var dialog: AlertDialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forgot_password, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog = alertDialog(requireContext())

        binding.submit.setOnClickListener {
            if (binding.email.text.toString().trim().isEmpty()){
                showToast("Email cannot be empty")
                return@setOnClickListener
            }
            forgotPassword()
        }
    }


    private fun forgotPassword() {
        val email = binding.email.text.toString().trim()
        viewModel.forgotPassword(email)
        viewModel.forgotPasswordResponse.observe(viewLifecycleOwner) {
            if(it is Resource.Loading){
                dialog.show()
            }else{
                dialog.dismiss()
            }
            when (it) {
                is Resource.Success -> {
                    //viewModel.clearUser()
                    showToast("Successful")
                    findNavController().navigate(R.id.resetPasswordFragment)
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
}