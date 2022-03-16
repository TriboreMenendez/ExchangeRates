package tribore.exchangerates.domain.usecase

import tribore.exchangerates.domain.model.RatesCurrencyDomainModel
import tribore.exchangerates.domain.repository.CurrencyRepository

class GetListCurrencyUseCase(private val currencyRepository: CurrencyRepository) {

    suspend fun getRatesCurrency(): List<RatesCurrencyDomainModel> {
        return currencyRepository.getRatesCurrency()
    }
}