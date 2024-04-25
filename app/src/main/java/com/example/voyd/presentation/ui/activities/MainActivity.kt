package com.example.voyd.presentation.ui.activities

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.activityViewModels
import com.example.voyd.R
import com.example.voyd.databinding.ActivityMainBinding
import com.example.voyd.domain.models.Payload
import com.example.voyd.domain.models.UpdateProfileDTO
import com.example.voyd.presentation.viewModel.VoydViewModel
import com.example.voyd.presentation.viewState.Resource
import com.example.voyd.utils.AppConstants
import com.example.voyd.utils.AppConstants.alertDialog
import com.example.voyd.utils.AppConstants.showToast
import com.google.android.material.navigation.NavigationView
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var _binding: ActivityMainBinding
    private val binding get() = _binding
    private lateinit var navView: NavigationView
    private val viewModel by viewModels<VoydViewModel>()
    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dialog = alertDialog(this)


        drawerLayout = binding.drawerLayout
        navView = binding.navView

    }

    private fun updateProfile() {
        val firstName = binding.appBarDashboard.contentDashboard.firstName.text.toString().trim()
        val lastName = binding.appBarDashboard.contentDashboard.secondName.text.toString().trim()
        val payload = Payload(
            first_name = firstName,
            last_name = lastName
        )
        val updateProfile = UpdateProfileDTO(
            payload = payload
        )
        viewModel.updateUserProfile(updateProfile)
        viewModel.userDetailsResponse.observe(this) {
            if(it is Resource.Loading){
                dialog.show()
            }else{
                dialog.dismiss()
            }
            when (it) {
                is Resource.Success -> {
                    //viewModel.clearUser()
                    Log.d("NOT_TOKEN", it.value.toString())
                    Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show()
                }

                is Resource.Failure -> {
                    Log.d("FAILURE", it.toString())
                    try {
                        val jsonObject = JSONObject(it.errorBody)
                        val error = jsonObject.getString("message")
                        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
                else -> {}
            }

        }
    }

}