package com.example.caravan

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.caravan.data.repository.AccountService
import com.example.caravan.data.repository.CaravanRepository
import com.example.caravan.data.util.Result
import com.example.caravan.domain.model.*
import com.example.caravan.domain.navigation.Screens
import com.example.caravan.domain.use_cases.*
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getUserTypeUseCase: GetUserTypeUseCase,
    private val getSellerByKeyUseCase: GetSellerByKeyUseCase,
    private val getBuyerByKeyUseCase: GetBuyerByKeyUseCase,
    private val getRepByKeyUseCase: GetRepByKeyUseCase,
    private val ac: AccountService,
    private val getCatsUseCase: GetCatsUseCase,
    private val repository: CaravanRepository
) : ViewModel() {

    val fireId = ac.getUserId()

    //__________________________values
    private val _spalsh = MutableStateFlow(true)
    val spalsh = _spalsh.asStateFlow()

    val there_is_net = mutableStateOf(true)
    var firstScreen = ""

    //___________________________functions

    fun onSplashScreen(userId: String) {
        if (there_is_net.value) {
            if (!ac.hasUser()) {
                firstScreen = "login"
                _spalsh.value = false
            } else {
                val usertype = getUserType(userId)
                Log.d("TESTAPI", usertype)
                if (!getIsUserActivated(usertype, userId)) {
                    firstScreen = "wait"
                    _spalsh.value = false
                } else {
                    getCats()
                    firstScreen = usertype
                    _spalsh.value = false
                }
            }
        } else {
            firstScreen = "nonet"
            //_spalsh.value = false
        }
    }

    private fun getCats() {

        runBlocking(Dispatchers.IO) {

            getCatsUseCase().collectLatest { response ->
                response.data?.string()?.let { repository.saveCats(it) }
            }
        }

    }


    fun signOut(navController: NavHostController) {

        ac.signOut()

        navController.navigate(Screens.Main.passItem("x")) {
            launchSingleTop = true
            popUpTo(0) { inclusive = true }
        }
    }


    fun getIsUserActivated(usertype: String, userId: String): Boolean {
        when (usertype) {
            "buyer" -> {
                runBlocking {
                    getProductsUseCase().onEach {
                        when (it) {
                            is Result.Loading -> {
                                Log.d("TESTAPI", "loading")
                            }
                            is Result.Success -> {
                                Log.d("TESTAPI", it.data.toString())
                                return@onEach
                            }
                            is Result.Error -> {
                                Log.d("TESTAPI", it.message.toString())
                            }

                        }
                    }.collectLatest { repository.saveProductList(it.data) }
                }


                val job: Boolean = runBlocking {
                    async {
                        getBuyerByKeyUseCase(Id(id = userId)).collectLatest { respone ->
                            respone.data?.string()?.let {
                                Log.d("Mito", it)
                                repository.saveUser(it)
                            }
                        }
                    }.await()

                    val buyer =
                        Gson().fromJson(
                            repository.getSavedUser().user,
                            BuyersList::class.java
                        )[0]
                    buyer.isActive

                }

                return job
            }
            "seller" -> {

                val job: Boolean = runBlocking {
                    async {
                        getSellerByKeyUseCase(Id(id = userId)).collectLatest { respone ->
                            respone.data?.string()?.let {
                                Log.d("Mito", it)
                                repository.saveUser(it)
                            }
                        }
                    }.await()

                    val seller =
                        Gson().fromJson(
                            repository.getSavedUser().user,
                            SellerList::class.java
                        )[0]

                    seller.isActive

                }

                return job
            }
            else -> {
                val job: Boolean = runBlocking {
                    async {
                        getRepByKeyUseCase(Id(id = userId)).collectLatest { respone ->
                            respone.data?.string()?.let {
                                Log.d("Mito", it)
                                repository.saveUser(it)
                            }
                        }
                    }.await()

                    val rep =
                        Gson().fromJson(
                            repository.getSavedUser().user,
                            RepList::class.java
                        )[0]
                    rep.isActive

                }

                return job
            }
        }

        return false
    }

    fun getUserType(userId: String): String {
        try {
            return getUserTypeUseCase(Id(id = userId))
        } catch (e: Exception) {
            return getUserType(userId)
        }
    }


}

