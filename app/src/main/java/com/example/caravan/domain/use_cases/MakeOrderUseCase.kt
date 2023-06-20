package com.example.caravan.domain.use_cases

import com.example.caravan.data.repository.CaravanRepository
import com.example.caravan.domain.model.Order
import com.example.caravan.domain.model.Product
import okhttp3.ResponseBody
import javax.inject.Inject

class MakeOrderUseCase @Inject constructor(
    private val repository: CaravanRepository
){
    suspend operator fun invoke(order: Order) {
        return repository.makeOrder(order)
    }
}