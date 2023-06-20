package com.example.caravan.ui.seller

import android.content.ContentResolver
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.caravan.MainViewModel
import com.example.caravan.R
import com.example.caravan.common.snackbar.SnackbarManager
import com.example.caravan.data.repository.AccountService
import com.example.caravan.data.repository.CaravanRepository
import com.example.caravan.data.util.Result
import com.example.caravan.domain.model.*
import com.example.caravan.domain.use_cases.*
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class SellerViewModel @Inject constructor(
    private val getAllSellerProductsUseCase: GetAllSellerProductsUseCase,
    private val repository: CaravanRepository,
    private val createNewProductUseCase: CreateNewProductUseCase,
    private val changeThisProductUseCase: ChangeThisProductUseCase,
    private val uploadImageGetUrlUseCase: UploadImageGetUrlUseCase,
    private val accountLinkUseCase: AccountLinkUseCase,
    private val deleteThisProductUseCase: DeleteThisProductUseCase,
    private val getBuyerByKeyUseCase: GetBuyerByKeyUseCase,
    private val deleteThisOrder: DeleteThisOrder,
    private val getMyOrdersUseCase: GetMyOrdersUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase
) : ViewModel() {

    val myCats = runBlocking {
        repository.getSavedCats()
    }

    val tag = "SELLER_TEST"

    //__________________________values
    val loading = mutableStateOf(true)
    var myProducts: MutableState<List<Product>?> = mutableStateOf(null)


    val name = mutableStateOf("")
    val content = mutableStateOf("")
    val cats = mutableListOf<String>()

    val images = mutableListOf<String>()
    val image0 = mutableStateOf("")
    val image1 = mutableStateOf("")
    val image2 = mutableStateOf("")
    val image3 = mutableStateOf("")


    val fPrice = mutableStateOf("")
    val sPrice = mutableStateOf("")
    val inv = mutableStateOf("")

    val minOrder = mutableStateOf("")

    val id = mutableStateOf("")
    val sellerKey = mutableStateOf("")
    var myOrders: MutableState<OrderList?> = mutableStateOf(null)

    val savedSeller =
        Gson().fromJson(
            runBlocking(Dispatchers.IO) { repository.getSavedUser() }.user,
            SellerList::class.java
        )[0]

    var url = mutableStateOf("")

    //___________________________functions
    fun getMyOrders(sellerId: String) {
        viewModelScope.launch {
            getMyOrdersUseCase(Id(id = sellerId)).onEach {
                when (it) {
                    is Result.Loading -> {
                        Log.d("ORDERTEST", "loading")
                    }
                    is Result.Success -> {
                        try {
                            myOrders.value =
                                Gson().fromJson(it.data?.string(), OrderList::class.java)

                        } catch (e: Exception) {
                            Log.e("ORDERTEST", "ORDERTEST: ", e)

                        }
                    }
                    is Result.Error -> {
                        Log.d("ORDERTEST", it.message.toString())
                    }

                }
            }.collectLatest {

                Log.d("ORDERTEST", "last is " + myOrders.value.toString())

            }
        }
    }

    fun deleteOrderByKey(key: String, sellerId: String) {
        viewModelScope.launch {
            async { deleteThisOrder(Id(id = key)) }.await()
            getMyOrders(sellerId)
        }
    }

    fun getBuyerByKey(key: String): Buyer? = runBlocking(Dispatchers.IO) {

        var buyer: Buyer? = null

        Log.d("GETBUYERTEST", key)

        async {
            try {

                getBuyerByKeyUseCase(Id(id = key)).collectLatest { respone ->
                    respone.data?.string()?.let {
                        buyer = Gson().fromJson(it, BuyersList::class.java)[0]

                    }
                }
            } catch (e: Exception) {
                Log.d("GETBUYERTEST", e.message.toString())
            }
        }.await()

        Log.d("GETBUYERTEST", buyer.toString())
        buyer

    }

    fun getSellerProducts(sellerId: String) {
        getAllSellerProductsUseCase(Id(sellerId)).onEach {
            when (it) {
                is Result.Success -> {
                    loading.value = false
                    myProducts.value = it.data
                    Log.d(tag, it.data.toString())
                }
                is Result.Error -> {
                    loading.value = true
                    Log.d(tag, it.message.toString())
                }

            }
        }.launchIn(viewModelScope)
    }

    fun uploadImageGetUrl(x: MutableState<String>, uri: Uri, cn: ContentResolver) {
        uploadImageGetUrlUseCase(uri, cn, x)

        images.clear()
        images.addAll(listOf(image0.value, image1.value, image2.value, image3.value))
    }

    fun isSellerActive(): Payout {

        val job = runBlocking {
            accountLinkUseCase(Id(id = savedSeller.stripeId)).string()
        }

        return Gson().fromJson(job, Payout::class.java)
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

    fun createNewProduct(sellerId: String, popBack: () -> Unit) {

        val allFieldsAreFull = cats.size > 0 &&
                content.value.isNotEmpty() &&
                name.value.isNotEmpty() &&
                image0.value.isNotEmpty() &&
                image1.value.isNotEmpty() &&
                image2.value.isNotEmpty() &&
                image3.value.isNotEmpty() &&
                fPrice.value.isNotEmpty() &&
                sPrice.value.isNotEmpty() &&
                inv.value.isNotEmpty() &&
                minOrder.value.isNotEmpty()

        val isNumNum =
            sPrice.value.isNum() && fPrice.value.isNum() && minOrder.value.isNum() && inv.value.isNum()

        val isFPbiggerSP = fPrice.value >= sPrice.value

        if (!allFieldsAreFull) {
            SnackbarManager.showMessage(R.string.empty_data)
            return
        }

        if (!isFPbiggerSP) {
            SnackbarManager.showMessage(R.string.FpSp)
            return
        }

        if (!isNumNum) {
            SnackbarManager.showMessage(R.string.incorrect_data)
            return
        }

        viewModelScope.launch {
            val x = createNewProductUseCase(
                Product(
                    id = null,
                    content = content.value,
                    name = name.value,
                    cat = cats,
                    imageUrls = listOf(image0.value, image1.value, image2.value, image3.value),
                    initPrice = fPrice.value.toInt(),
                    minOrder = minOrder.value,
                    newPrice = sPrice.value.toInt(),
                    sellerKey = sellerId,
                    amountInInv = inv.value.toInt(),
                    sellerStripe = savedSeller.stripeId
                )
            )
            Log.d(tag, x.toString())
        }

        SnackbarManager.showMessage(R.string.product_created)

        getSellerProducts(sellerId)

        popBack()
    }

    fun updateProduct(sellerId: String, popBack: () -> Unit) {

        val allFieldsAreFull = cats.size > 0 &&
                content.value.isNotEmpty() &&
                name.value.isNotEmpty() &&
                fPrice.value.isNotEmpty() &&
                sPrice.value.isNotEmpty() &&
                inv.value.isNotEmpty() &&
                minOrder.value.isNotEmpty()

        if (
            !allFieldsAreFull
        ) {
            SnackbarManager.showMessage(R.string.empty_data)
            return
        }

        val isFPbiggerSP = fPrice.value >= sPrice.value

        if (!isFPbiggerSP) {
            SnackbarManager.showMessage(R.string.FpSp)
            return
        }


        viewModelScope.launch {
            val x = changeThisProductUseCase(
                Product(
                    id = id.value,
                    content = content.value,
                    name = name.value,
                    cat = cats,
                    imageUrls = listOf(image0.value, image1.value, image2.value, image3.value),
                    initPrice = fPrice.value.toInt(),
                    minOrder = minOrder.value,
                    newPrice = sPrice.value.toInt(),
                    sellerKey = sellerId,
                    amountInInv = inv.value.toInt(),
                    sellerStripe = savedSeller.stripeId
                )
            )
            Log.d(tag, x.toString())
        }

        SnackbarManager.showMessage(R.string.product_updated)

        getSellerProducts(sellerId)

        popBack()
    }

    fun setCurrentItemValues(id: String, sellerId: String) {

        if (id.toInt() == -1) {
            return
        }

        getSellerProducts(sellerId = sellerId)

        val item = myProducts.value?.get(id.toInt())
        if (loading.value) {
            return
        }



        if (item != null) {

            name.value = item.name
            content.value = item.content

            this.id.value = item.id ?: ""
            sellerKey.value = item.sellerKey

            minOrder.value = item.minOrder

            item.let { if (cats.size < 4) cats.addAll(it.cat) }


            item.let { if (images.size < 4) images.addAll(it.imageUrls) }
            image0.value = images[0]
            image1.value = images[1]
            image2.value = images[2]
            image3.value = images[3]

            fPrice.value = item.initPrice.toString()
            sPrice.value = item.newPrice.toString()

            inv.value = item.amountInInv.toString()
        }
    }

    fun deleteThisProduct(navController: NavHostController?) {

        viewModelScope.launch(Dispatchers.IO) {
            deleteThisProductUseCase(Id(id = id.value))
            SnackbarManager.showMessage(R.string.product_deleted)
        }

        navController?.popBackStack()

    }

    fun getInvCount(): MutableState<Int> {

        var inv = mutableStateOf(0)

        for (i in myProducts.value!!) {

            inv.value += i.amountInInv

        }

        return inv

    }

    fun getProductNameById(s: String): String =
        runBlocking {
            getProductByIdUseCase(Id(s)).name
        }

}



