package tribore.exchangerates.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

typealias Valute = Map<String, CurrencyApiModel>

@JsonClass(generateAdapter = true)
data class ResponseApiModel(
    val Date: String,
    val PreviousDate: String,
    val PreviousURL: String,
    val Timestamp: String,
    @Json(name = "Valute") val valute: Valute
)