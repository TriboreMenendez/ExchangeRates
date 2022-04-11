package tribore.exchangerates.data.repository

import tribore.exchangerates.data.network.CurrencyNetworkApi
import tribore.exchangerates.domain.model.RatesCurrencyDomainModel
import tribore.exchangerates.domain.repository.CurrencyRepository

class CurrencyRepositoryImpl(private val currencyNetworkApi: CurrencyNetworkApi): CurrencyRepository {

    // Converting the data model to a domain model
    override suspend fun getRatesCurrency(): List<RatesCurrencyDomainModel> {
        return currencyNetworkApi.getRatesCurrency().Currency.values.map { it.toDomain() }
    }

}