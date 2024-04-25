package com.example.voyd.utils



import android.app.AlertDialog
import android.content.Context
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.example.voyd.R
import com.example.voyd.presentation.viewState.Resource

object AppConstants {
    const val BASE_URL = "https://reacttask.mkdlabs.com"
    const val LOGIN_X_PROJECT_URL = "cmVhY3R0YXNrOjVmY2h4bjVtOGhibzZqY3hpcTN4ZGRvZm9kb2Fjc2t5ZQ=="
    const val REGISTER_X_PROJECT_URL = "cmVhY3R0YXNrOmQ5aGVkeWN5djZwN3p3OHhpMzR0OWJtdHNqc2lneTV0Nw=="
    const val UPDATE_PROFILE_X_PROJECT_URL = "cmVhY3R0YXNrOjVmY2h4bjVtOGhibzZqY3hpcTN4ZGRvZm9kb2Fjc2t5ZQ=="
    const val FORGOT_PASSWORD_X_PROJECT_URL = "cmVhY3R0YXNrOjVmY2h4bjVtOGhibzZqY3hpcTN4ZGRvZm9kb2Fjc2t5ZQ=="
    const val RESET_PASSWORD_X_PROJECT_URL = "cmVhY3R0YXNrOjVmY2h4bjVtOGhibzZqY3hpcTN4ZGRvZm9kb2Fjc2t5ZQ=="
    const val RESET_PASSWORD_TOKEN = "1181961E-45A2-479B-B43F-69C5817258B5-57D2-16489608"

    /**
     * SHARED PREFERENCES KEYS
     * */
    const val STRING_SESSION_TOKEN_SHARED_PREFS_KEY = "string_session_token_shared_prefs_key"
    const val TEMP_USER_DETAILS = "temp_user_details"

    /**
     * INTERCEPTOR TAGS
     * */
    const val STRING_LOGGING_INTERCEPTOR_TAG = "logging_interceptor"
    const val STRING_AUTH_INTERCEPTOR_TAG = "auth_interceptor"

    /**
     * CALL TIMEOUT
     * */
    const val TIME_OUT_15 = 15L

    /**
     * TOAST
     * */
    fun Fragment.showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    /**
     * VALIDATE PASSWORD
     * */
    fun validatePasswordMismatch(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }

    /**
     * ALERT DIALOG
     * */
    fun alertDialog(
        context: Context
    ): AlertDialog {
        val dialogView: View =
            LayoutInflater.from(context).inflate(R.layout.layout_custom_loading_dialog, null)
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)
        dialogBuilder.setCancelable(false)
        dialogBuilder.setView(dialogView)
        return dialogBuilder.create()
    }


}