package tribore.exchangerates.domain.model

data class RatesCurrencyDomainModel(
    val CharCode: String,
    val ID: String,
    val Name: String,
    val Nominal: Int,
    val NumCode: String,
    val Previous: Double,
    val Value: Double
)