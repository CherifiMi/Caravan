package com.example.caravan.domain.use_cases

import com.example.caravan.data.repository.CaravanRepository
import com.example.caravan.domain.model.Product
import okhttp3.ResponseBody
import javax.inject.Inject

class ChangeThisProductUseCase @Inject constructor(
    private val repository: CaravanRepository
){
    suspend operator fun invoke(product: Product): ResponseBody {
        return repository.changeThisProduct(product)
    }
}