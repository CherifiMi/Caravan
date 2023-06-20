package com.example.caravan.domain.use_cases

import com.example.caravan.data.repository.CaravanRepository
import com.example.caravan.domain.model.Id
import okhttp3.ResponseBody
import javax.inject.Inject

class DeleteThisOrder @Inject constructor(
    private val repository: CaravanRepository
) {
    suspend operator fun invoke(id: Id){
        return repository.deleteOrder(id)
    }
}