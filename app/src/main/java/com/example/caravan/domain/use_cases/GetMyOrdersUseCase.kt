package com.example.caravan.domain.use_cases

import com.example.caravan.data.repository.CaravanRepository
import com.example.caravan.data.util.Result
import com.example.caravan.domain.model.Id
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMyOrdersUseCase @Inject constructor(
    private val repository: CaravanRepository
) {
    operator fun invoke(id: Id): Flow<Result<ResponseBody>> = flow {
        try {
            emit(Result.Loading<ResponseBody>())
            val response = repository.myOrder(id)
            emit(Result.Success<ResponseBody>(response))
        }catch (e: HttpException){
            emit(Result.Error<ResponseBody>(e.localizedMessage ?: "you dont have net"))
        }catch (e: IOException){
            emit(Result.Error<ResponseBody>("cant get to server"))
        }
    }
}