package com.example.caravan.domain.use_cases

import com.example.caravan.data.repository.CaravanRepository
import com.example.caravan.data.util.Result
import com.example.caravan.domain.model.BuyersList
import com.example.caravan.domain.model.ProductsList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: CaravanRepository
){
    operator fun invoke(): Flow<Result<ProductsList>> = flow {
        try {
            emit(Result.Loading<ProductsList>())
            val productList = repository.getProducts()
            emit(Result.Success<ProductsList>(productList))
        }catch (e: HttpException){
            emit(Result.Error<ProductsList>(e.localizedMessage ?: "you dont have netin happend"))
        }catch (e: IOException){
            emit(Result.Error<ProductsList>("cant get to server"))
        }
    }
}