package tribore.exchangerates.domain.usecase

import tribore.exchangerates.domain.model.RatesCurrencyDomainModel
import tribore.exchangerates.domain.repository.CurrencyRepository

class GetListCurrencyUseCase(private val currencyRepository: CurrencyRepository) {

    suspend fun execute(): List<RatesCurrencyDomainModel> {
        return currencyRepository.getRatesCurrency()
    }
}