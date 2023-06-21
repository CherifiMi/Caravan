package com.example.common.domain.use_cases

import com.example.caravan.data.repository.CaravanRepository
import com.example.caravan.domain.model.CardEntity
import com.example.caravan.domain.model.Id
import javax.inject.Inject

class SaveCardUseCase @Inject constructor(
    private val repository: CaravanRepository
) {
    suspend operator fun invoke(card : CardEntity){
        repository.saveCard(card)
    }
}