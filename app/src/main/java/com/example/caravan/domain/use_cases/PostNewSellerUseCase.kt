package com.example.caravan.domain.use_cases

import com.example.caravan.data.repository.CaravanRepository
import com.example.caravan.domain.model.Buyer
import com.example.caravan.domain.model.Seller
import okhttp3.ResponseBody
import javax.inject.Inject

class PostNewSellerUseCase @Inject constructor(
    private val repository: CaravanRepository
){
    suspend operator fun invoke(seller: Seller): ResponseBody {
        return repository.postNewSeller(seller)
    }
}