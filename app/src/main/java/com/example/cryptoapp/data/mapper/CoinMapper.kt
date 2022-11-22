package com.example.cryptoapp.data.mapper

import com.example.cryptoapp.data.database.model.CoinInfoDbModel
import com.example.cryptoapp.data.network.model.CoinInfoDto
import com.example.cryptoapp.data.network.model.CoinInfoJsonContainerDto
import com.example.cryptoapp.data.network.model.CoinsNamesListDto
import com.example.cryptoapp.domain.model.CoinInfo
import com.google.gson.Gson

class CoinMapper {

    fun mapDtoToDbModel(dto: CoinInfoDto): CoinInfoDbModel = CoinInfoDbModel(
        fromSymbol = dto.fromSymbol,
        highDay = dto.highDay,
        imageUrl = dto.imageUrl,
        lastMarket = dto.lastMarket,
        lastUpdate = dto.lastUpdate,
        lowDay = dto.lowDay,
        price = dto.price,
        toSymbol = dto.toSymbol
    )

    fun mapJsonContainerToListCoinInfo(jsonContainer: CoinInfoJsonContainerDto): List<CoinInfoDto> {
        val result = mutableListOf<CoinInfoDto>()
        val jsonObject = jsonContainer.json ?: return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinInfoDto::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    fun mapNamesListToString(namesListDto: CoinsNamesListDto): String {
        return namesListDto.names?.map {
            it.coinNameDto?.name
        }?.joinToString(",") ?: ""
    }

    fun mapDbModelToEntity(dbModel: CoinInfoDbModel) = CoinInfo(
        fromSymbol = dbModel.fromSymbol,
        highDay = dbModel.highDay,
        imageUrl = dbModel.imageUrl,
        lastMarket = dbModel.lastMarket,
        lastUpdate = dbModel.lastUpdate,
        lowDay = dbModel.lowDay,
        price = dbModel.price,
        toSymbol = dbModel.toSymbol
    )
}