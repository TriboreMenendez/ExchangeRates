package tribore.exchangerates.domain.model

data class RatesCurrencyDomainModel(
    val charCode: String,
    val id: String,
    val name: String,
    val nominal: Int,
    val numCode: String,
    val previous: Double,
    val value: Double
)