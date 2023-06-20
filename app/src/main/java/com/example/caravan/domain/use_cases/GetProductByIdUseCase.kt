package com.example.caravan.domain.use_cases

import com.example.caravan.data.repository.CaravanRepository
import com.example.caravan.domain.model.Id
import com.example.caravan.domain.model.Product
import com.example.caravan.domain.model.ProductsList
import com.google.gson.Gson
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(
    private val repository: CaravanRepository
){
    suspend operator fun invoke(id: Id): Product {
        return Gson().fromJson(repository.getProductById(id).string(), ProductsList::class.java)[0]
    }
}