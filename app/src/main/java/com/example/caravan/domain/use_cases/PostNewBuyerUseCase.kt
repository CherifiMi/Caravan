package com.example.caravan.domain.use_cases

import com.example.caravan.data.repository.CaravanRepository
import com.example.caravan.data.util.Result
import com.example.caravan.domain.model.Buyer
import com.example.caravan.domain.model.BuyersList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostNewBuyerUseCase @Inject constructor(
    private val repository: CaravanRepository
){
    suspend operator fun invoke(buyer: Buyer): ResponseBody{
        return repository.postNewBuyer(buyer)
    }
}