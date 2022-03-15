package tribore.exchangerates.data.model

data class ResponseApiModel(
    val Date: String,
    val PreviousDate: String,
    val PreviousURL: String,
    val Timestamp: String,
    val Valute: List<ValuteApi>
)