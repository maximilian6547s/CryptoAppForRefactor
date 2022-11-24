package com.example.cryptoapp.domain.usecases

import com.example.cryptoapp.domain.repository.CoinRepository
import javax.inject.Inject

class LoadDataUseCase @Inject constructor(
    private val repository: CoinRepository
) {

    operator fun invoke()  = repository.loadData()
}