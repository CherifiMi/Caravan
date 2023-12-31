package com.example.common.domain.use_cases

import com.example.caravan.data.repository.CaravanRepository
import com.example.caravan.domain.model.Id
import com.example.caravan.domain.model.Product
import okhttp3.ResponseBody
import javax.inject.Inject

class DeleteThisProductUseCase @Inject constructor(
    private val repository: CaravanRepository
) {
    suspend operator fun invoke(id: Id): ResponseBody {
        return repository.deleteThisProduct(id)
    }
}