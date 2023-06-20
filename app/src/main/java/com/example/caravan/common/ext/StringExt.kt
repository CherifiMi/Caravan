package com.example.caravan.common.ext

import android.util.Log
import android.util.Patterns
import com.example.caravan.R
import com.example.caravan.common.snackbar.SnackbarManager
import kotlinx.coroutines.CoroutineExceptionHandler
import java.util.regex.Pattern


private const val MIN_PASS_LENGTH = 6
private const val PASS_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$"

val showErrorExceptionHandler = CoroutineExceptionHandler { _, throwable ->
    onError(throwable)
}


fun onError(error: Throwable) {
    SnackbarManager.showMessage(R.string.somthing_wrong)
}

fun String.isValidEmail(): Boolean {
    return this.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPassword(): Boolean {
    return this.isNotBlank()
            && this.length >= MIN_PASS_LENGTH
            && Pattern.compile(PASS_PATTERN).matcher(this).matches()
}

fun String.passwordMatches(repeated: String): Boolean {
    return this == repeated
}

fun String.idFromParameter(): String {
    return this.substring(1, this.length-1)
}
