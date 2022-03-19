package tribore.exchangerates.domain.model

data class ExchangeCurrency(
    var charCode: String,
    var nominal: Int,
    var value: Double
) {
    constructor(): this("",1,1.00)
}