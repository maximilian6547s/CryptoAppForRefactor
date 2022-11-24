package com.example.cryptoapp.domain.usecases

import com.example.cryptoapp.domain.repository.CoinRepository
import javax.inject.Inject

class GetCoinInfoListUseCase @Inject constructor(
    private val repository: CoinRepository
) {

    operator fun invoke() = repository.getCoinInfoList()
}