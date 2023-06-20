package com.example.caravan.ui.signup

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalUriHandler
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.caravan.R
import com.example.caravan.data.repository.AccountService
import com.example.caravan.common.ext.*
import com.example.caravan.common.snackbar.SnackbarManager
import com.example.caravan.domain.model.Buyer
import com.example.caravan.domain.model.CardEntity
import com.example.caravan.domain.model.Rep
import com.example.caravan.domain.model.Seller
import com.example.caravan.domain.navigation.Screens
import com.example.caravan.domain.use_cases.PostNewBuyerUseCase
import com.example.caravan.domain.use_cases.PostNewRepUseCase
import com.example.caravan.domain.use_cases.PostNewSellerUseCase
import com.example.caravan.domain.use_cases.SaveCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.caravan.R.string as AppText

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountService: AccountService,
    private val postNewBuyerUseCase: PostNewBuyerUseCase,
    private val postNewSellerUseCase: PostNewSellerUseCase,
    private val postNewRepUseCase: PostNewRepUseCase,
    private val saveCardUseCase: SaveCardUseCase
) : ViewModel() {

    var selected_type = mutableStateOf(1)

    var selectedText = mutableStateOf("")
    var expanded = mutableStateOf(false)

    var email = mutableStateOf("")
    var password = mutableStateOf("")

    var first_name = mutableStateOf("")
    var last_name = mutableStateOf("")
    var brand_name = mutableStateOf("")
    var address = mutableStateOf("")
    var phone = mutableStateOf("")

    var url = mutableStateOf("")

    //--------------------------payment info
    var exM = mutableStateOf("")
    var exY = mutableStateOf("")
    var cvc = mutableStateOf("")
    var num = mutableStateOf("")
    var chn = mutableStateOf("")


    //----------------------------------------------------
    fun getUserId(): String {
        return accountService.getUserId()
    }

    fun String.isNum(): Boolean {
        val s = this
        s.toList().forEach { s ->
            try {
                s.toInt()
            } catch (e: Exception) {
                return false
            }
        }

        return true
    }

    fun CreateNewUser(i: Int, navController: NavHostController) {

        selected_type.value = i

        val isBuyerDataValid =
            i == 1 && chn.value.isNotEmpty() && first_name.value.isNotEmpty() && last_name.value.isNotEmpty() && brand_name.value.isNotEmpty() && address.value.isNotEmpty() && phone.value.isNotEmpty()

        val isSellerDataValid =
            i == 2 && first_name.value.isNotEmpty() && last_name.value.isNotEmpty() && brand_name.value.isNotEmpty() && phone.value.isNotEmpty() && selectedText.value.isNotEmpty()

        val isRepDataValid =
            i == 3 && first_name.value.isNotEmpty() && last_name.value.isNotEmpty() && phone.value.isNotEmpty()

        val isCardValid =
            exM.value.length == 2 && exY.value.length == 2 && cvc.value.length == 3 && num.value.length == 16

        val isNumNum =
            phone.value.isNum() && cvc.value.isNum() && exM.value.isNum() && exY.value.isNum() && num.value.isNum()

        //__________________________________
        if (
            !isBuyerDataValid && !isSellerDataValid && !isRepDataValid
        ) {

            SnackbarManager.showMessage(AppText.empty_data)
            return
        }

        if (!email.value.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        if (!password.value.isValidPassword()) {
            SnackbarManager.showMessage(AppText.password_error)
            return
        }

        if (!isCardValid && i == 1) {
            SnackbarManager.showMessage(AppText.card_error)
            return
        }

        if (!isNumNum) {
            SnackbarManager.showMessage(R.string.incorrect_data)
            return
        }

        viewModelScope.launch(showErrorExceptionHandler) {
            accountService.createAccount(email.value, password.value) { error ->
                if (error == null) {
                    linkWithEmail(navController)
                } else onError(error)
                Log.d("FIREBASETEST", error?.message.toString())
            }
        }
    }

    private fun linkWithEmail(navController: NavHostController) {
        viewModelScope.launch(showErrorExceptionHandler) {

            accountService.linkAccount(email.value, password.value) { error ->
                viewModelScope.launch {
                    async {

                        when (selected_type.value) {
                            1 ->{
                                postNewBuyerUseCase(
                                    Buyer(
                                        id = null,
                                        address = address.value,
                                        autheId = accountService.getUserId(),
                                        brand = brand_name.value,
                                        isActive = false,
                                        owner = "${first_name.value} ${last_name.value}",
                                        phone = phone.value
                                    )
                                )

                                saveCardUseCase(
                                    CardEntity(
                                        name = chn.value,
                                        num = num.value.toInt(),
                                        exM = exM.value.toInt(),
                                        exY = exY.value.toInt(),
                                        cvc = cvc.value.toInt()
                                    )
                                )
                            }

                            2 -> {

                                val res = postNewSellerUseCase(
                                    Seller(
                                        autheId = accountService.getUserId(),
                                        brand = brand_name.value,
                                        id = null,
                                        isActive = false,
                                        ordersId = null,
                                        owner = "${first_name.value} ${last_name.value}",
                                        phone = phone.value,
                                        productsId = null,
                                        type = selectedText.value,
                                        stripeId = ""
                                    )
                                )


                                url.value = res.string().replace("\"", "")
                                Log.d("URLTEST",url.value)
                            }

                            3 -> postNewRepUseCase(
                                Rep(
                                    autheId = accountService.getUserId(),
                                    id = null,
                                    isActive = false,
                                    myBuyers = emptyList(),
                                    mySellers = emptyList(),
                                    name = "${first_name.value} ${last_name.value}",
                                    phone = phone.value,
                                )
                            )
                            else -> {}
                        }

                    }.await()

                    SnackbarManager.showMessage(AppText.account_created)

                    navController.navigate(Screens.Main.passItem("x")) {
                        launchSingleTop = true
                        popUpTo(0) { inclusive = true }
                    }

                }

            }
        }
    }
}