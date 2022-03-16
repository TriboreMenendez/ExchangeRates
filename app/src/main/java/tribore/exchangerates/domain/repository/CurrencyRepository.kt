package tribore.exchangerates.domain.repository

import tribore.exchangerates.domain.model.RatesCurrencyDomainModel

interface CurrencyRepository {
    suspend fun getRatesCurrency(): List<RatesCurrencyDomainModel>
}