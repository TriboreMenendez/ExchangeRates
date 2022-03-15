package tribore.exchangerates.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseApiModel(
    val Date: String,
    val PreviousDate: String,
    val PreviousURL: String,
    val Timestamp: String,
    val Valute: Valute
)