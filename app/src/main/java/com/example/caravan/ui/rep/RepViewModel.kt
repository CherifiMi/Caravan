package com.example.caravan.ui.rep

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caravan.data.repository.CaravanRepository
import com.example.caravan.domain.model.*
import com.example.caravan.domain.use_cases.GetBuyerByKeyUseCase
import com.example.caravan.domain.use_cases.GetSellerByKeyUseCase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class RepViewModel @Inject constructor(
    private val repository: CaravanRepository,
    private val getBuyerByKeyUseCase: GetBuyerByKeyUseCase,
    private val getSellerByKeyUseCase: GetSellerByKeyUseCase,
) : ViewModel() {

    val rep: Rep = runBlocking {
        Gson().fromJson(
            repository.getSavedUser().user,
            RepList::class.java
        )[0]
    }
    var repMyBuyers = mutableStateOf(mutableListOf<Buyer>())
    var repMySellers = mutableStateOf(mutableListOf<Seller>())

    init {
        for (i in rep.myBuyers!!) {
            val buyer = runBlocking {
                async {
                    getBuyerByKeyUseCase(Id(id = i)).collectLatest { respone ->
                        respone.data?.string()?.let {
                            Log.d("Mito", it)
                            repository.saveUser(it)
                        }
                    }
                }.await()


                Gson().fromJson(
                    repository.getSavedUser().user,
                    BuyersList::class.java
                )[0]

            }

            repMyBuyers.value.add(buyer)
        }

        for (i in rep.mySellers!!) {
            val seller = runBlocking {
                async {
                    getSellerByKeyUseCase(Id(id = i)).collectLatest { respone ->
                        respone.data?.string()?.let {
                            Log.d("Mito", it)
                            repository.saveUser(it)
                        }
                    }
                }.await()


                Gson().fromJson(
                    repository.getSavedUser().user,
                    SellerList::class.java
                )[0]

            }
            repMySellers.value.add(seller)
        }
        Log.d(
            "MITOREP",
            "buyers  " + repMyBuyers.value.toString() + " sellers  " + repMySellers.value.toString()
        )
    }

}

