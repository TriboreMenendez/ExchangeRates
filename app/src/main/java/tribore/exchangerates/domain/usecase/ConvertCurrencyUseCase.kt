package tribore.exchangerates.domain.usecase

import tribore.exchangerates.domain.model.ExchangeCurrency

class ConvertCurrencyUseCase {

    fun execute(amountRuble: Int, exchangeCurrency: ExchangeCurrency): Double {

        val nominal = exchangeCurrency.nominalExchangeCurrency
        val value = exchangeCurrency.valueExchangeCurrency
        return  amountRuble / value * nominal
    }

}