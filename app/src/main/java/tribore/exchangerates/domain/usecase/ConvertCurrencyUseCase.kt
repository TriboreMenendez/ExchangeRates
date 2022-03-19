package tribore.exchangerates.domain.usecase

import tribore.exchangerates.domain.model.ExchangeCurrency

class ConvertCurrencyUseCase {

    fun execute(amountRuble: Int, exchangeCurrency: ExchangeCurrency): Double {

        val nominal = exchangeCurrency.nominal
        val value = exchangeCurrency.value
        return  amountRuble / value * nominal
    }

}