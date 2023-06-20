package com.example.caravan.domain.use_cases

import com.example.caravan.data.repository.CaravanRepository
import com.example.caravan.data.util.Result
import com.example.caravan.domain.model.BuyersList
import com.example.caravan.domain.model.Id
import com.example.caravan.domain.model.UserType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class GetUserTypeUseCase @Inject constructor(
    private val repository: CaravanRepository
) {

    operator fun invoke(id: Id): String{
        var result: String = runBlocking {

            repository.getUserType(id)[0].type
        }

        return result
    }
}