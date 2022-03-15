package tribore.exchangerates.data.network

import retrofit2.http.GET
import tribore.exchangerates.data.model.ResponseApiModel

//Api key: https://www.cbr-xml-daily.ru/daily_json.js

interface CurrencyNetworkApi {
    @GET("daily_json.js")

    suspend fun getRatesCurrency():ResponseApiModel

}