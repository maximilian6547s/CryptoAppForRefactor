package com.example.cryptoapp.data.mapper

import com.example.cryptoapp.data.database.model.CoinInfoDbModel
import com.example.cryptoapp.data.network.model.CoinInfoDto
import com.example.cryptoapp.data.network.model.CoinInfoJsonContainerDto
import com.example.cryptoapp.data.network.model.CoinsNamesListDto
import com.example.cryptoapp.domain.model.CoinInfo
import com.google.gson.Gson
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class CoinMapper {

    fun mapDtoToDbModel(dto: CoinInfoDto): CoinInfoDbModel = CoinInfoDbModel(
        fromSymbol = dto.fromSymbol,
        highDay = dto.highDay,
        imageUrl = BASE_IMAGE_URL + dto.imageUrl,
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
        lastUpdate = convertTimestampToTime(dbModel.lastUpdate),
        lowDay = dbModel.lowDay,
        price = dbModel.price,
        toSymbol = dbModel.toSymbol
    )

    private fun convertTimestampToTime(timestamp: Long?): String {
        if (timestamp == null) return ""
        val stamp = Timestamp(timestamp * 1000)
        val date = Date(stamp.time)
        val pattern = "HH:mm:ss"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }
    companion object {
        const val BASE_IMAGE_URL = "https://cryptocompare.com"

    }
}