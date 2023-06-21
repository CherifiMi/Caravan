package com.example.common.domain.use_cases

import com.example.caravan.data.repository.CaravanRepository
import com.example.caravan.domain.model.Rep
import com.example.caravan.domain.model.Seller
import okhttp3.ResponseBody
import javax.inject.Inject

class PostNewRepUseCase @Inject constructor(
    private val repository: CaravanRepository
){
    suspend operator fun invoke(rep: Rep): ResponseBody {
        return repository.postNewRep(rep)
    }
}