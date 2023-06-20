package com.example.caravan.ui.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.caravan.R
import com.example.caravan.data.repository.AccountService
import com.example.caravan.common.ext.isValidEmail
import com.example.caravan.common.ext.isValidPassword
import com.example.caravan.common.ext.onError
import com.example.caravan.common.ext.showErrorExceptionHandler
import com.example.caravan.common.snackbar.SnackbarManager
import com.example.caravan.domain.navigation.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject
import com.example.caravan.R.string as AppText


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountService: AccountService
) : ViewModel() {


    val email = mutableStateOf("")
    val password = mutableStateOf("")

    fun getuser() {
        accountService.signOut()
    }

    fun signOut(navController: NavHostController){

        accountService.signOut()

        navController.navigate(Screens.Main.passItem("x")) {
            launchSingleTop = true
            popUpTo(0) { inclusive = true }
        }
    }

    fun onSignInClick(navController: NavHostController) {
        if (!email.value.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }
        if (password.value.isBlank() && password.value.isValidPassword()) {
            SnackbarManager.showMessage(AppText.password_error)
            return
        }

        viewModelScope.launch(showErrorExceptionHandler) {
            accountService.authenticate(email.value, password.value) { error ->
                if (error == null) {

                    viewModelScope.launch(Dispatchers.IO) {
                        var x = async { linkWithEmail() }
                        x.await()
                        launch(Dispatchers.Main) {
                            navController.navigate(Screens.Main.passItem("x"))
                            {
                                launchSingleTop = true
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    }


                } else onError(error)
            }
        }

    }

    private fun linkWithEmail() {
        viewModelScope.launch(showErrorExceptionHandler) {
            accountService.linkAccount(email.value, password.value) { error ->
                Log.d("LOGINTEST", error.toString())
            }
        }
    }
}