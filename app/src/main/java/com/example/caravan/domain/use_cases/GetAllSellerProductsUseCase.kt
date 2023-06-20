package com.example.caravan.domain.use_cases

import com.example.caravan.data.repository.CaravanRepository
import com.example.caravan.data.util.Result
import com.example.caravan.domain.model.BuyersList
import com.example.caravan.domain.model.Id
import com.example.caravan.domain.model.ProductsList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAllSellerProductsUseCase @Inject constructor(
    private val repository: CaravanRepository
){
    operator fun invoke(id: Id): Flow<Result<ProductsList>> = flow {
        try {
            emit(Result.Loading<ProductsList>())
            val productsList = repository.getAllSellerProducts(id)
            emit(Result.Success<ProductsList>(productsList))
        }catch (e: HttpException){
            emit(Result.Error<ProductsList>(e.localizedMessage ?: "you dont have net"))
        }catch (e: IOException){
            emit(Result.Error<ProductsList>("cant get to server"))
        }
    }
}